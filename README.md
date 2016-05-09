# Nearsoft Questions

## Requirements

- [Java 1.8] (https://docs.oracle.com/javase/8/docs/technotes/guides/install/mac_jdk.html)
- [PostgreSQL Server] (http://www.postgresql.org/download/macosx/)
- Maven 3. Run `brew update && brew install maven`
- Node 6. You can use [nvm] (https://github.com/creationix/nvm)

## Setting up your environment

- Create a database in Postgres. By default the application is trying to connect to a `nsquestions` database but you can override this using the dbname property
- Make sure you have a database user with privileges to work with the created database.

## Running the API

We are using Spring Boot and the plugin to run a Spring Boot application with Maven is enabled so you can run the app with
`mvn spring-boot:run -Ddbuser=<user> -Ddbpass=<password> -Dserver.port=<port>`

**Note**: To be able to login with Google Nearsoft account you must run the application in 8080

## Running the React App

To run the App in React go to `src/main/webapp` and run the command `npm start`. Once it's ready it will open your browser with the url `http://localhost:3001`.
For more information about the stack used here, check the documentation of [React Starter Kit](https://github.com/kriasoft/react-starter-kit).

## Development in React

To mantain the code quality we are following the [Airbnb JavaScript Style Guide](https://github.com/airbnb/javascript). Configure your main editor to use [Eslint](http://eslint.org/), it will pick the configuration from the `package.json` and verify your code as you type.

I encourage you to run the command `npm run lint` prior any commit to verify it's warnings free.

## Deploying

Each time a change in the code from the master branch is detected a new deployment is done to Heroku app. master branch is protected so you can't push directly to it.

## How to contribute

Please check how to contribute in our [Contribution document](https://github.com/Nearsoft/questions/blob/master/CONTRIBUTING.md)

