# Nearsoft Questions

## Requirements

- [Java 1.8] (https://docs.oracle.com/javase/8/docs/technotes/guides/install/mac_jdk.html)
- [PostgreSQL Server] (http://www.postgresql.org/download/macosx/)
- Maven 3. Run `brew update && brew install maven`

## Setting up your environment

- Create a database in Postgres. By default the application is trying to connect to a `nsquestions` database but you can override this using the dbname property
- Make sure you have a database user with privileges to work with the created database.

## Running

We are using Spring Boot and the plugin to run a Spring Boot application with Maven is enabled so you can run the app with
`mvn spring-boot:run -Ddbuser=<user> -Ddbpass=<password> -Dserver.port=<port>`

**Note**: To be able to login with Google Nearsoft account you must run the application in 8080 or 8081

## Deploying

Each time a change in the code from the master branch is detected a new deployment is done to Heroku app. master branch is protected so you can't push directly to it.

## How to contribute

Please check how to contribute in our [Contribution document](https://github.com/Nearsoft/questions/blob/master/CONTRIBUTING.md)

