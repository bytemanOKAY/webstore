package com.webstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.webstore.entity.User;
import com.webstore.service.UserService;

@Controller
@RequestMapping("/register")
public class RegisterController {
	@Autowired
	private UserService userService;
	
	@ModelAttribute("user")
	public User contruct(){
		return new User();
	}
	
	@RequestMapping
	public String showRegister() {
		return "user-register";
	}
	@RequestMapping(method = RequestMethod.POST)
	public String doRegister(@Valid @ModelAttribute("user") User user, BindingResult result,  RedirectAttributes redirectAttributes) {
		if(result.hasErrors()){
			return "user-register";
		}
		
		userService.addUser(user);
		redirectAttributes.addFlashAttribute("success", true);
		return "redirect:/register.html";
	}
	
	@RequestMapping("/available")
	@ResponseBody
	public String available(@RequestParam String username){
		Boolean available = userService.findOne(username) == null;
		return available.toString();
	}
}
