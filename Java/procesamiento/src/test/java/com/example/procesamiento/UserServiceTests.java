package com.example.procesamiento;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.example.procesamiento.model.User;
import com.example.procesamiento.repository.UserRepository;
import com.example.procesamiento.service.UserService;


@SpringBootTest
public class UserServiceTests {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testGetUserById() {
        User user = new User(/*...*/);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Assert.assertEquals(user, userService.getUser(1));
    }

    @Test
}
