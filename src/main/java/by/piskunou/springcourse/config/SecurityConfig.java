package by.piskunou.springcourse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import by.piskunou.springcourse.services.PersonDetailsService;

import static by.piskunou.springcourse.models.Role.ROLE_ADMIN;
import static by.piskunou.springcourse.models.Role.ROLE_USER;

@SuppressWarnings("deprecation")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String AUTH_LOGIN = "/auth/login";
	
	private final PersonDetailsService personDetailsService;
	
	@Autowired
	public SecurityConfig(PersonDetailsService personDetailsService) {
		this.personDetailsService = personDetailsService;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(AUTH_LOGIN, "/auth/registration", "/error").permitAll()
								.anyRequest().hasAnyAuthority(ROLE_ADMIN.toString(), ROLE_USER.toString())
			.and()
			.formLogin().loginPage(AUTH_LOGIN)
						.loginProcessingUrl("/process_login")
						.defaultSuccessUrl("/hello", true)
						.failureUrl("/auth/login?error")
			.and()
			.logout().logoutUrl("/logout")
					 .logoutSuccessUrl(AUTH_LOGIN);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(personDetailsService)
			.passwordEncoder(getPasswordEncoder());
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
