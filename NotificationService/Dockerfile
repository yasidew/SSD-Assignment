FROM openjdk:17
LABEL authors="YKods"
EXPOSE 8082
ADD target/notification-service-docker.jar notification-service-docker.jar

ENTRYPOINT ["java", "-jar", "/notification-service-docker.jar"]

