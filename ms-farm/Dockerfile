FROM openjdk

WORKDIR /app

COPY target/farmbox-0.0.1-SNAPSHOT.jar /app/farmbox.jar

ENTRYPOINT ["java","-jar", "farmbox.jar"]

EXPOSE 8320

