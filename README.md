# REST cooking recipe database application

Angular 7 Front End with Bootstrap

Spring Boot Back End with Maven and Hibernate, Spring Security with JWT

MySQL database



## How to run:

1. Create schema called 'testdb' on localhost port 3306 in database (or change these values in src/java/main/resources/application.properties as well as the datasource username and password of your choice).
2. In Spring Boot Back End folder run 'mvn clean install' (this will run unit tests and create tables in your database).
3. In Angular 7 Front End folder run 'npm install' (this will install node modules).
4. Now you're ready to run the app. In Angular 7 Front End folder run 'ng serve' and open a browser tab on localhost:4200 or just run the command 'ng serve --open'.
