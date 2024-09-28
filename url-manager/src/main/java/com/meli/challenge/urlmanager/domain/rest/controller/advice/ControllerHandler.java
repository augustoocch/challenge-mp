package com.meli.challenge.urlmanager.domain.rest.controller.advice;

import com.meli.challenge.urlmanager.model.exception.ExceptionDto;
import com.meli.challenge.urlmanager.model.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ExceptionDto> handleServiceException(ServiceException ex) {
        ExceptionDto errorResponse = new ExceptionDto(ex.getMessage(), ex.getCode());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleGeneralException(Exception ex) {
        ExceptionDto errorResponse = new ExceptionDto("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}