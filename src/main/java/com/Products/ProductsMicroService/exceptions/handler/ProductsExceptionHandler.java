package com.Products.ProductsMicroService.exceptions.handler;

import com.Products.ProductsMicroService.exceptions.ErrorMessage;
import com.Products.ProductsMicroService.exceptions.MissingConfigurationException;
import com.Products.ProductsMicroService.exceptions.NoIdNumberException;
import com.Products.ProductsMicroService.exceptions.WrongTypeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ProductsExceptionHandler {

    @ExceptionHandler({NoIdNumberException.class})
    public ResponseEntity<ErrorMessage> handlePatientNotFoundException(
            NoIdNumberException ex) {
        return new ResponseEntity<ErrorMessage>(
                new ErrorMessage(ex.getMessage()), new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler({WrongTypeException.class})
    public ResponseEntity<ErrorMessage> handleWrongTypeException(
            WrongTypeException ex) {
        return new ResponseEntity<ErrorMessage>(
                new ErrorMessage(ex.getMessage()), new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler({MissingConfigurationException.class})
    public ResponseEntity<ErrorMessage> handleMissingConfigurationException(
            MissingConfigurationException ex) {
        return new ResponseEntity<ErrorMessage>(
                new ErrorMessage(ex.getMessage()), new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
