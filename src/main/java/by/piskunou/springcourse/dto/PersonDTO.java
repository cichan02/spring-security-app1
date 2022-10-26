package by.piskunou.springcourse.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonDTO {
	@NotBlank
	@Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов")
	private String username;
	
	@Min(value = 1900, message = "Год рождения должен быть больше, чем 1900")
	private int yearOfBirth;

	private String password;
}
