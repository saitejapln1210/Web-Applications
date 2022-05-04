package com.epam.dto;

import com.epam.constants.Constants;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	private int id;
	
	@Pattern(regexp = Constants.USER_NAME_REGEX, message="Invalid Username")
	@NotNull(message = "username cannot be null")
	@NotBlank(message = "username cannot be blank")
	private String userName;
	
	@Pattern(regexp = Constants.EMAIL_REGEX, message="Invalid email")
	@NotNull(message = "email cannot be null")
	@NotBlank(message = "email cannot be blank")
	private String email;
	
	@Pattern(regexp = Constants.PASSWORD_REGEX, message="Invalid Password")
	@NotNull(message = "password cannot be null")
	@NotBlank(message = "password cannot be blank")
	private String password;

	public UserDto(String userName, String email, String password) {
		this.userName = userName;
		this.email = email;
		this.password = password;
	}
}
