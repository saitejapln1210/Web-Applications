package com.epam.exceptions;

public class UserBookLimitReachedException extends Exception {
    public UserBookLimitReachedException(String s) {
        super(s);
    }
}
