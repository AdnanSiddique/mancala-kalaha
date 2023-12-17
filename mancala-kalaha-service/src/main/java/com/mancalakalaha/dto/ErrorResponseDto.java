package com.mancalakalaha.dto;

import org.springframework.http.HttpStatusCode;

public record ErrorResponseDto (String errorMessage, HttpStatusCode statusCode) {
}
