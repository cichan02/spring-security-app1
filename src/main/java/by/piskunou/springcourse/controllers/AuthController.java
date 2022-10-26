package by.piskunou.springcourse.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.piskunou.springcourse.dto.AuthenticationDTO;
import by.piskunou.springcourse.dto.PersonDTO;
import by.piskunou.springcourse.models.Person;
import by.piskunou.springcourse.security.JWTUtil;
import by.piskunou.springcourse.services.PeopleService;
import by.piskunou.springcourse.util.PersonErrorResponse;
import by.piskunou.springcourse.util.PersonNotRegistratedException;
import by.piskunou.springcourse.util.PersonValidator;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final PersonValidator personValidator;
	private final PeopleService peopleService;
	private final JWTUtil jwtUtil;
	private final ModelMapper modelMapper;
	private final AuthenticationManager authenticationManager;

	@Autowired
	public AuthController(PersonValidator personValidator, PeopleService peopleService, JWTUtil jwtUtil,
						  ModelMapper modelMapper, AuthenticationManager authenticationManager) {
		this.personValidator = personValidator;
		this.peopleService = peopleService;
		this.jwtUtil = jwtUtil;
		this.modelMapper = modelMapper;
		this.authenticationManager = authenticationManager;
	}
	
	@PostMapping("/registration")
	public ResponseEntity<Map<String, String>> performRegistration(@RequestBody @Valid PersonDTO personDTO,
			BindingResult bindingResult) throws PersonNotRegistratedException {
		Person person = convertToPerson(personDTO);
		
		personValidator.validate(person, bindingResult);
		if(bindingResult.hasErrors()) {
			StringBuilder errorMessage = new StringBuilder();
			
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				errorMessage.append(error.getField() + " - " + error.getDefaultMessage() + " ");
			}
			
			throw new PersonNotRegistratedException(errorMessage.toString());
		}
		
		peopleService.register(person);
		
		String token = jwtUtil.generateToken(person.getUsername());
	
		return new ResponseEntity<>(Map.of("jwt-token", token), HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> performLogin(@RequestBody @Valid AuthenticationDTO authDTO,
															BindingResult bindingResult) throws AuthenticationException {
		if(bindingResult.hasErrors()) {
			StringBuilder errorMessage = new StringBuilder();
			
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				errorMessage.append(error.getField() + " - " + error.getDefaultMessage() + " ");
			}
			
			throw new BadCredentialsException(errorMessage.toString());
		}		
		
		UsernamePasswordAuthenticationToken authInputToken = 
				new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword());
		authenticationManager.authenticate(authInputToken);
		
		String token = jwtUtil.generateToken(authDTO.getUsername());
		
		return new ResponseEntity<>(Map.of("jwt-token", token), HttpStatus.OK);
	}
	
	@ExceptionHandler
	private ResponseEntity<PersonErrorResponse> handleException(PersonNotRegistratedException personNotRegistratedException) {
		PersonErrorResponse response = new PersonErrorResponse(personNotRegistratedException.getMessage(),
															   System.currentTimeMillis());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	private ResponseEntity<Map<String, String>> handleException(AuthenticationException authException) {
		return new ResponseEntity<>(Map.of("message", authException.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	private Person convertToPerson(PersonDTO personDTO) {
		return modelMapper.map(personDTO, Person.class);
	}
}
