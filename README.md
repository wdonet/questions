# Nearsoft Questions

## Continuous Integration

We are following behaviors in two tools (**TODO**: _later we'll decide wich one is better_):

1. [![Travis Build Status](https://travis-ci.org/Nearsoft/questions.svg?branch=dev)](https://travis-ci.org/Nearsoft/questions)
    - The config is based on [travis.yml](https://github.com/Nearsoft/questions/blob/dev/.travis.yml) file
2. [![Codeship Build Status](https://codeship.com/projects/fe46cd40-f9e0-0133-27dd-124ad23604b3/status?branch=dev)](https://codeship.com/projects/151404)
    - Each push on [dev branch](https://github.com/Nearsoft/questions/tree/dev) triggers a build.

## Requirements

- [Java 1.8] (https://docs.oracle.com/javase/8/docs/technotes/guides/install/mac_jdk.html)
- [PostgreSQL Server] (http://www.postgresql.org/download/macosx/)
- [Flywaydb 3](https://flywaydb.org/)
- [Maven 3](http://maven.apache.org/ref/3.3.3/). Run `brew update && brew install maven`
- [Node 6](https://nodejs.org/en/). You can use [nvm] (https://github.com/creationix/nvm) to manage node versions instead of setting up virtual environments.

## Setting up your environment

### Database
- Create a database in Postgres. By default the application is trying to connect to a `nsquestions` database but you can override this using the dbname property.
- Make sure you have a database user with privileges to work with the created database.
- We manage migrations with _Flywaydb_, so every time you run the Api application it checks for new migrations to install over the database in Postgres.

### Running the API (Java)

We are using Spring Boot and the maven plugin to run the application.

1. Run:
> ``mvn spring-boot:run -Ddbuser=<user> -Ddbpass=<password> -Dserver.port=<port>``
    - `spring-boot` - The maven plugin
    - `dbuser` - The Postgres user used in your local DB (**required**)
    - `dbpass` - The Postgres password used in your local DB (default is no password)
    - `dbname` - The Postgres DB name used in your local DB (default is nsquestions)
    - `dbhost` - The Postgres host (default is localhost)
    - `dbport` - The Postgres port (default is 5432)
    - `server.port` - the port to use for the web application (default is 8080)
2. Open your browser and go to url `http://localhost:<port>`.

#### API Notes:
1. To be able to login with Google Nearsoft account you must run the application in port 8080 locally.
2. [API documentation](https://github.com/Nearsoft/questions/blob/dev/API.md).

### Running the React App

To run the App in React:

1. Go to `src/main/webapp`
2. Run `npm install`
3. Run `npm start`
4. Open your browser and go to url `http://localhost:3001`

For more information about the stack used here, check the documentation of [React Starter Kit](https://github.com/kriasoft/react-starter-kit).

## Development in React

We follow the [Airbnb JavaScript Style Guide](https://github.com/airbnb/javascript) to keep the code quality.

You should configure your IDE/Text editor with [Eslint](http://eslint.org/), it will pick the configuration from the `package.json` and verify your code as you type.
You *must* run the command `npm run lint` prior any commit to verify warnings free.

## Deploying

Each push on [master branch](https://github.com/Nearsoft/questions/tree/master) triggers a new deployment in [Heroku app](http://nsquestions.herokuapp.com).
You shouldn't push to `Master branch` without a review of principals.
**TODO:** We are planning to do separately triggers for qa/dev/others envs.

### + Environments
+ [SkyTouch](https://skquestions.herokuapp.com/)
+ [QA](https://qa.herokuapp.com)
+ [HackDayCUN](https://questionshd.herokuapp.com/)

## How to contribute

Please check how to contribute in our [Contribution document](https://github.com/Nearsoft/questions/blob/master/CONTRIBUTING.md)
