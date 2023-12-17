package com.mancalakalaha.exception;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String gameId){
        super(String.format("Game not found with id %s", gameId));
    }
}
