package by.piskunou.springcourse.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import by.piskunou.springcourse.security.PersonDetails;
import by.piskunou.springcourse.services.AdminService;

@Controller
public class HelloController {
	private final AdminService adminService;
	
	@Autowired
	public HelloController(AdminService adminService) {
		this.adminService = adminService;
	}

	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
	
	@GetMapping("/show-user-info")
	@ResponseBody
	public Map<String, String> info() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		PersonDetails personDetails = (PersonDetails)authentication.getPrincipal();
		
		return Map.of("username", personDetails.getUsername());
	}
	
	@GetMapping("/admin")
	public String adminPage() {
		adminService.doAdminStuff();
		return "admin";
	}
}
