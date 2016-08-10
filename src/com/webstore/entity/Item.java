package com.webstore.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = Integer.MAX_VALUE)
	private byte[] image;

	@Size(min = 5, message = "Name must be at least 5 characters")
	private String name;

	@Size(min = 5, message = "description must be at least 5 characters")
	//@Lob
	@Column(length = 4000)
	private String descriptionShort;

	@Size(min = 5, message = "description must be at least 5 characters")
	//@Lob
	@Column(length = 4000)
	private String descriptionLong;

	// @Column(columnDefinition = "DECIMAL(15,2)")
	@Digits(integer = 6, fraction = 2, message = "Smth happened")
	@DecimalMin("1.00")
	private Integer price;

	@OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)
	private List<Review> reviews;

	@ManyToMany(mappedBy = "cart")
	private List<User> users;

	private Integer point;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescriptionShort() {
		return descriptionShort;
	}

	public void setDescriptionShort(String descriptionShort) {
		this.descriptionShort = descriptionShort;
	}

	public String getDescriptionLong() {
		return descriptionLong;
	}

	public void setDescriptionLong(String descriptionLong) {
		this.descriptionLong = descriptionLong;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}
