FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/patient-information-0.0.1-SNAPSHOT.jar patInformation.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "patInformation.jar"]