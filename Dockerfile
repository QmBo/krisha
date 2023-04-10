FROM openjdk:11-jdk-oracle
WORKDIR mir-exchange
ADD target/krisha-parser.jar krisha-parser.jar
ENTRYPOINT java -jar krisha-parser.jar