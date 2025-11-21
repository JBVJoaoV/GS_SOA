package com.example.taskmanager.security;

import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<GrantedAuthority> authorities = u.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.name()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), authorities);
    }
}
