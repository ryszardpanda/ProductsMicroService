package com.Products.ProductsMicroService.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MissingConfigurationException extends RuntimeException {
    private final HttpStatus httpStatus;

    public MissingConfigurationException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
