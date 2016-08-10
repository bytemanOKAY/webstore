package com.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webstore.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByName(String name);

}
