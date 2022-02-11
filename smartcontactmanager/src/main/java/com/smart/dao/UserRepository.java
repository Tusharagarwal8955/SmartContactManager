package com.smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.User;

public interface UserRepository  extends JpaRepository<User, Integer >{

	@Query(value = "select * from user where email=?1",nativeQuery = true)
	public User getUserByUsername(String email);
	
}
