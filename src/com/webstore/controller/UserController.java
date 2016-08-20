package com.webstore.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.webstore.entity.Item;
import com.webstore.entity.User;
import com.webstore.service.ItemService;
import com.webstore.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/cart")
	public String showCart(Model model, Principal principal){
		String name = principal.getName();
		model.addAttribute("user", userService.findOne(name));
		return "user-cart";
	}
	
	@RequestMapping("/cart/add/{id}")
	public String addItemToCart(Model model, Principal principal,  @PathVariable int id,  RedirectAttributes redirectAttributes){
		User user = userService.findOne(principal.getName());
		Item item = itemService.findOne(id);
		
		user.getCart().add(item);
		userService.save(user);
		
		redirectAttributes.addFlashAttribute("success", true);
		return "redirect:/items/" + id + ".html";
	}
		
	@RequestMapping("/cart/remove/{id}")
	public String removeItemToCart(Model model, Principal principal,  @PathVariable int id){
		User user = userService.findOne(principal.getName());
		Item item = itemService.findOne(id);
		
		user.getCart().remove(item);
		userService.save(user);
		
		return "redirect:/cart.html";
	}
	
//	@RequestMapping("/account")
//	public String account(Model model, Principal principal){
//		String name = principal.getName();
//		model.addAttribute("user", userService.findOneByName(name));
//		return "user-detail";
//	}
//	@RequestMapping(value = "/account", method = RequestMethod.POST)
//	public String account(@Valid @ModelAttribute("user") User user, BindingResult result,  RedirectAttributes redirectAttributes, Principal principal){
//		if(result.hasErrors()){
//			return "user-detail";
//		}
//		User oldUserName = userService.findOneByName(principal.getName());
//		oldUserName.setName(user.getName());
//		oldUserName.setEmail(user.getEmail());
//		oldUserName.setPassword(user.getPassword());
//		
//		
//		userService.save(user);
//		redirectAttributes.addFlashAttribute("success", true);
//		return "redirect:/account.html";
//	}
}
