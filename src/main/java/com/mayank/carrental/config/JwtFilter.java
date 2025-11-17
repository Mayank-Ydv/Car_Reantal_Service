package com.mayank.carrental.config;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mayank.carrental.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter {
	private final JwtUtil jwtUtil;

	public JwtFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String path = request.getRequestURI();

		System.out.println("Request Path: " + path);

		// Skip JWT validation only for /api/auth/roles
		if (path.equals("/api/auth/roles")) {
			filterChain.doFilter(request, response);
			return;
		}else if(path.equals("/v3/api-docs")) {
			filterChain.doFilter(request, response);
			return;
		}

		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		System.out.println("Authorization Header: " + header);
		if (header != null && header.startsWith("Bearer ")) {
			String token = header.substring(7);

			System.out.println("Request Path: " + path);

			try {
				Jws<Claims> claims = jwtUtil.validateToken(token);

				String userId = claims.getBody().getSubject();

				String role = (String) claims.getBody().get("role");

				System.out.println("User ID: " + userId);
				System.out.println("Role: " + role);

				List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userId, null,
						authorities);

				SecurityContextHolder.getContext().setAuthentication(auth);

			} catch (Exception ex) {
				ex.printStackTrace(); // Log the error
			}
		} else {
			System.out.println("Authorization header missing or invalid.");
		}
		filterChain.doFilter(request, response);
	}
}
