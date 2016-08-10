package com.webstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.webstore.entity.Item;
import com.webstore.entity.Review;
import com.webstore.entity.User;
import com.webstore.repository.ItemRepository;
import com.webstore.repository.ReviewRepository;
import com.webstore.repository.UserRepository;

@Service
public class ReviewService {
	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private UserRepository userRepository;

	@PreAuthorize("hasRole('ROLE_USER')")
	public void save(Review review, int id, String name) {
		Item item = itemRepository.findOne(id);
		User user = userRepository.findByName(name);

		review.setItem(item);
		review.setUser(user);

		reviewRepository.save(review);
	}
}
