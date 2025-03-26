FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/patients_informations-0.0.1-SNAPSHOT.jar patInformation.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "patInformation.jar"]