package com.example.taskmanager.service;

import com.example.taskmanager.model.Role;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String username, String rawPassword, Set<Role> roles) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already taken");
        }
        User u = new User(username, passwordEncoder.encode(rawPassword));
        u.setRoles(roles);
        return userRepository.save(u);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(Long id) { return userRepository.findById(id); }
}
