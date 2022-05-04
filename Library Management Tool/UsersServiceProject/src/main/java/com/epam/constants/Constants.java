package com.epam.constants;

public class Constants {
    private Constants() {
    }

    public static final String USER_NAME_REGEX ="^[a-z0-9_]{5,20}$";
    public static final String PASSWORD_REGEX ="^(?=.*[0-9])"
                                                    + "(?=.*[a-z])(?=.*[A-Z])"
                                                    + "(?=.*[@#$%^&+=])"
                                                    + "(?=\\S+$).{8,20}$";
    public static final String EMAIL_REGEX ="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
}
