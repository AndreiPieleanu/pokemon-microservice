# pokemon-microservice 👾

The microservice for pokemons! 

Here I will present the project's setup steps, how to run it and how to add new features! 

## Pre-requisites

You will need to download the following applications in order to benefit from this project's code: 
- [Docker](https://www.docker.com/products/docker-desktop/)
- Any IDEs, such as [IntelliJ](https://www.jetbrains.com/idea/download/), [Visual Studio](https://visualstudio.microsoft.com/downloads/), etc.
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) as it's the most stable version
- [Postman](https://www.postman.com/downloads/) to send HTTP requests
- [RabbitMQ](https://www.rabbitmq.com/docs/download). I reccomend that you use Docker to pull the RabbitMQ image, because it is easier to work with and faster to deploy. If you choose this option, make sure you have downloaded Docker before runnign this command:

`docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4-management`

## How to use

There are 2 options of using this project: via Docker or via repository cloning.

### Docker way

For Docker way, we need to create a network where our pokemon app can access RabbitMQ. To create a network named `pokemon-network`, open a cmd file and type the following command: 

`docker network create pokemon-network`

Make sure you have the RabbitMQ image pulled first, consult **Pre-requisites** to see how you do it. Then, create a RabbitMQ container within `pokemon-network` network and assign ports `5672` and `15672` to it: 

`docker run --name rabbitmq-pokemon --network pokemon-network -p 5672:5672 -p 15672:15672 rabbitmq:4.0-management`

Then pull the `pokemon-microservice` image via this command: 

`docker image pull trainingtexas/pokemon-service`

Then create a container based on the image and assign it to our network like this: 

`docker run --name pokemon-service --network pokemon-network -p 8080:8080 trainingtexas/pokemon-service:latest`

You should see the 2 newly-created contianer in Docker Desktop like below: 

![image](https://github.com/user-attachments/assets/440ff085-0aee-4c73-9e10-f0b015f680bc)

Run both projects and wait 1-2 minutes for them to boot. Then, open Postman and create some HTTP requests via `Collections -> My Workspace -> New -> HTTP`. The URL is `http://localhost:8080/pokemon`. Below you can see the HTTP methods you have access to: 
- get all pokemons: `GET http://localhost:8080/pokemon`
- add new pokemon: `POST http://localhost:8080/pokemon`, body raw JSON `{"name": "Pikachu", "type": "Golden"}`. `Name` and `Type` can be whatever String you want
- update pokemon with id 1: `PUT http://localhost:8080/pokemon/1`, body raw JSON `{"name": "New Pikachu", "type": New Gold"}`. `Name` and `Type` can be whatever String you want
- delete pokemon with id 1: `DELETE http://localhost:8080/pokemon/1`
- get pokemon with id 1: `GET http://localhost:8080/pokemon/1`
- get external pokemons: `GET http://localhost:8080/pokemon/external`

### Project way

Make sure you have the RabbitMQ image pulled first, consult **Pre-requisites** to see how you do it. Then, create a RabbitMQ container within `pokemon-network` network and assign ports `5672` and `15672` to it: 

`docker run --name rabbitmq-pokemon --network pokemon-network -p 5672:5672 -p 15672:15672 rabbitmq:4.0-management`

Running the project and having access to code requires you to clone this repository. Once it's done, open it with an IDE (I'm using IntelliJ). Go to class `PokemonMicroserviceApplication` and run it and wait approximately 1/2 minutes to boot up. Then, open Postman and create some HTTP requests via `Collections -> My Workspace -> New -> HTTP`. The URL is `http://localhost:8080/pokemon`. Below you can see the HTTP methods you have access to: 
- get all pokemons: `GET http://localhost:8080/pokemon`
- add new pokemon: `POST http://localhost:8080/pokemon`, body raw JSON `{"name": "Pikachu", "type": "Golden"}`. `Name` and `Type` can be whatever String you want
- update pokemon with id 1: `PUT http://localhost:8080/pokemon/1`, body raw JSON `{"name": "New Pikachu", "type": New Gold"}`. `Name` and `Type` can be whatever String you want
- delete pokemon with id 1: `DELETE http://localhost:8080/pokemon/1`
- get pokemon with id 1: `GET http://localhost:8080/pokemon/1`
- get external pokemons: `GET http://localhost:8080/pokemon/external`

## Features 🎨

- **100% Kotlin code**.
- CRUD pokemons.
- Get external API pokemons
- Unit and integration tests made with Junit 5 and Mockito.
- CI/CD Setup with GitHub Actions.

## Gradle Setup 🐘

This template is using [**Gradle Kotlin DSL**](https://docs.gradle.org/current/userguide/kotlin_dsl.html) as well as the [Plugin DSL](https://docs.gradle.org/current/userguide/plugins.html#sec:plugins_block) to setup the build.

Dependencies are centralized inside the Gradle Version Catalog in the [libs.versions.toml](gradle/libs.versions.toml) file in the `gradle` folder.

## CI/CD ⚙️

This template is using [**GitHub Actions**](https://github.com/cortinico/kotlin-android-template/actions) as CI. You don't need to setup any external service and you should have a running CI/CD once you start using this template, just make sure that you turn on the "Read and Write permissions" on the Action Settings of your repository.

There are currently the following workflows available:
- [CI/CD pipeline](.github/workflows/main.yml) - Will compile, test, measure and push new Docker image to hub. 

## Testing
The project contains unit tests and integration tests. We do unit tests on the service layer, as most logic can be found there and integration tests on the controller. The total unit test coverage is 94% and 31% for integration tests, simply to prove that it works. To generate a new test report, write the following command in a cmd within the repository workspace: 

`./gradlew test jacocoTestReport`

The newly generated report can be found at `build/customJacocoReportDir/test/jacocoTestReport.xml` relative to project path. Everytime you push a new change, a new report will be generated and found at the earlier-mentioned path. 

## Possible improvement ideas 💹
- SonarQube/SonarCloud for monitoring and ensuring high-quality, bug-free code.
- more features

# Architecture
My application consists of a single microservice called `pokemon-microservice`. This microservice is comprised of 3 layers: data/repository layer, service/business layer and controller/layer. 
![c1](https://github.com/user-attachments/assets/54e1c84e-fee2-4da4-92a9-f85e96ef976b)
![c2](https://github.com/user-attachments/assets/9d4fc332-bad4-4f20-a9f5-d95e09d96d54)
![c3](https://github.com/user-attachments/assets/f88abf6c-35a3-499f-baab-6935713d6c79)
![c4](https://github.com/user-attachments/assets/8fa894ce-ea28-4622-968b-c2de423bc5dd)

Controller layer communicates with Postman interface via HTTP requests. It sends the commands to IService layer which handles the logic. The data is retrieved from H2 database via the data layer. DTO classes are used to handle the database elements thanks to JPA. The microservice is built in such a way it fires RabbitMQ events that can be listened to by other microservices if developers wishes to add. 

## RabbitMQ

We have a main exchange `pokemon-exchange` with 1 queue and 1 routing key for each CRUD operation (4 in total). Each microservice must have 4 classes and be configured as follwing: 
- `RabbitMQConfig`: configuration for RabbitMQ by setting up exchanges, queues, and communication channels
- `RabbitMQProducer`: managing methods for firing events
- `RabbitMQConsumer`: managing methods for listening to events
- `PokemonCreatedEvent`: class responsible for storing event data

Once the RabbitMQ Docker container is running, you can analyse and measure RabbitMQ connections and events via the [main console](http://localhost:15672/). 
