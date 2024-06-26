package com.example.procesamiento.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.procesamiento.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, PagingAndSortingRepository<User, Integer> {

	Optional<User> findByEmail(String email);

	List<User> findByRol(String rol);

	User findByEmailAndPasswordAndRol(String email, String password, String role);

}
