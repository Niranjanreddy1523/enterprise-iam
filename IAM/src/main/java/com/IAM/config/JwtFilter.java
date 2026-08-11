package com.IAM.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	/* @Autowired private JwtUtil jwtUtil;
	@Autowired private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain) throws ServletException, IOException {
	    final String authHeader = request.getHeader("Authorization");
	    String username = null;
	    String jwt = null;
	
	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        jwt = authHeader.substring(7);
	        username = jwtUtil.extractUsername(jwt);
	    }
	
	    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	
	        if (jwtUtil.validateToken(jwt, userDetails)) {
	            UsernamePasswordAuthenticationToken authToken =
	                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	
	            SecurityContextHolder.getContext().setAuthentication(authToken);
	        }
	    }
	    filterChain.doFilter(request, response);
	}
	@Bean
	  public UserDetailsService userDetailsService(UserRepository userRepository) {
	      return username -> userRepository.findByUsername(username)
	          .map(user -> User.withUsername(user.getUsername())
	                           .password(user.getPassword()) // must be BCrypt encoded
	                           .roles(user.getRoles().stream()
	                                      .map(Role::getName)
	                                      .toArray(String[]::new))
	                           .build())
	          .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	      
	  }
	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepository) {
	    return username -> userRepository.findByUsername(username)
	        .map(user -> org.springframework.security.core.userdetails.User
	                .withUsername(user.getUsername())
	                .password(user.getPassword()) // must already be BCrypt encoded
	                .roles(user.getRoles().stream()
	                           .map(Role::getName)
	                           .toArray(String[]::new))
	                .build()) // <-- important
	        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}*/
	
	private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            String username = jwtUtil.extractUsername(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }


}
