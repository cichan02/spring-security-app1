package by.piskunou.springcourse.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Person")
public class Person {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank
	@Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов")
	@Column(name = "username")
	private String username;
	
	@Min(value = 1900, message = "Год рождения должен быть больше, чем 1900")
	@Column(name = "year_of_birth")
	private int yearOfBirth;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Role role;

	public Person(String username, int yearOfBirth) {
		this.username = username;
		this.yearOfBirth = yearOfBirth;
	}
}
