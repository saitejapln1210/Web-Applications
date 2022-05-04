package com.epam.dto;
import com.epam.constants.RegexConstants;
import com.epam.entity.Group;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
@NoArgsConstructor
public class AccountDto {
    private int id;

    @Pattern(regexp = RegexConstants.USER_NAME_REGEX,message = "invalid account name")
    @NotNull(message = "account username cannot be null")
    @NotBlank(message = "account username cannot be blank")
    private String username;

    @Pattern(regexp = RegexConstants.PASSWORD_REGEX,message = "invalid account password")
    @NotNull(message = "account password cannot be null")
    @NotBlank(message = "account password cannot be blank")
    private String password;

    @Pattern(regexp = RegexConstants.URL_REGEX,message = "invalid account url")
    @NotNull(message = "account url cannot be null")
    @NotBlank(message = "account url cannot be blank")
    private String url;


    private Group group;

    public AccountDto(String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;
    }
}
