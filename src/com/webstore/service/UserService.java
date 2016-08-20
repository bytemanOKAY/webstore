package com.webstore.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.webstore.entity.Role;
import com.webstore.entity.User;


@Service
@Transactional
public class UserService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private RoleService roleService;
	

	public List<User> findAll(){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> from = criteriaQuery.from(User.class);
        
        CriteriaQuery<User> selectAll = criteriaQuery.select(from);
        
        TypedQuery<User> allQuery = entityManager.createQuery(selectAll);
        
        return allQuery.getResultList();
	}

	public void addUser(User user) {
		user.setEnabled(true);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		List<Role> roles = new ArrayList<>();
		
		roles.add(roleService.findByName("ROLE_USER"));
		user.setRoles(roles);
		
		save(user);
	}

	public User findOne(String username) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> from = criteriaQuery.from(User.class);
		
		CriteriaQuery<User> select = criteriaQuery.select(from);
		select.where(criteriaBuilder.equal(from.get("name"), username));
		
		return entityManager.createQuery(select).getSingleResult();
	}
	public User findOne(int id) {
		return entityManager.find(User.class, id);
	}	
	@Transactional
	public void save(User user){
		if(user.getId() == null){
			entityManager.persist(user);
		} else{
			entityManager.merge(user);
		}
	}
	@Transactional
	public void delete(User user)throws Exception{
		if(findOne(user.getId()) == null){
			throw new Exception("Given user does not exist.");
		}else{
			entityManager.remove(user);
		}
	}
}
