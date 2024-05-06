package com.example.procesamiento.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.procesamiento.model.LoginRequest;
import com.example.procesamiento.model.User;
import com.example.procesamiento.repository.UserRepository;
import com.example.procesamiento.service.UserService;

@RestController
//@RequestMapping("/api/v1")
public class UserController {

	private final UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	private ResponseEntity<List<User>> findAll() {
		List<User> Users = userRepository.findAll();
		return ResponseEntity.ok(Users);
	}

	@GetMapping("/users")
	private ResponseEntity<List<User>> findAlll() {
		List<User> Users = userRepository.findAll();
		return ResponseEntity.ok(Users);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUser(@PathVariable Integer id) {
		User user = userService.getUser(id);
		return ResponseEntity.ok(user);
	}

//	@GetMapping("/users")
//    public ResponseEntity<Page<User>> getAllUsers(Pageable pageable) {
//        Page<User> users = userService.getUsers(pageable);
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }

	@PostMapping("/user")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User savedUser = userService.saveUser(user);
		return ResponseEntity.ok(savedUser);
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
		User updatedUser = userService.updateUser(id, user);
		return ResponseEntity.ok(updatedUser);
	}

//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//	    User user = userService.loginUser(request.getEmail(), request.getPassword(), request.getRole());
//	    if (user != null) {
//	        return ResponseEntity.ok(user);
//		} else {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas o acceso denegado.");
//	    }
//	}

	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> optUser = userRepository.findByEmail(request.getEmail());
        User user;

        // Verifica si el usuario con ese email ya existe
        if (optUser.isPresent()) {
            user = optUser.get();
            // Verifica si el rol y la contraseña son correctos
            if (user.getRol().equals(request.getRole()) && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas o acceso denegado.");
            }
        } else if ("User".equals(request.getRole()) && !request.getEmail().isEmpty()) {
            // Crear un nuevo usuario si el rol es 'User' y el email no existe
            User newUser = new User();
            newUser.setEmail(request.getEmail());
            newUser.setPassword(passwordEncoder.encode(request.getPassword()));
            newUser.setRol("User");
            userRepository.save(newUser);
            return ResponseEntity.ok(newUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas o acceso denegado.");
        }
    }

//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//		User user = userService.loginUser(request.getEmail(), request.getPassword(), request.getRole());
//		if (user != null) {
//			// Usuario autenticado correctamente
//			return ResponseEntity.ok(user);
//		} else if ("user".equals(request.getRole())) {
//			// Crear nuevo usuario
//			User newUser = userService.createUser(request.getEmail(), request.getPassword());
//			return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
//		} else {
//			// Usuario existente o credenciales incorrectas
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas o acceso denegado.");
//		}
//	}

}