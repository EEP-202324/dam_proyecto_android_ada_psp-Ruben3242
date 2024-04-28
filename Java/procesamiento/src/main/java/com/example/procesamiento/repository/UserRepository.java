package com.example.procesamiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.procesamiento.model.User;

public interface UserRepository extends JpaRepository<User, Integer>, PagingAndSortingRepository<User, Integer> {


    User findByEmailAndPassword(String email, String password);

	User findByEmail(String email);
    
	
}
