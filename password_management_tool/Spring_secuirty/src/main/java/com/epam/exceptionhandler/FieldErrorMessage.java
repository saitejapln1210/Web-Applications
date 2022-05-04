package com.epam.exceptionhandler;

import lombok.*;

@Getter
@AllArgsConstructor
public class FieldErrorMessage {
    private String field;
    private String errorMessage;
}
