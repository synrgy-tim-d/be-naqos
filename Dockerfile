#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
ARG DATASOURCE_POSTGRES_URL
ENV DATASOURCE_POSTGRES_URL=$DATASOURCE_POSTGRES_URL

ARG POSTGRES_USERNAME
ENV POSTGRES_USERNAME=$POSTGRES_USERNAME

ARG POSTGRES_PASSWORD
ENV POSTGRES_PASSWORD=$POSTGRES_PASSWORD

ARG JWT_SECRET
ENV JWT_SECRET=$JWT_SECRET

ARG CLOUDINARY_CLOUD_NAME
ENV CLOUDINARY_CLOUD_NAME=$CLOUDINARY_CLOUD_NAME

ARG CLOUDINARY_API_KEY
ENV CLOUDINARY_API_KEY=$CLOUDINARY_API_KEY

ARG CLOUDINARY_API_SECRET
ENV CLOUDINARY_API_SECRET=$CLOUDINARY_API_SECRET

COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM wurstmeister/base

MAINTAINER Wurstmeister

ENV ZOOKEEPER_VERSION 3.4.13

#Download Zookeeper
RUN wget -q http://mirror.vorboss.net/apache/zookeeper/zookeeper-${ZOOKEEPER_VERSION}/zookeeper-${ZOOKEEPER_VERSION}.tar.gz && \
wget -q https://www.apache.org/dist/zookeeper/KEYS && \
wget -q https://www.apache.org/dist/zookeeper/zookeeper-${ZOOKEEPER_VERSION}/zookeeper-${ZOOKEEPER_VERSION}.tar.gz.asc && \
wget -q https://www.apache.org/dist/zookeeper/zookeeper-${ZOOKEEPER_VERSION}/zookeeper-${ZOOKEEPER_VERSION}.tar.gz.md5

#Verify download
RUN md5sum -c zookeeper-${ZOOKEEPER_VERSION}.tar.gz.md5 && \
gpg --import KEYS && \
gpg --verify zookeeper-${ZOOKEEPER_VERSION}.tar.gz.asc

#Install
RUN tar -xzf zookeeper-${ZOOKEEPER_VERSION}.tar.gz -C /opt

#Configure
RUN mv /opt/zookeeper-${ZOOKEEPER_VERSION}/conf/zoo_sample.cfg /opt/zookeeper-${ZOOKEEPER_VERSION}/conf/zoo.cfg

ENV JAVA_HOME /usr/lib/jvm/java-7-openjdk-amd64
ENV ZK_HOME /opt/zookeeper-${ZOOKEEPER_VERSION}
RUN sed  -i "s|/tmp/zookeeper|$ZK_HOME/data|g" $ZK_HOME/conf/zoo.cfg; mkdir $ZK_HOME/data

ADD start-zk.sh /usr/bin/start-zk.sh
EXPOSE 2181 2888 3888

WORKDIR /opt/zookeeper-${ZOOKEEPER_VERSION}
VOLUME ["/opt/zookeeper-${ZOOKEEPER_VERSION}/conf", "/opt/zookeeper-${ZOOKEEPER_VERSION}/data"]

CMD /usr/sbin/sshd && bash /usr/bin/start-zk.sh

FROM wurstmeister/kafka:latest
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
CMD ["/wait-for-it.sh", "zookeper:2181", "--", "start-kafka.sh"]

#
# Package stage
#
FROM openjdk:11-jre-slim

ARG kafka_version=3.3.1
ARG scala_version=3.2.1
ARG vcs_ref=unspecified
ARG build_date=unspecified

LABEL org.label-schema.name="kafka" \
      org.label-schema.description="Apache Kafka" \
      org.label-schema.build-date="${build_date}" \
      org.label-schema.vcs-url="https://github.com/wurstmeister/kafka-docker" \
      org.label-schema.vcs-ref="${vcs_ref}" \
      org.label-schema.version="${scala_version}_${kafka_version}" \
      org.label-schema.schema-version="1.0" \
      maintainer="wurstmeister"

ENV KAFKA_VERSION=$kafka_version \
    SCALA_VERSION=$scala_version \
    KAFKA_HOME=/opt/kafka

ENV PATH=${PATH}:${KAFKA_HOME}/bin

COPY download-kafka.sh start-kafka.sh broker-list.sh create-topics.sh versions.sh /tmp2/

RUN set -eux ; \
    apt-get update ; \
    apt-get upgrade -y ; \
    apt-get install -y --no-install-recommends jq net-tools curl wget ; \
### BEGIN docker for CI tests
    apt-get install -y --no-install-recommends gnupg lsb-release ; \
	curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg ; \
	echo \
  		"deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/debian \
  		$(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null ; \
    apt-get update ; \
    apt-get install -y --no-install-recommends docker-ce-cli ; \
    apt remove -y gnupg lsb-release ; \
    apt clean ; \
    apt autoremove -y ; \
    apt -f install ; \
### END docker for CI tests
### BEGIN other for CI tests
    apt-get install -y --no-install-recommends netcat ; \
### END other for CI tests
    chmod a+x /tmp2/*.sh ; \
    mv /tmp2/start-kafka.sh /tmp2/broker-list.sh /tmp2/create-topics.sh /tmp2/versions.sh /usr/bin ; \
    sync ; \
    /tmp2/download-kafka.sh ; \
    tar xfz /tmp2/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz -C /opt ; \
    rm /tmp2/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz ; \
    ln -s /opt/kafka_${SCALA_VERSION}-${KAFKA_VERSION} ${KAFKA_HOME} ; \
    rm -rf /tmp2 ; \
    rm -rf /var/lib/apt/lists/*

COPY overrides /opt/overrides

# Use "exec" form so that it runs as PID 1 (useful for graceful shutdown)
CMD ["start-kafka.sh"]

COPY --from=build /home/app/target/apps-0.0.1-SNAPSHOT.jar /usr/local/lib/backend.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/backend.jar"]