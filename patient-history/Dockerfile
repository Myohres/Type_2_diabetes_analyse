FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/patient-history-0.0.1-SNAPSHOT.jar patHistory.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "patHistory.jar"]