package com.webstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.webstore.entity.Item;
import com.webstore.entity.User;
import com.webstore.service.ItemService;
import com.webstore.service.UserService;

@Controller
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private ItemService itemService;

	@ModelAttribute("item")
	public Item constructItem() {
		return new Item();
	}

	@RequestMapping("/users")
	public String users(Model model) {
		model.addAttribute("users", userService.findAll());
		return "users";
	}

	@RequestMapping("/users/{id}")
	public String detail(Model model, @PathVariable int id) {
		model.addAttribute("user", userService.findOne(id));
		return "user-detail";
	}

	@RequestMapping("/users/block/{id}")
	public String blockUser(Model model, @PathVariable int id) {

		User user = userService.findOne(id);

		user.setEnabled(false);
		userService.save(user);
		return "redirect:/users.html";
	}

	@RequestMapping("/users/unblock/{id}")
	public String unBlockUser(Model model, @PathVariable int id) {

		User user = userService.findOne(id);

		user.setEnabled(true);
		userService.save(user);
		return "redirect:/users.html";
	}

	@RequestMapping("/items/add")
	public String showItemAdd() {
		return "item-add";
	}

	@RequestMapping(value = "/items/add", method = RequestMethod.POST)
	public String doItemAdd(@Valid @ModelAttribute("item") Item item,
			@RequestParam(name = "imageFile") CommonsMultipartFile imageFile, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "item-add";
		}

		item.setImage(imageFile.getBytes());

		itemService.save(item);
		redirectAttributes.addFlashAttribute("success", true);
		return "redirect:/items/add.html";
	}

	@RequestMapping("/items/edit/{id}")
	public String showItemEdit(Model model, @PathVariable("id") int id) {
		model.addAttribute("item", itemService.findOne(id));
		return "item-edit";
	}

	@RequestMapping(value = "/items/edit/{id}", method = RequestMethod.POST)
	public String doItemEdit(@Valid @ModelAttribute("item") Item item, @PathVariable("id") int id,
			@RequestParam(name = "imageFile", required = false) CommonsMultipartFile imageFile, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "item-edit";
		}
		
		if(imageFile != null && !imageFile.isEmpty()){
			item.setImage(imageFile.getBytes());
		}
		
		itemService.save(item);
		redirectAttributes.addFlashAttribute("success", true);

		return "redirect:/items/edit/" + id + ".html";
	}

	@RequestMapping("items/remove/{id}")
	public String removeItem(@PathVariable int id) throws Exception {
		Item item = itemService.findOne(id);
		if (item == null) {
			// TODO item remove error message
		} else {
			itemService.delete(item);
		}
		return "redirect:/index.html";
	}

}
