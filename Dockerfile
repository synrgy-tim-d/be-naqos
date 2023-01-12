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

FROM ubuntu:latest
RUN apt-get update && apt-get install -y openjdk-8-jdk wget
RUN wget http://mirrors.gigenet.com/apache/kafka/2.5.0/kafka_2.12-2.5.0.tgz
RUN tar xzf kafka_2.12-2.5.0.tgz && rm kafka_2.12-2.5.0.tgz

# Install Zookeeper
RUN wget https://downloads.apache.org/zookeeper/zookeeper-3.6.2/apache-zookeeper-3.6.2-bin.tar.gz
RUN tar xzf apache-zookeeper-3.6.2-bin.tar.gz && rm apache-zookeeper-3.6.2-bin.tar.gz

# Add scripts to start Kafka and Zookeeper
COPY start-kafka.sh /usr/local/bin/
COPY start-zookeeper.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/start-kafka.sh
RUN chmod +x /usr/local/bin/start-zookeeper.sh

# Expose ports
EXPOSE 2181
EXPOSE 9092
CMD [ "/usr/local/bin/start-zookeeper.sh", "/usr/local/bin/start-kafka.sh" ]

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/apps-0.0.1-SNAPSHOT.jar /usr/local/lib/backend.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/backend.jar"]