package com.smart.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.dao.UserRepository;
import com.smart.entities.User;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/index")
	public String dashboard(Model model,Principal principal) {
		
		String userNameString = principal.getName();

		//get user using username
		User usr =  userRepository.getUserByUsername(userNameString);
		
		model.addAttribute("usr",usr);
		
		return "normal/user_dashboard";
	}

	
}																	
