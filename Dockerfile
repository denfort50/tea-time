FROM openjdk
ARG JAR_FILE=target/tea-time-1.0.jar
WORKDIR tea-time
COPY ${JAR_FILE} app.jar
CMD java -jar app.jar