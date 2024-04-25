package com.example.procesamiento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.procesamiento.model.User;
import com.example.procesamiento.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUser(@PathVariable Integer id) {
		User user = userService.getUser(id);
		return ResponseEntity.ok(user);
	}

	@PostMapping("/user")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		User savedUser = userService.saveUser(user);
		return ResponseEntity.ok(savedUser);
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable Integer id) {
		userService.deleteUser(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/user")
	public ResponseEntity<List<User>> getUsers() {
		List<User> users = userService.getUsers();
		return ResponseEntity.ok(users);
	}
	
	@GetMapping
	public ResponseEntity<User> getUserByEmail(@PathVariable String email, @PathVariable String password) {
		User user = userService.getUserByEmail(email, password);
		return ResponseEntity.ok(user);
	}
	
	

}