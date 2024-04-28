package com.example.procesamiento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.procesamiento.model.Center;

public interface CenterRepository extends JpaRepository<Center, Integer>, PagingAndSortingRepository<Center, Integer> {
	
	List<Center> findByNombreCentroContainingIgnoreCase(String nombreCentro);

	List<Center> findByNombreCentroStartingWithIgnoreCase(String tipo);
}
