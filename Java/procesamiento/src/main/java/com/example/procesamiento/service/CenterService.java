package com.example.procesamiento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.procesamiento.model.Center;
import com.example.procesamiento.repository.CenterRepository;

import java.util.List;

@Service
public class CenterService {
	private final CenterRepository centerRepository;

	@Autowired
	public CenterService(CenterRepository centerRepository) {
		this.centerRepository = centerRepository;
	}

	public List<Center> searchCentersByName(String nombre) {
		return centerRepository.findByNameContainingIgnoreCase(nombre);
	}

	public List<Center> searchCentersByType(String type) {

		return centerRepository.findByNameStartingWithIgnoreCase(type);
	}

	public List<Center> getAllCentersSorted(boolean ascending) {
		Sort sort = Sort.by(Sort.Direction.ASC, "nombreCentro");
		if (!ascending) {
			sort = sort.descending();
		}
		return centerRepository.findAll(sort);
	}

	public Center saveCenter(Center center) {
		if (center.getName() == null) {
			return null;
		}
		return centerRepository.save(center);
	}

	public void deleteCenter(Integer id) {
		centerRepository.deleteById(id);
	}

	public Center updateCenter(Integer id, Center center) {
		Center centerToUpdate = centerRepository.findById(id).orElse(null);
		if (centerToUpdate != null) {
			centerToUpdate.setName(center.getName());
			centerToUpdate.setWeb(center.getWeb());
			centerToUpdate.setType(center.getType());
			centerToUpdate.setAddress(center.getAddress());
			centerToUpdate.setPhone(center.getPhone());
			centerToUpdate.setDescr(center.getDescr());
			return centerRepository.save(centerToUpdate);
		}
		return null;
	}

	public List<Center> getAllCenters() {
		return centerRepository.findAll();
	}

	public Center getCenter(Integer id) {
		return centerRepository.findById(id).orElse(null);
	}

	public boolean existsById(Integer id) {
		return centerRepository.existsById(id);
	}

}
