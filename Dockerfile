FROM openjdk:11
ADD /target/JavaSpringBoot-1.0-SNAPSHOT.jar javaspringboot.jar
ENTRYPOINT ["java", "-jar", "javaspringboot.jar"]
