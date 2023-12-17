FROM openjdk:17-slim

ARG IMAGE_VERSION="0.0.1-SNAPSHOT"
ARG IMAGE_NAME="Mancala Kalaha Backend Service"
ARG MAINTAINER="Adnan Siddiq"
LABEL version=${IMAGE_VERSION} name=${IMAGE_NAME} maintainer=${MAINTAINER}

COPY target/mancala-kalaha-0.0.1.jar .
CMD ["java","-jar", "mancala-kalaha-0.0.1.jar"]
EXPOSE 8080
