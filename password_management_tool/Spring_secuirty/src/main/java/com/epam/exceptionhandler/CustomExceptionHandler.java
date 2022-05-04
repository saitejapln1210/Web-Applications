package com.epam.exceptionhandler;
import com.epam.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@EnableWebMvc
public class CustomExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<FieldErrorMessage>> badMethodArgumentsException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors =e.getBindingResult().getFieldErrors();
        List<FieldErrorMessage> fieldErrorMessages =fieldErrors.stream().map(fieldError -> new FieldErrorMessage(fieldError.getField(),fieldError.getDefaultMessage())).collect(Collectors.toList());
        return new ResponseEntity<>(fieldErrorMessages,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({UserAlreadyExsistException.class, GroupAlreadyExsistException.class, AccountAlreadyExistException.class})
    public ResponseEntity<String> alreadyExistsExceptionHandler(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({GroupNotFoundException.class, UserDoesNotExistException.class,AccountNotFoundException.class})
    public ResponseEntity<String> notFoundExceptionHandler(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

}