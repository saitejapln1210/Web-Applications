package com.epam.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private int id;
	private String userName;
	private String email;
	private String password;

	public UserDto(String userName, String email, String password) {
		this.userName = userName;
		this.email = email;
		this.password = password;
	}
}
