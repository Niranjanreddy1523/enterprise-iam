package com.IAM.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.IAM.entity.User;
import com.IAM.repository.RefreshTokenRepository;
import com.IAM.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       RefreshTokenRepository refreshTokenRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

	 public Optional<User> login(String username, String rawPassword) {
	    return userRepository.findByUsername(username)
	            .filter(user -> passwordEncoder.matches(rawPassword, user.getPassword()));
	}
	
	public void logout(Long userId) {
	    refreshTokenRepository.deleteByUserId(userId);
	}
}
