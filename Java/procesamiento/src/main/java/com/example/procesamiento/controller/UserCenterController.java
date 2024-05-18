package com.example.procesamiento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.procesamiento.model.User;
import com.example.procesamiento.service.UserCenterService;

@RestController
public class UserCenterController {
	@Autowired
	private UserCenterService userCenterService;

	@PostMapping("/{userId}/centers/{centerId}")
	public ResponseEntity<User> addUserCenter(@PathVariable int userId, @PathVariable int centerId) {
		User user = userCenterService.addUserCenter(userId, centerId);
		return ResponseEntity.ok(user);
	}

	@DeleteMapping("/{userId}/centers/{centerId}")
	public ResponseEntity<User> removeUserCenter(@PathVariable int userId, @PathVariable int centerId) {
		User user = userCenterService.removeUserCenter(userId, centerId);
		return ResponseEntity.ok(user);
	}
}