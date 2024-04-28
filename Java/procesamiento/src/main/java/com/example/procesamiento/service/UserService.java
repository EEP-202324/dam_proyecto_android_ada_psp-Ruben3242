package com.example.procesamiento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.procesamiento.model.User;
import com.example.procesamiento.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User getUser(Integer id) {
		return userRepository.findById(id).orElse(null);
	}

//	public User saveUser(User user) {
//		return userRepository.save(user);
//	}
	public User saveUser(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		return userRepository.save(user);
	}
	
	public boolean authenticateUser(String email, String rawPassword) {
        User user = userRepository.findByEmail(email);
        if (user != null && "owner".equals(user.getRol())) {
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }

	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}

	public User updateUser(User user) {
		return userRepository.save(user);
	}

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public User getUserByEmail(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

}