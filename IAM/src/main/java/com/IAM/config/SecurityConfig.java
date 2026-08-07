package com.IAM.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.IAM.entity.Role;
import com.IAM.repository.UserRepository;

@Configuration
public class SecurityConfig {

	  @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/api/users/add","/api/roles/add","/api/auth/**").permitAll()
	            .requestMatchers("/api/users/{id}").authenticated()
	            .anyRequest().authenticated())
	        .formLogin(Customizer.withDefaults())
	        .csrf(csrf -> csrf.disable());                     
	    return http.build();
	}
	  @Bean
	  public UserDetailsService userDetailsService(UserRepository userRepository) {
	      return username -> userRepository.findByUsername(username)
	          .map(user -> User.withUsername(user.getUsername())
	                           .password(passwordEncoder().encode(user.getPassword())) // must be BCrypt encoded
	                           .roles(user.getRoles().stream()
	                                      .map(Role::getName)
	                                      .toArray(String[]::new))
	                           .build())
	          .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	  }

	  
	
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
