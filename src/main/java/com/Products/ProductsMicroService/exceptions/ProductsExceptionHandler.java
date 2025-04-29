package com.Products.ProductsMicroService.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductsExceptionHandler {

    @ExceptionHandler({NoIdNumberException.class})
    public ResponseEntity<ErrorMessage> handlePatientNotFoundException(
            NoIdNumberException ex) {
        return new ResponseEntity<ErrorMessage>(
                new ErrorMessage(ex.getMessage()), new HttpHeaders(), ex.getHttpStatus());
    }
}
