package by.piskunou.springcourse.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationDTO {
	@NotBlank
	@Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов")
	private String username;
	
	private String password;
}
