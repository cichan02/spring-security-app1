package by.piskunou.springcourse.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.piskunou.springcourse.models.Person;
import by.piskunou.springcourse.models.Role;
import by.piskunou.springcourse.repositories.PeopleRepository;

@Service
@Transactional(readOnly = true)
public class PeopleService {
	private final PeopleRepository peopleRepo;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public PeopleService(PeopleRepository peopleRepo, PasswordEncoder passwordEncoder) {
		this.peopleRepo = peopleRepo;
		this.passwordEncoder = passwordEncoder;
	}
	
	public Optional<Person> findDistinctByIdNotAndUsername(int id, String username) {
		return peopleRepo.findDistinctByIdNotAndUsername(id, username);
	}
	
	public Optional<Person> findByUsername(String username) {
		return peopleRepo.findByUsername(username);
	}
	
	@Transactional
	public void register(Person person) {
		person.setPassword(passwordEncoder.encode(person.getPassword()));
		person.setRole(Role.ROLE_USER);
		
		peopleRepo.save(person);
	}
}
