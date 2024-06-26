package com.example.procesamiento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.procesamiento.model.Center;

@Repository
public interface CenterRepository extends JpaRepository<Center, Integer>, PagingAndSortingRepository<Center, Integer> {

	List<Center> findByNameContainingIgnoreCase(String name); 

	List<Center> findByNameStartingWithIgnoreCase(String type);
}
