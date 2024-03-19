FROM openjdk
LABEL authors="TK"
WORKDIR /measurement-generator
EXPOSE 8088
COPY target/measurement-generator-1.0.0-SNAPSHOT.jar measurement-generator-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "measurement-generator-1.0.0-SNAPSHOT.jar"]
