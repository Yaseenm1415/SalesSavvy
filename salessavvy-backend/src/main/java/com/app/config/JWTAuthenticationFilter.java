package com.app.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.entities.User;
import com.app.repositories.UserRepository;
import com.app.services.AuthServiceContract;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter{
	private final AuthServiceContract authService;
	private final UserRepository userRepository;
	
	public JWTAuthenticationFilter(AuthServiceContract authService, UserRepository userRepository) {
		super();
		this.authService = authService;
		this.userRepository = userRepository;
	}
	
	private static final List<String> PUBLIC_PATHS = List.of(
				"/api/user/register",
				"/api/user/login",
				"/admin/login",
				"/refresh",
				"/api/products",
				"/api/products/{id}",
				"/api/categories",
				"/api/categories/{id}",
				"/logout"
			);
	
			

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String uri = request.getRequestURI();
		
		return PUBLIC_PATHS.contains(uri) || uri.startsWith("/uploads/");
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String accessToken = getAccessTokenFromCookie(request);
		
		
		if(accessToken == null) {
			filterChain.doFilter(request, response);
			return;
		}
		
		if (!authService.validateAccessToken(accessToken)) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access token expired");			return;
		}
		
		String username = authService.extractUsername(accessToken);
		
		User user = userRepository.findByUsername(username).orElse(null); 
		
		if(user == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
			return;
		}
		
		String role = "ROLE_" + user.getRole().name();
		
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, List.of(authority));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		request.setAttribute("authenticatedUser", user);
		
		filterChain.doFilter(request, response);
		
		
	}
	
	private String getAccessTokenFromCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		
		if(cookies == null) {
			return null;
		}
		
		return Arrays.stream(cookies)
				.filter(cookie -> "accessToken".equals(cookie.getName()))
				.map(Cookie::getValue)
				.findFirst()
				.orElse(null);
	}
	

}
