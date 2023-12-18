package com.mancalakalaha.controller.advice;

import com.mancalakalaha.dto.ErrorResponseDto;
import com.mancalakalaha.exception.GameNotFoundException;
import com.mancalakalaha.exception.InvalidMoveException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(value = GameNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleGameNotFoundException(GameNotFoundException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);
        log.error("Error response : {}", errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidMoveException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidMoveException(InvalidMoveException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);
        log.error("Error response : {}", errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleRuntimeException(RuntimeException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);
        log.error("Error response : {}", errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex) {
        String error = Optional.ofNullable(ex.getMessage()).orElse(ex.getClass().getName()).concat(" Internal Server Error");
        ErrorResponseDto errorResponse = new ErrorResponseDto(error, HttpStatus.INTERNAL_SERVER_ERROR);
        log.error("Error response : {}", errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ErrorResponseDto> handleBadRequestException(Exception ex) {
        String error = Optional.ofNullable(ex.getMessage()).orElse(ex.getClass().getName()).concat(" Bad Request");
        ErrorResponseDto errorResponse = new ErrorResponseDto(error, HttpStatus.BAD_REQUEST);
        log.error("Error response : {}", errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
