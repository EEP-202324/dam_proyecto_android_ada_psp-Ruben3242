package com.example.procesamiento.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	    Optional<User> optUser = userRepository.findByEmail(email);
	    if (optUser.isPresent()) {
	        User user = optUser.get();
	        // Verifica el rol y la contraseña aquí
	        if (role.equals(user.getRol()) && passwordEncoder.matches(rawPassword, user.getPassword())) {
	            return user; // Usuario autenticado
	        }
	    } else if ("user".equals(role)) {
	        // Crea un nuevo usuario si el rol es 'user' y el email no existe
	        User newUser = new User();
	        newUser.setEmail(email);
	        newUser.setPassword(passwordEncoder.encode(rawPassword));
	        newUser.setRol(role);
	        return userRepository.save(newUser);
	    }
	    return null; // Usuario no encontrado o contraseña/rol incorrectos
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

//	public boolean authenticateUser(String email, String rawPassword) {
//        User user = userRepository.findByEmail(email);
//        if (user != null && "owner".equals(user.getRol())) {
//            return passwordEncoder.matches(rawPassword, user.getPassword());
//        }
//        return false;
//    }

	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}

//	public User updateUser(User user) {
//		return userRepository.save(user);
//	}
	public User updateUser(User user) {
		return userRepository.findById(user.getId()).map(existingUser -> {
			existingUser.setNombre(user.getNombre());
			existingUser.setEmail(user.getEmail());
			return userRepository.save(existingUser);
		}).orElse(null);
	}

	public List<User> getUsers() {
		return userRepository.findAll();
	}

//	public User getUserByEmail(String email, String password) {
//		return userRepository.findByEmailAndPassword(email, password);
//	}

	// faindByRol
	public List<User> getUsersByRol(String rol) {
		return userRepository.findByRol(rol);
	}

}