package com.mancalakalaha.controller.advice;

import com.mancalakalaha.dto.ErrorResponseDto;
import com.mancalakalaha.exception.GameNotFoundException;
import com.mancalakalaha.exception.InvalidMoveException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

}
