package com.niqdev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.niqdev.domain.Announcement;
import com.niqdev.service.AnnouncementService;

@Controller
@RequestMapping("/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService service;

    @GetMapping
    public String showAnnouncements(@RequestParam(defaultValue = "all") String category, Model model) {
        List<Announcement> announcements = service.getAnnouncementsByCategory(category);
        model.addAttribute("announcements", announcements);
        return "announcement";
    }

    @PostMapping("/add")
    public String addAnnouncement(@ModelAttribute Announcement announcement) {
        service.saveAnnouncement(announcement);
        return "redirect:/announcements";
    }
}
