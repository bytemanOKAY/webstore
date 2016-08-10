package com.webstore.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.webstore.entity.Item;
import com.webstore.entity.Review;
import com.webstore.service.ItemService;
import com.webstore.service.ReviewService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;

	@Autowired
	private ReviewService reviewService;

	@ModelAttribute("review")
	public Review constructReview() {
		return new Review();
	}

	@RequestMapping(value = "/items/getImage", method = RequestMethod.GET)
	public void showImage(@RequestParam("id") int itemId, HttpServletResponse response) throws IOException, InterruptedException {
		Item item = itemService.findById(itemId);
		if (item != null) {
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
			response.getOutputStream().write(item.getImage());
			
			response.getOutputStream().close();
		}
	}

	@RequestMapping("/items/{itemId}")
	public String showItem(Model model, @PathVariable("itemId") int id) {
		model.addAttribute("item", itemService.findOneWithReviews(id));
		return "item-detail";
	}

	@RequestMapping(value = "/items/{itemId}", method = RequestMethod.POST)
	public String addReviewToItem(Model model, @Valid @ModelAttribute("review") Review review, BindingResult result,
			RedirectAttributes redirectAttributes, Principal principal, @PathVariable("itemId") int id) {
		if (result.hasErrors()) {
			return showItem(model, id);
		}
		System.out.println(review.getId());

		reviewService.save(review, id, principal.getName());

		return "redirect:/items/" + id + ".html";
	}

	// Encoding UTF-8 via get problems
	@RequestMapping(value = "/search", produces = MediaType.TEXT_HTML_VALUE, method = RequestMethod.GET)
	public ResponseEntity<String> searchAjax(@RequestParam(name = "q") String q) {
		List<Item> items = itemService.findByNameContaining(q, 0, 10);
		System.out.println(q);
		List<String> json = new ArrayList<>();

		for (Item item : items) {
			json.add(item.getName());
		}
		HttpHeaders h = new HttpHeaders();

		h.add("Content-type", "text/html;charset=UTF-8");
		return new ResponseEntity<String>(new Gson().toJson(json), h, HttpStatus.OK);
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(Model model, @RequestParam(name = "search") String search) {
		List<Item> items = itemService.findByNameContaining(search, 0, 100);

		for (Item item : items) {
			System.out.println(item.getName());
		}

		model.addAttribute("items", items);
		return "index";
	}

	// Encoding UTF-8 via get problems
	@RequestMapping(value = "/items/available", method = RequestMethod.GET)
	@ResponseBody
	public String available(@RequestParam(name = "name") String name) {
		Boolean available = itemService.findOne(name) == null;
		return available.toString();
	}
}
