package com.example.procesamiento;

import org.springframework.data.annotation.Id;

public record User(@Id Long id, String name, String email, String password, String role) {

}
