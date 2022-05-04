package com.epam.dto;

import com.epam.constants.RegexConstants;
import com.epam.entity.Account;
import com.epam.entity.User;

import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
public class GroupDto {
    private int id;

    @Pattern(regexp = RegexConstants.USER_NAME_REGEX,message = "invalid group name")
    @NotNull(message ="group name cannot be blank" )
    @NotBlank(message = "group name cannot be blank")
    private String name;

    private List<Account> accounts;
    private User user;
    public GroupDto(String name) {
        this.name = name;
    }


}

