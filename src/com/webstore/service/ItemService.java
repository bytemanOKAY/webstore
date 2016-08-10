package com.webstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webstore.entity.Item;
import com.webstore.entity.Review;

import com.webstore.repository.ItemRepository;
import com.webstore.repository.ReviewRepository;

@Service
@Transactional
public class ItemService {
	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	public List<Item> findAll() {
		List<Item> items = itemRepository.findAll();

		for (Item item : items) {

			List<Review> reviews = reviewRepository.findByItem(item);
			setAvgPoint(item, reviews);

		}

		return items;
	}

	public List<Item> findAll(Pageable pageable) {
		List<Item> items = itemRepository.findAll(pageable).getContent();

		for (Item item : items) {

			List<Review> reviews = reviewRepository.findByItem(item);
			setAvgPoint(item, reviews);

		}

		return items;
	}

	public Item findById(int id) {
		return itemRepository.findOne(id);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void delete(Item item) {
		itemRepository.delete(item);
	}

	@Transactional
	public Item findOneWithReviews(int id) {
		Item item = findById(id);
		if (item != null) {
			List<Review> reviews = reviewRepository.findByItem(item);

			setAvgPoint(item, reviews);

			item.setReviews(reviews);
		}
		return item;
	}

	public void save(Item item) {
		itemRepository.save(item);
	}

	private void setAvgPoint(Item item, List<Review> reviews) {

		if (reviews != null && item != null) {
			int avgReviewPoint = 0;
			if (reviews.size() > 0) {
				for (Review review : reviews) {
					avgReviewPoint += review.getPoint();
				}
				avgReviewPoint /= reviews.size();
			}
			item.setPoint(avgReviewPoint);
		}
	}

	public Item findOne(String username) {
		return itemRepository.findByName(username);
	}

	public List<Item> findByNameContaining(String name) {
		return findByNameContaining(name, 0, 5);
	}

	public List<Item> findByNameContaining(String name, int page, int size) {
		return itemRepository.findByNameContaining(name, new PageRequest(page, size, Direction.ASC, "price"));
	}
}
