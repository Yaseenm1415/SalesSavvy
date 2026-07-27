package com.app.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entities.User;
import com.app.enums.UserRole;
import com.app.services.DashboardServiceContract;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin/dashboard")
public class DashboardController {
	DashboardServiceContract dashboardService;

	public DashboardController(DashboardServiceContract dashboardService) {
		super();
		this.dashboardService = dashboardService;
	}

	@GetMapping
	public ResponseEntity<?> getDashboardStats(HttpServletRequest httpRequest) {

		User user = (User) httpRequest.getAttribute("authenticatedUser");
		if (user == null || user.getRole() != UserRole.ADMIN) {
			throw new RuntimeException("Unauthenticated");
		}

		return ResponseEntity.ok(dashboardService.getDashboardStats());

	}
}
