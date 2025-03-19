FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/patient_bilan-0.0.1-SNAPSHOT.jar patBilan.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "patBilan.jar"]