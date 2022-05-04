package com.epam.exceptions;

public class AccountAlreadyExistException extends Exception {
    public AccountAlreadyExistException(String msg) {
        super(msg);
    }
}
