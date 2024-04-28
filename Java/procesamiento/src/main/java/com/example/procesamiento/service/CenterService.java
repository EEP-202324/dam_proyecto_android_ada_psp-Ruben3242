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
        return centerRepository.findByNombreCentroContainingIgnoreCase(nombre);
    }
    
    public List<Center> searchCentersByType(String type) {
        return centerRepository.findByNombreCentroStartingWithIgnoreCase(type);
    }
    
    public List<Center> getAllCentersSorted(boolean ascending) {
        Sort sort = Sort.by(Sort.Direction.ASC, "nombreCentro");
        if (!ascending) {
            sort = sort.descending();
        }
        return centerRepository.findAll(sort);
    }

    public Center saveCenter(Center center) {
        return centerRepository.save(center);
    }
}
