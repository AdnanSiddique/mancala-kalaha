package com.mancalakalaha.controller.advice;

import com.mancalakalaha.constant.Player;
import com.mancalakalaha.dto.ErrorResponseDto;
import com.mancalakalaha.exception.GameNotFoundException;
import com.mancalakalaha.exception.InvalidMoveException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ControllerExceptionHandlerTest {

    @InjectMocks
    private ControllerExceptionHandler controllerExceptionHandler;

    @Test
    void handleGameNotFoundException() {
        GameNotFoundException exception = new GameNotFoundException("TEST");

        ResponseEntity<ErrorResponseDto> responseEntity = controllerExceptionHandler.handleGameNotFoundException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Game not found with id TEST", Objects.requireNonNull(responseEntity.getBody()).errorMessage());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getBody().statusCode());
    }

    @Test
    void handleInvalidMoveException() {
        InvalidMoveException exception = new InvalidMoveException(Player.A, 8);

        ResponseEntity<ErrorResponseDto> responseEntity = controllerExceptionHandler.handleInvalidMoveException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid move for Player A for pit 8", Objects.requireNonNull(responseEntity.getBody()).errorMessage());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getBody().statusCode());
    }
}
