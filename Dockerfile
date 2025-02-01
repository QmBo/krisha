FROM openjdk:11-jdk-oracle
WORKDIR /krisha-parser
ADD target/*.jar krisha-parser.jar
ENTRYPOINT ["java", "-jar", "krisha-parser.jar"]