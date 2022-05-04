package com.epam.exceptionhandler;


import lombok.*;


@Data
@NoArgsConstructor
public class ErrorResponse {
    private String timestamp;
    private String status;
    private String error;
}

