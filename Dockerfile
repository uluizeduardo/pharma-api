FROM openjdk:21
WORKDIR /app
COPY target/pharma-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080 5006
ENTRYPOINT ["sh", "-c", "java $ADDITIONAL_OPTS -jar app.jar"]
