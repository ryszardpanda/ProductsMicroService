package com.Products.ProductsMicroService.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WrongTypeException extends RuntimeException{
    private HttpStatus httpStatus;

    public WrongTypeException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
