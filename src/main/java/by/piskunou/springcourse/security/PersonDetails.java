package by.piskunou.springcourse.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import by.piskunou.springcourse.models.Person;

public class PersonDetails implements UserDetails {
	private static final long serialVersionUID = -8785988283868815931L;
	
	private final transient Person person;
	
	@Autowired
	public PersonDetails(Person person) {
		this.person = person;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}

	@Override
	public String getPassword() {	
		return person.getPassword();
	}

	@Override
	public String getUsername() {	
		return person.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {	
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {	
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {	
		return true;
	}

	@Override
	public boolean isEnabled() {	
		return true;
	}

	public Person getPerson() {
		return person;
	}
}
