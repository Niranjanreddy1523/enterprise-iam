package com.IAM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.IAM.config.AuthRequest;
import com.IAM.config.AuthResponse;
import com.IAM.config.JwtUtil;
import com.IAM.entity.User;
import com.IAM.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(authService.register(user));
    }

    /*@PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String username,
                                      @RequestParam String password) {
        Optional<User> user = authService.login(username, password);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.status(401).build());
        
    }*/
	  
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
	    authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
	    );
	
	    final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
	    final String jwt = jwtUtil.generateToken(userDetails);
	
	    return ResponseEntity.ok(new AuthResponse(jwt));
	}
	/* 
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest request) {
	    User user = userService.findByUsername(request.getUsername());
	    if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	        String otp = otpService.generateOtp(user);
	        otpSender.sendOtp(user.getEmail(), otp);
	        return ResponseEntity.ok("OTP sent to registered email.");
	    } else {
	        return ResponseEntity.status(401).body("Bad credentials");
	    }
	}*/


    @PostMapping("/logout/{userId}")
    public ResponseEntity<Void> logout(@PathVariable Long userId) {
        authService.logout(userId);
        return ResponseEntity.noContent().build();
    }
}

