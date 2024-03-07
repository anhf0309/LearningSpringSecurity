package com.example.Barebones.service;


import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.Barebones.model.User;
import com.example.Barebones.repo.UserRegistrationDto;

public interface UserService extends UserDetailsService {
   User findByEmail(String email);
   User save(UserRegistrationDto registration);
}
