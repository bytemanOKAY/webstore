package com.webstore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.webstore.entity.Role;
import com.webstore.entity.User;

import com.webstore.repository.RoleRepository;
import com.webstore.repository.UserRepository;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	//@Autowired
	//private ItemRepository itemRepository;
	
	public List<User> findAll(){
		return userRepository.findAll();
	}

	public void addUser(User user) {
		user.setEnabled(true);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		List<Role> roles = new ArrayList<>();
		
		roles.add(roleRepository.findByName("ROLE_USER"));
		user.setRoles(roles);
		userRepository.save(user);
	}

	public User findOne(String username) {
		return userRepository.findByName(username);
	}
	public User findOne(int id) {
		return userRepository.findOne(id);
	}	
//	public User findOneWithItems(int id){
//		User user = findOne(id);
//		
//		List<Item> cart = itemRepository.findBy
//		
//		return user;
//	}
//	
	public void save(User user){
		userRepository.save(user);
	}
	
}
