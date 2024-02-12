package com.coderscampus.a13.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.coderscampus.a13.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

	@Query("select u from User u left join fetch u.accounts left join fetch u.address")
	List<User> findAll();
}
