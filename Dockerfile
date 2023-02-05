#
# Build stage
#
FROM maven:3.6.3-jdk-8 AS build
WORKDIR /tmp

# Download the libxext6 package
RUN wget http://kr.archive.ubuntu.com/ubuntu/pool/main/libx/libxext/libxext6_1.3.4-0ubuntu1_amd64.deb

# Install the libxext6 package
RUN dpkg -i libxext6_1.3.4-0ubuntu1_amd64.deb && rm libxext6_1.3.4-0ubuntu1_amd64.deb

ARG BASE_URL
ENV BASE_URL=$BASE_URL

ARG MAIL_USERNAME
ENV MAIL_USERNAME=$MAIL_USERNAME

ARG MAIL_PASSWORD
ENV MAIL_PASSWORD=$MAIL_PASSWORD

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

#
# Package stage
#
FROM openjdk:8-jre
COPY --from=build /home/app/target/*.jar /usr/local/lib/backend.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/backend.jar"]