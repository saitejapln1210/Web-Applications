package com.epam.exceptions;

public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String msg) {
        super(msg);
    }
}