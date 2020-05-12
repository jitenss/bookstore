FROM openjdk:8-jre-alpine

COPY target/bookstore-0.0.1-SNAPSHOT.jar /app.jar

CMD ["java", "-jar", "-Dserver.port=8080", "/app.jar"]



#COPY ./target/bookstore-0.0.1-SNAPSHOT.jar /usr/app/
#
#WORKDIR /usr/app
#
##RUN sh -c 'bookstore-0.0.1-SNAPSHOT.jar'
#
#CMD ["java","-jar","bookstore-0.0.1-SNAPSHOT.jar"]