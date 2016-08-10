package com.webstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webstore.entity.Item;
import com.webstore.entity.Review;
import com.webstore.entity.User;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	List<Review> findByItem(Item item);
	List<Review> findByUser(User user);
}
