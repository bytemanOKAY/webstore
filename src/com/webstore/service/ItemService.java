package com.webstore.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webstore.entity.Item;
import com.webstore.entity.Review;

@Service
@Transactional
public class ItemService {

	private static final Logger logger = Logger.getLogger(ItemService.class);
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ReviewService reviewService;

	public List<Item> findAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		countQuery.select(criteriaBuilder.count(countQuery.from(Item.class)));
		Long count = entityManager.createQuery(countQuery).getSingleResult();

		return findAll(1, count.intValue());
	}

	public List<Item> findAll(int page, int pageSize) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Item> criteriaQuery = criteriaBuilder.createQuery(Item.class);
		Root<Item> from = criteriaQuery.from(Item.class);

		CriteriaQuery<Item> selectAll = criteriaQuery.select(from);

		TypedQuery<Item> allQuery = entityManager.createQuery(selectAll);
		allQuery.setFirstResult((page - 1) * pageSize);
		allQuery.setMaxResults(pageSize);
		List<Item> items = allQuery.getResultList();
		
		for (Item item : items) {
			item.setPoint(reviewService.getItemAvgRate(item));
		}
		
		return items;
	}

	public Item findOne(int id) {
		return entityManager.find(Item.class, id);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Transactional
	public void delete(Item item) throws Exception {
		if (findOne(item.getId()) == null) {
			throw new Exception("Given item does not exist.");
		} else {
			entityManager.remove(item);
		}
	}

	@Transactional
	public Item findOneWithReviews(int id) {
		Item item = findOne(id);
		if (item != null) {
			List<Review> reviews = reviewService.findByItem(item);

			item.setPoint(reviewService.getItemAvgRate(item));

			item.setReviews(reviews);
		}
		return item;
	}
	@Transactional
	public void save(Item item) {
		if (item.getId() == null) {
			entityManager.persist(item);
		} else {
			entityManager.merge(item);
		}
	}

	public Item findOne(String name) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Item> criteriaQuery = criteriaBuilder.createQuery(Item.class);
		Root<Item> from = criteriaQuery.from(Item.class);
		CriteriaQuery<Item> select = criteriaQuery.select(from);

		select.where(criteriaBuilder.equal(from.get("name"), name));

		Item item = null;
		try{
			item = entityManager.createQuery(select).getSingleResult();
		}catch (NoResultException e) {

		}
		
		return item;
	}

	public List<Item> findByNameContaining(String name) {
		return findByNameContaining(name, 1, 5);
	}

	public List<Item> findByNameContaining(String name, int page, int pageSize) {
		if(page < 1 || pageSize < 1){ 
			logger.error("Bad pagination values (page = " + page + ", pageSize = " + pageSize, new Exception("Bad pagination values"));
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Item> criteriaQuery = criteriaBuilder.createQuery(Item.class);
		Root<Item> from = criteriaQuery.from(Item.class);
		CriteriaQuery<Item> select = criteriaQuery.select(from);

		
		Predicate nameContaining = criteriaBuilder.like(from.get("name"), "%" + name + "%");
		select.where(nameContaining);
		select.orderBy(criteriaBuilder.desc(from.get("price")));
		
		TypedQuery<Item> selectItem = entityManager.createQuery(select);
		selectItem.setFirstResult((page - 1) * pageSize);
		selectItem.setMaxResults(pageSize);
		

		return selectItem.getResultList();
	}
}
