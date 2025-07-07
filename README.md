# Blackjack Game API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

This project implements a reactive Java API for a Blackjack game using Spring Boot. The API is designed to interact with two different databases: MongoDB and MySQL. It includes all necessary functionalities for playing Blackjack, such as managing players, card hands, and game rules.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [API Endpoints](#api-endpoints)
  - [Player Management](#player-management)
  - [Game Management](#game-management)
- [Database Configuration](#database-configuration)
- [Exception Handling](#exception-handling)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

## Features
- **Reactive Architecture**: Built with Spring WebFlux for non-blocking operations
- **Database**: MongoDB and MySQL
- **Game Logic**: Implements all Blackjack rules (hit, stand, )
- **Player Management**: Registration and score tracking
- **Comprehensive Testing**: Unit and integration tests coverage
- **API Documentation**: Auto-generated Swagger/OpenAPI docs

## Technologies Used
| Category        | Technologies                          |
|-----------------|--------------------------------------|
| Backend         | Java 17, Spring Boot 3, WebFlux      |
| Databases       | MongoDB, MySQL                       |
| Data Access     | Spring Data MongoDB, R2DBC           |
| Utilities       | Lombok, ModelMapper                  |
| Testing         | JUnit 5, Mockito, Testcontainers     |
| Documentation   | SpringDoc OpenAPI (Swagger UI)       |

## Getting Started

### Prerequisites
- JDK 17+
- Maven 3.8+
- Docker (optional, for database containers)
- MySQL 8.0+
- MongoDB 5.0+

### Installation

   ```bash
   git clone https://github.com/zohra-b/S5.01.git
   cd S5.01

   Build the project:

   ```bash
mvn clean install
```
   ```bash
mvn spring-boot:run
```

The application will be available at: http://localhost:8080

## Documentation (Swagger)

API documentation is automatically generated using SpringDoc OpenAPI. Once the application is running, you can access:

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs


## API Endpoints
### Player Management (MySQL)

| Endpoint                     | Method | Description                                  |
|------------------------------|--------|----------------------------------------------|
| `/players/getAll`            | GET    | Retrieves all registered players             |
| `/players/playerById/{id}`   | GET    | Gets details of a player by their ID         |
| `/players/playerByEmail/{email}` | GET | Gets details of a player by their email      |
| `/players/login`             | POST   | Attempts player login by checking email      |
| `/players/register`          | POST   | Registers a new player                       |
| `/players/{id}/updateScore`  | PUT    | Updates a player's total score               |
| `/players/{id}`              | PUT    | Changes a player's name                      |
| `/players/ranking`           | GET    | Retrieves players ranked by total score      |

### Game Management (MongoDB)

| Endpoint                     | Method | Description                                  |
|------------------------------|--------|----------------------------------------------|
| `/games/new`                 | POST   | Creates and starts a new Blackjack game      |
| `/games/{id}/play`           | PUT    | Allows a player to make a move in a game     |
| `/games/{id}`                | GET    | Retrieves the full details of a game session |
| `/games/{id}/delete`         | DELETE | Deletes a specific game session              |
| `/games/{playerId}/games`    | GET    | Retrieves all games by a specific player     |
