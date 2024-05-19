package com.example.procesamiento.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.procesamiento.model.User;
import com.example.procesamiento.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User getUser(Integer id) {
		return userRepository.findById(id).orElse(null);
	}

	public User loginUser(String email, String rawPassword, String role) {
		User user = new User();
		user.setRol(role);
		Optional<User> userEmail = userRepository.findByEmail(email);
		if (user.getRol().equals("Owner") || user.getRol().equals("User")) {
			if (userEmail.isPresent() && user.getRol().equals("Owner")) {
				if (user.getPassword().equals(passwordEncoder.encode(rawPassword))) {
					return user;
				} else {
					return null;
				}
			} else if (!userEmail.isPresent() && user.getRol().equals("User")) {
				User newUser = new User();
				newUser.setEmail(email);
				newUser.setPassword(passwordEncoder.encode(rawPassword));
				newUser.setRol("User");
				return userRepository.save(newUser);
			} else if (userEmail.isPresent() && user.getRol().equals("User")) {
				if (user.getPassword().equals(passwordEncoder.encode(rawPassword))) {
					return user;
				} else {
					return null;
				}
			}
		}
		return null;
	}

	public User saveUser(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		return userRepository.save(user);
	}

	public User createUser(String email, String rawPassword) {
		// Comprobar si el usuario ya existe
		Optional<User> existingUser = userRepository.findByEmail(email);
		if (existingUser != null) {
			// Si el usuario ya existe, devuelve null o lanza una excepción
			return null;
		} else {
			// Si no existe, crea un nuevo usuario
			User newUser = new User();
			newUser.setEmail(email);
			newUser.setPassword(passwordEncoder.encode(rawPassword));
			newUser.setRol("user");
			// Guardar el nuevo usuario en la base de datos
			return userRepository.save(newUser);
		}
	}

	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}

	public User updateUser(Integer id, User user) {
		User existingUser = userRepository.findById(id).orElse(null);
		if (existingUser != null) {
			existingUser.setNombre(user.getNombre());
			existingUser.setCentroVisita(user.getCentroVisita());
			existingUser.setEdad(user.getEdad());
			existingUser.setEmail(user.getEmail());
			existingUser.setPassword(/* passwordEncoder.encode */(user.getPassword()));
			existingUser.setRol(user.getRol());
			return userRepository.save(existingUser);
		} else {
			return null;
		}
	}

	public Page<User> getUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	public List<User> getUsersByRol(String rol) {
		return userRepository.findByRol(rol);
	}

	public User loginUser2(String email, String password, String role) {
		User newUser = new User();
		newUser.setEmail(email);
		newUser.setPassword(passwordEncoder.encode(password));
		newUser.setRol(role);
		return userRepository.save(newUser);

	}

}