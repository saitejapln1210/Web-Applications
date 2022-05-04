package com.epam.dto;

import com.epam.constants.RegexConstants;

import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;



@Data

@NoArgsConstructor
public class UserDto {
    private int id;
    @Pattern(regexp = RegexConstants.USER_NAME_REGEX,message = "invalid master username")
    @NotNull(message = "master username cannot be null")
    @NotBlank(message = "master username cannot be null")
    private String masterUserName;

    @Pattern(regexp = RegexConstants.PASSWORD_REGEX,message = "invalid password")
    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password cannot be null")
    private String masterPassword;


    public UserDto(String masterUserName, String masterPassword) {
        this.masterUserName = masterUserName;
        this.masterPassword = masterPassword;
    }
}
