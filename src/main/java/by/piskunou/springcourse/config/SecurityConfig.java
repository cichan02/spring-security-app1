package by.piskunou.springcourse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import by.piskunou.springcourse.services.PersonDetailsService;

import static by.piskunou.springcourse.models.Role.ROLE_ADMIN;
import static by.piskunou.springcourse.models.Role.ROLE_USER;

@SuppressWarnings("deprecation")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String AUTH_LOGIN = "/auth/login";
	
	private final PersonDetailsService personDetailsService;
	private final JWTFilter jwtFilter;
	
	@Autowired
	public SecurityConfig(PersonDetailsService personDetailsService, JWTFilter jwtFilter) {
		this.personDetailsService = personDetailsService;
		this.jwtFilter = jwtFilter;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests().antMatchers(AUTH_LOGIN, "/auth/registration", "/error").permitAll()
								.anyRequest().hasAnyAuthority(ROLE_ADMIN.toString(), ROLE_USER.toString())
			.and()
			.formLogin().loginPage(AUTH_LOGIN)
						.loginProcessingUrl("/process_login")
						.defaultSuccessUrl("/hello", true)
						.failureUrl("/auth/login?error")
			.and()
			.logout().logoutUrl("/logout")
					 .logoutSuccessUrl(AUTH_LOGIN)
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
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
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
