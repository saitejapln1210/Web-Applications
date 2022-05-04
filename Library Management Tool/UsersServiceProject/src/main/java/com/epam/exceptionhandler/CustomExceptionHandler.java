package com.epam.exceptionhandler;

import com.epam.exceptions.EmailAlreadyExists;
import com.epam.exceptions.UserDoesNotExistException;
import com.epam.exceptions.UserNameAlreadyExists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<FieldErrorMessage>> badMethodArgumentsException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors =e.getBindingResult().getFieldErrors();
        List<FieldErrorMessage> fieldErrorMessages =fieldErrors.stream().map(fieldError -> new FieldErrorMessage(fieldError.getField(),fieldError.getDefaultMessage())).collect(Collectors.toList());
        return new ResponseEntity<>(fieldErrorMessages,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({UserNameAlreadyExists.class,EmailAlreadyExists.class})
    public ResponseEntity<String> alreadyExistsExceptionHandler(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<String> notFoundExceptionHandler(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}