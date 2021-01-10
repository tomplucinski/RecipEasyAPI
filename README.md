# API for RecipEasy

## Supported versions:

- Java 8 to 13
- Spring boot 2.2.0.RELEASE
- MongoDB 4.2.1
- MongoDB Java driver 3.11.1
- Maven 3.6.2

## Commands

- Start the server in a console with `mvn spring-boot:run`.
- If you add some Unit Tests, you would start them with `mvn clean test`.
- You can start the end to end tests with `mvn clean integration-test`.
- You can build the project with : `mvn clean package`.
- You can run the project with the fat jar and the embedded Tomcat: `java -jar target/java-spring-boot-mongodb-starter-1.0.0.jar` but I would use a real tomcat in production.

## Swagger
- Swagger is already configured in this project in `SwaggerConfig.java`.
- The API can be seen at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).
- You can also try the entire REST API directly from the Swagger interface!