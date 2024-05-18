package com.example.procesamiento.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.procesamiento.repository.UserRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		// Verifica si hay datos en la base de datos
		if (userRepository.findByRol("Owner").size() == 0) {
			User defaultOwner = new User();
			defaultOwner.setEmail("owner123@gmail.com");
			defaultOwner.setPassword(passwordEncoder.encode("1234"));
			defaultOwner.setRol("Owner");
			userRepository.save(defaultOwner);
		}
	}
}