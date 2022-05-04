package com.epam.exceptions;

public class UserAlreadyExsistException extends Exception {
    public UserAlreadyExsistException(String msg) {
        super(msg);
    }
}
