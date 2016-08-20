package com.webstore.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webstore.entity.Item;
import com.webstore.entity.Review;
import com.webstore.entity.User;

@Service
@Transactional
public class ReviewService {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ItemService itemService;
	@Autowired
	private UserService userService;

	@PreAuthorize("hasRole('ROLE_USER')")
	public void addReview(Review review, int itemId, String userName) {
		Item item = itemService.findOne(itemId);
		User user = userService.findOne(userName);

		review.setItem(item);
		review.setUser(user);

		save(review);
	}
	@Transactional
	public void save(Review review) {
		if (review.getId() == null) {
			entityManager.persist(review);
		} else {
			entityManager.merge(review);
		}
	}
	public int getItemAvgRate(Item item){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
		Root<Review> from = criteriaQuery.from(Review.class);
		
		Expression<Double> avgExp = criteriaBuilder.avg(from.get("point"));
		
		CriteriaQuery<Double> select = criteriaQuery.select(avgExp);
		Predicate itemMatch = criteriaBuilder.equal(from.get("item"), item);
		select.where(itemMatch);
		
		Double avg = entityManager.createQuery(select).getSingleResult();
		if(avg != null){
			return avg.intValue();
		}
		return 0;
	}
	public List<Review> findByItem(Item item){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
		Root<Review> from = criteriaQuery.from(Review.class);
		
		CriteriaQuery<Review> select = criteriaQuery.select(from);
		Predicate whereItem = criteriaBuilder.equal(from.get("item"), item);
		select.where(whereItem);
		
		TypedQuery<Review> selectReviewsByItem = entityManager.createQuery(select);
		
		// must throw NoResultException, but it doesnt
		return selectReviewsByItem.getResultList();
	}
}
