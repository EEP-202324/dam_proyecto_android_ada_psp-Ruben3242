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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.procesamiento.model.LoginRequest;
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

	@GetMapping("/user")
	public ResponseEntity<List<User>> getUsers() {
		List<User> users = userService.getUsers();
		return ResponseEntity.ok(users);
	}

//	@GetMapping
//	public ResponseEntity<User> getUserByEmail(@PathVariable String email, @PathVariable String password) {
//		User user = userService.getUserByEmail(email, password);
//		return ResponseEntity.ok(user);
//	}

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

//	@PostMapping("/user")
//	public ResponseEntity<User> updateUser(@RequestBody User user) {
//		User updatedUser = userService.updateUser(user);
//		return ResponseEntity.ok(updatedUser);
//	} 
	// updateUser
	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
		User updatedUser = userService.updateUser(id, user);
		return ResponseEntity.ok(updatedUser);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
	    User user = userService.loginUser(request.getEmail(), request.getPassword(), request.getRole());
	    if (user != null) {
	        // Usuario autenticado correctamente
	        return ResponseEntity.ok(user);
	    } else if ("user".equals(request.getRole())) {
	        // Crear nuevo usuario
	        User newUser = userService.createUser(request.getEmail(), request.getPassword());
	        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
	    } else {
	        // Usuario existente o credenciales incorrectas
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas o acceso denegado.");
	    }
	}


}