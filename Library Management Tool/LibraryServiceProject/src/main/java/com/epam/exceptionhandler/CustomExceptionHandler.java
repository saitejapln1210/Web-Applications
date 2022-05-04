package com.epam.exceptionhandler;
import com.epam.exceptions.UserBookLimitReachedException;
import com.epam.exceptions.UserHasAlreadyBookException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Date;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(FeignException e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(e.getMessage());
        errorResponse.setTimestamp(new Date().toString());
        errorResponse.setStatus(String.valueOf(e.status()));
        return new ResponseEntity<>(errorResponse,HttpStatus.valueOf(e.status()));
    }
    @ExceptionHandler({UserBookLimitReachedException.class, UserHasAlreadyBookException.class})
    public ResponseEntity<Object> handleException(Exception e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
