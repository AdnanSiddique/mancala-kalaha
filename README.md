# Mancala Kahala Game

## Game Description

Mancala Kahala is a traditional two-player turn-based strategy board game. 
The game involves capturing stones in pits and aims to accumulate more stones in the player's store (Kahala) by the end of the game. 
Each player has their own set of pits and stores, and the game offers engaging tactics and strategic thinking.

## Project Structure

The Mancala Kahala Game project is structured as a full-stack web application with a Spring Boot backend, MongoDB for data storage, and a React UI for the frontend.

### Backend (Spring Boot)

The backend is implemented using Spring Boot, providing a robust and scalable foundation for the game logic and data storage.

- **GameServiceImpl:** Implements the game logic, including initializing a game, handling moves, and updating game status.

- **MoveHandler Interfaces (ValidationHandler, SowHandler, UpdateHandler):** Implements a chain of responsibility pattern to handle different aspects of a player's move in the game.

- **GameRepository:** Interface for MongoDB interaction, allowing the storage and retrieval of game data.

### Frontend (React UI)

The frontend is built using React, offering a dynamic and interactive user interface for playing Mancala Kahala.

- **GameBoard Component:** Displays the game board, allowing players to make moves and interact with the game.

- **App:** Communicates with the backend via RESTful API calls to handle game-related actions.

## How to Run

Follow these steps to run the Mancala Kahala Game:

### Prerequisites

- **Docker:** Ensure that the docker is installed on your system.

### Backend

1. Clone the repository:

    ```bash
    git clone https://github.com/AdnanSiddique/mancala-kalaha.git
    cd mancala-kahala
    ```
2. Build mancala-kalaha-service: 


- Change the directory to **mancala-kalaha-service** and run ```bash ./mvnw clean install or .\mvnw clean install (Windows) ```. This will take a while to download the dependencies and build the jar file.



3. Run docker compose file:

    ```bash
    docker-compose -f docker-compose.yaml up
    ```
   
   The React application will be accessible at `http://localhost:3000`.

Now, you can access the Mancala Kahala Game!


## Author/Developer
- [Adnan Siddiq](https://www.linkedin.com/in/adnans/) 