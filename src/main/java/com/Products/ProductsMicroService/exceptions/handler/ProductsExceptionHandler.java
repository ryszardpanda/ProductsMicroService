package com.Products.ProductsMicroService.exceptions.handler;

import com.Products.ProductsMicroService.exceptions.ErrorMessage;
import com.Products.ProductsMicroService.exceptions.MissingConfigurationException;
import com.Products.ProductsMicroService.exceptions.NoIdNumberException;
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

    @ExceptionHandler({MissingConfigurationException.class})
    public ResponseEntity<ErrorMessage> handleMissingConfigurationException(
            MissingConfigurationException ex) {
        return new ResponseEntity<ErrorMessage>(
                new ErrorMessage(ex.getMessage()), new HttpHeaders(), ex.getHttpStatus());
    }
}
