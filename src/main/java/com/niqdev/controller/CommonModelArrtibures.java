package com.niqdev.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.niqdev.domain.Announcement;
import com.niqdev.domain.AnnouncementCategory;
import com.niqdev.domain.User;
import com.niqdev.service.AnnouncementService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class CommonModelArrtibures {
	
	private final AnnouncementService announcementService;

	@ModelAttribute("requestURI")
	public String getCurrentUrl(HttpServletRequest request) {
	    return request.getRequestURI();
	}
	
	@ModelAttribute("authUser")
	public User authUser(@AuthenticationPrincipal User user) {
		return user;
	}
	
	@ModelAttribute("announcements")
	public List<Announcement> announcements() {
		return announcementService.getAllAnnouncements();
	}
	
	@ModelAttribute("categories")
	public AnnouncementCategory[] categories() {
		return AnnouncementCategory.values();
	}
	
}
