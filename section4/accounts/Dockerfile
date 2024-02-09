##start with a base image containing java runtime
FROM openjdk:17-jdk-slim

## information around who maintains the image
MAINTAINER mahendrakundare.com

#Add the application jar to the image
COPY build/libs/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]