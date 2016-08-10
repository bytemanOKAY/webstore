package com.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webstore.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByName(String string);

}
