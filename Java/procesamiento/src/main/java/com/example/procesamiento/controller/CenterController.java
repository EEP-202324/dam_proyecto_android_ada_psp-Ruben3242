package com.example.procesamiento.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.procesamiento.model.Center;
import com.example.procesamiento.service.CenterService;

import java.util.List;

@RestController
//@RequestMapping("/api/v1")
public class CenterController {
    private final CenterService centerService;

    @Autowired
    public CenterController(CenterService centerService) {
        this.centerService = centerService;
    }

    @GetMapping("/centers")
    public List<Center> getAllCenters(@RequestParam(required = false) String name,
                                      @RequestParam(required = false) String type,
                                      @RequestParam(required = false) Boolean sortAsc) {
        if (name != null) {
            return centerService.searchCentersByName(name);
        } else if (type != null) {
            return centerService.searchCentersByType(type);
        } else {
            return centerService.getAllCentersSorted(sortAsc != null ? sortAsc : true);
        }
    }
    
    @PostMapping("/centers")
    public ResponseEntity<Center> addCenter(@RequestBody Center center) {
        Center newCenter = centerService.saveCenter(center);
        return new ResponseEntity<>(newCenter, HttpStatus.CREATED);
    }
    
    @DeleteMapping("/centers/{id}")
	public ResponseEntity<?> deleteCenter(@PathVariable Integer id) {
		centerService.deleteCenter(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
    
	@PutMapping("/centers/{id}")
	public ResponseEntity<Center> updateCenter(@PathVariable Integer id, @RequestBody Center center) {
		Center updatedCenter = centerService.updateCenter(id, center);
		return new ResponseEntity<>(updatedCenter, HttpStatus.OK);
	}

}
