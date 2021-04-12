FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8081
ADD /target/StartupReview-0.0.1-SNAPSHOT.jar StartupReview-spring.jar
ENTRYPOINT ["java", "-jar", "StartupReview-spring.jar"]