package com.smart.controllers;

import java.util.logging.Handler;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;
import antlr.debug.NewLineListener;
import net.bytebuddy.asm.Advice.This;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;

//Handler for Home
@RequestMapping("/")
public String home(Model model) {
	model.addAttribute("title","Home - Smart Contact Manager");
	return "home";
}

//Handler for About
@RequestMapping("/about")
public String about(Model model) {
	model.addAttribute("title","about");
	return "about";
}	

//Handler for Signup page
@RequestMapping("/signup")
public String signUp(Model model) {
	
	model.addAttribute("title","Register - Smart Contact Manager");
	model.addAttribute("user", new User());
	
	return "signup";
}

//handler for login page
@RequestMapping("/signin")
public String signIn(Model model) 
{
	model.addAttribute("title","Login to enjoy our services...");
	return "login";
	}

//Handler for registering user
@RequestMapping(value = "/do_register",method = RequestMethod.POST)
public String do_register(@Valid @ModelAttribute("user") User user,BindingResult result1 ,@RequestParam(value="agreement", defaultValue="false") boolean agreement,Model model,HttpSession session) {
	
try {	
	if (!agreement)
	{
		System.out.println("not working because of agreement..");
		throw new Exception("Please agree terms and conditions.");
		}
	
	System.out.println("USER:- "+user.toString());

	System.out.println("error   -    " + result1.toString());
	
	if(result1.hasErrors()) {
		
		System.out.println("error" + result1.toString());
		model.addAttribute("user",user);
		return "signup" ;
	}

	user.setRole("ROLE_USER");
	user.setEnabled(true);
	user.setImageUrl("default.png");
	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

	User result=  this.userRepository.save(user);
	model.addAttribute("user",new User());
	
	session.setAttribute("message", new Message("Successfully Registered !!", "alert-success") );

}

catch (Exception e) {
	// TODO: handle exception
	e.printStackTrace();
	model.addAttribute("user",user);
	session.setAttribute("message", new Message("Something went wrong !!  " +e.getMessage(),"alert-danger"));
}
	return "signup";
}


	
}
