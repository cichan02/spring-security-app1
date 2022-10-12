package by.piskunou.springcourse.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import by.piskunou.springcourse.models.Person;
import by.piskunou.springcourse.repositories.PeopleRepository;
import by.piskunou.springcourse.security.PersonDetails;

@Service
public class PersonDetailsService implements UserDetailsService {
	private final PeopleRepository peopleRepository;

	@Autowired
	public PersonDetailsService(PeopleRepository peopleRepository) {
		this.peopleRepository = peopleRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {		
		Optional<Person> person = peopleRepository.findByUsername(username);
		if(person.isEmpty()) {
			throw new UsernameNotFoundException("User not found!");
		}
		return new PersonDetails(person.get());
	}
}
