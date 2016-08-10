package com.webstore.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.webstore.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {

	List<Item> findByNameContaining(String name);
	List<Item> findByNameContaining(String name, Pageable pageable);
	Page<Item> findAll(Pageable pageable);
	Item findByName(String username);
}
