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
    void handleGameNotFoundException_ReturnsBadRequest() {
        GameNotFoundException exception = new GameNotFoundException("TEST");
        ResponseEntity<ErrorResponseDto> response = controllerExceptionHandler.handleGameNotFoundException(exception);
        assertErrorResponse(response, "Game not found with id TEST", HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleInvalidMoveException_ReturnsBadRequest() {
        InvalidMoveException exception = new InvalidMoveException(Player.A, 8);
        ResponseEntity<ErrorResponseDto> response = controllerExceptionHandler.handleInvalidMoveException(exception);
        assertErrorResponse(response, "Invalid move for Player A for pit 8", HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleGenericException_ReturnsInternalServerError() {
        Exception exception = new Exception();
        ResponseEntity<ErrorResponseDto> response = controllerExceptionHandler.handleGenericException(exception);
        assertErrorResponse(response, "java.lang.Exception Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void handleBadRequestException_ReturnsBadRequest() {
        Exception exception = new IllegalArgumentException();
        ResponseEntity<ErrorResponseDto> response = controllerExceptionHandler.handleBadRequestException(exception);
        assertErrorResponse(response, "java.lang.IllegalArgumentException Bad Request", HttpStatus.BAD_REQUEST);
    }

    private void assertErrorResponse(ResponseEntity<ErrorResponseDto> response, String expectedErrorMessage, HttpStatus statusCode) {
        assertEquals(statusCode, response.getStatusCode());
        assertEquals(expectedErrorMessage, Objects.requireNonNull(response.getBody()).errorMessage());
        assertEquals(statusCode, response.getBody().statusCode());
    }
}
