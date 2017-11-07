#!/bin/bash

cd server/
./gradlew buildDocker
cd ..
cd client/
docker build -t marcogbarcellos/todo-redux:latest .
cd ..
docker-compose up