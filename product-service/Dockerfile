FROM openjdk:17-slim
VOLUME /tmp
COPY target/product-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8001 3306
ENTRYPOINT ["java","-jar","/app.jar"]
