FROM openjdk:17-slim
VOLUME /tmp
COPY target/order-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8101 3306
ENTRYPOINT ["java","-jar","/app.jar"]
