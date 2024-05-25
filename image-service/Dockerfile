FROM openjdk:17-slim
VOLUME /tmp
COPY target/image-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8201 3306
ENTRYPOINT ["java","-jar","/app.jar"]
