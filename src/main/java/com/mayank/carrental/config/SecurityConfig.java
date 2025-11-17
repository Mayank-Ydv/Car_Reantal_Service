package com.mayank.carrental.config;
//
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mayank.carrental.util.JwtUtil;

@Configuration
public class SecurityConfig {

	private final JwtUtil jwtUtil;

	public SecurityConfig(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		JwtFilter jwtFilter = new JwtFilter(jwtUtil);

		http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
						 //Role-based access
						.requestMatchers(HttpMethod.GET, "/api/admin/**").hasAuthority("ROLE_ADMIN")
						.requestMatchers(HttpMethod.GET, "/api/user/**").hasAuthority("ROLE_USER")
						
						.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
						 .requestMatchers("/auth/**").permitAll() // your login/register endpoints
						 
						 
						.requestMatchers(HttpMethod.GET, "/api/user/bookings/**").hasAuthority("ROLE_USER")
						.requestMatchers(HttpMethod.DELETE, "/api/user/bookings/**").hasAuthority("ROLE_USER")
						.requestMatchers(HttpMethod.POST, "/api/admin/**").hasAuthority("ROLE_ADMIN")
						.requestMatchers("/h2-console/**").permitAll().anyRequest().authenticated())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.headers(headers -> headers.frameOptions(frame -> frame.disable())); // For H2 console

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//            .csrf().disable() // Disable CSRF for simplicity
//            .authorizeHttpRequests()
//            .anyRequest().permitAll(); // Allow all endpoints without authentication
//
//        return http.build();
//    }
//}

