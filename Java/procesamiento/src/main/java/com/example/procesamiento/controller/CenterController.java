package com.example.procesamiento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.procesamiento.model.Center;
import com.example.procesamiento.service.CenterService;

@RestController
//@RequestMapping("/api/v1")
public class CenterController {
	@Autowired
	private final CenterService centerService;

	@Autowired
	public CenterController(CenterService centerService) {
		this.centerService = centerService;
	}

//    @GetMapping("/centers")
//    public List<Center> getAllCenters(@RequestParam(required = false) String name,
//                                      @RequestParam(required = false) String type,
//                                      @RequestParam(required = false) Boolean sortAsc) {
//        if (name != null) {
//            return centerService.searchCentersByName(name);
//        } else if (type != null) {
//            return centerService.searchCentersByType(type);
//        } else {
//            return centerService.getAllCentersSorted(sortAsc != null ? sortAsc : true);
//        }
//    }
	@GetMapping("/centers")
	public ResponseEntity<List<Center>> getAllCenters() {
		List<Center> centers = centerService.getAllCenters();
		return ResponseEntity.ok(centers);
	}

	@GetMapping("/centers/{id}")
	public ResponseEntity<Center> getCenter(@PathVariable Integer id) {
		Center center = centerService.getCenter(id);
		if (center == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(center);
	}

	@PostMapping("/centers")
	public ResponseEntity<Center> addCenter(@RequestBody Center center) {
		Center newCenter = centerService.saveCenter(center);
		if (newCenter == null) {
			return ResponseEntity.badRequest().build();
		}
		return new ResponseEntity<>(newCenter, HttpStatus.CREATED);
	}

	@DeleteMapping("/centers/{id}")
	public ResponseEntity<Void> deleteCenter(@PathVariable Integer id) {
		if (centerService.existsById(id)) {
			centerService.deleteCenter(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/centers/{id}")
	public ResponseEntity<Center> updateCenter(@PathVariable Integer id, @RequestBody Center center) {
		Center updatedCenter = centerService.updateCenter(id, center);
		return new ResponseEntity<>(updatedCenter, HttpStatus.OK);
	}

}
