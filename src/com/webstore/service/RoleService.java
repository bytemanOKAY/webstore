package com.webstore.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webstore.entity.Role;

@Service
public class RoleService {
	@PersistenceContext
	private EntityManager entityManager;

	public Role findByName(String name) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
		Root<Role> from = criteriaQuery.from(Role.class);

		CriteriaQuery<Role> select = criteriaQuery.select(from);
		select.where(criteriaBuilder.equal(from.get("name"), name));

		TypedQuery<Role> selectRole = entityManager.createQuery(select); 
		Role role = null;
		try{
			role = selectRole.getSingleResult();
		}catch (NoResultException e) {

		}
		return role;
	}
	
	@Transactional
	public void save(Role role) {
		if (role.getId() == null) {
			entityManager.persist(role);
			
		} else {
			entityManager.merge(role);
		}
	}
}
