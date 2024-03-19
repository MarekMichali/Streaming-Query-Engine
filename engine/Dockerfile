FROM openjdk
LABEL authors="TK"
WORKDIR /engine
EXPOSE 8080
COPY target/engine-1.0.0-SNAPSHOT.jar engine-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "engine-1.0.0-SNAPSHOT.jar"]
