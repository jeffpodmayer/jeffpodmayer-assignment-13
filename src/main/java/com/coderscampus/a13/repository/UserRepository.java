package com.coderscampus.a13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderscampus.a13.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

	
}
