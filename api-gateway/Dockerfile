FROM openjdk:17-slim
VOLUME /tmp
COPY target/api-gateway-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","/app.jar"]
