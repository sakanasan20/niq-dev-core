package com.niqdev.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.niqdev.domain.User;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class CommonModelArrtibures {

	@ModelAttribute("requestURI")
	public String getCurrentUrl(HttpServletRequest request) {
	    return request.getRequestURI();
	}
	
	@ModelAttribute("authUser")
	public User authUser(@AuthenticationPrincipal User user) {
		return user;
	}
	
}
