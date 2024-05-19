package com.example.procesamiento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.procesamiento.model.Center;
import com.example.procesamiento.model.User;
import com.example.procesamiento.repository.CenterRepository;
import com.example.procesamiento.repository.UserRepository;

@Service
public class UserCenterService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CenterRepository centerRepository;

	public User addUserCenter(int userId, int centerId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		Center center = centerRepository.findById(centerId).orElseThrow(() -> new RuntimeException("Center not found"));
		user.getCenters().add(center);
		return userRepository.save(user);
	}

	public User removeUserCenter(int userId, int centerId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		Center center = centerRepository.findById(centerId).orElseThrow(() -> new RuntimeException("Center not found"));
		user.getCenters().remove(center);
		return userRepository.save(user);
	}

}
