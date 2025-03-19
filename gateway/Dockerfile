FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/gateway-0.0.1-SNAPSHOT.jar gateway.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "gateway.jar"]