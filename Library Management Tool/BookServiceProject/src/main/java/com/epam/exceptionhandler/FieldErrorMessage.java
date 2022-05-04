package com.epam.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class FieldErrorMessage {
    private String field;
    private String errorMessage;
}
