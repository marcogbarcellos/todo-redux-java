# Todo Application

This is a simple Todo Application built with React/Redux on the frontend and Java on the backend.

## Backend Tech Stack

This project is built with:

- Java 8 -> Language
- Spring Boot -> Main Framework
- MySql -> Main Database
- H2(In Memory DB) -> Test Database
- Logback -> Logging Framework
- ElasticSearch -> NoSQL Database used to store Logs asynchronously

For more details on how on the backend implementation go to the [Backend Documentation](server/README.md)

## Frontend Tech Stack

This project is built with:

- Javascript -> Language
- React -> View Framework
- Redux -> Main Framework

For more details on how on the frontend implementation go to the [Frontend Documentation](client/README.md)

## Requirements

To run this project you'll need to download the following:

- [Java 8+](https://www.java.com/en/download/)
- [Gradle](https://gradle.org/install)
- [Docker](https://docs.docker.com/engine/installation/)
- [NodeJs 6+](https://nodejs.org/en/download/)

## Running the Project With Docker

For simplicity's sake a script called `run.sh` located in the main root was created, so all You'll need to do is go to the project's root and run

- `$ sh run.sh` OR `$ ./run.sh`

After that you'll be able to start, you can check the following links(if you keep the ports as the predefined ones):

- Backend API Documentation: http://localhost:8080/api/v1/swagger-ui.html
- Backend API Documentation: http://localhost:9000/