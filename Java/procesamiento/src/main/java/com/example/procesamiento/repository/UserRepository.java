package com.example.procesamiento.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.procesamiento.model.User;

public interface UserRepository extends JpaRepository<User, Integer>, PagingAndSortingRepository<User, Integer> {

	Optional<User> findByEmail(String email); // Método correcto para buscar por email

	List<User> findByRol(String rol);

	User findByEmailAndPasswordAndRol(String email, String password, String role);

}
