package by.piskunou.springcourse.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.piskunou.springcourse.models.Person;
import by.piskunou.springcourse.repositories.PeopleRepository;

@Service
@Transactional(readOnly = true)
public class PeopleService {
	private final PeopleRepository peopleRepo;

	@Autowired
	public PeopleService(PeopleRepository peopleRepo) {
		this.peopleRepo = peopleRepo;
	}
	
	public Optional<Person> findDistinctByIdNotAndUsername(int id, String username) {
		return peopleRepo.findDistinctByIdNotAndUsername(id, username);
	}
	
	@Transactional
	public void register(Person person) {
		peopleRepo.save(person);
	}
}
