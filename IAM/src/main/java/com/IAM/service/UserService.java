package com.IAM.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.IAM.entity.User;
import com.IAM.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

//    public Optional<User> getUserById(Long id) {
//        return userRepository.findById(id);
//    }
    
    public Optional<User> getUserById(Long id) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Only allow if the logged-in user matches the requested ID or has ADMIN role
        if (!currentUser.getId().equals(id)) {
            throw new AccessDeniedException("Please enter correct user credentials");
        }

        return userRepository.findById(id);
    }

    

    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setEmail(updatedUser.getEmail());       
                    user.setUsername(updatedUser.getUsername()); 
                    return userRepository.save(user);
                }).orElseThrow(() -> new RuntimeException("User not found"));
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
  
}

