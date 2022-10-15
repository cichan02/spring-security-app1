package by.piskunou.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import by.piskunou.springcourse.models.Person;
import by.piskunou.springcourse.services.PeopleService;

@Component
public class PersonValidator implements Validator {
	private final PeopleService peopleService;
	
	@Autowired
	public PersonValidator(PeopleService peopleService) {
		this.peopleService = peopleService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Person person = (Person)target;
		
		if(peopleService.findDistinctByIdNotAndUsername(person.getId(), person.getUsername()).isPresent()) {
			errors.rejectValue("username", "5*", "This username has already taken");
		}
	}
}
