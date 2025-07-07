FROM openjdk:21-jdk

LABEL maintainer="Rychu"

COPY target/ProductsMicroService-0.0.1-SNAPSHOT.jar ProductsMicroService-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "ProductsMicroService-0.0.1-SNAPSHOT.jar"]