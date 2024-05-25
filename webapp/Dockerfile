FROM openjdk:17-slim
VOLUME /tmp
COPY target/webapp-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 80 8001 8101 8201
ENTRYPOINT ["java","-jar","/app.jar"]
