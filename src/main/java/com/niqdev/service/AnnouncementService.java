package com.niqdev.service;

import java.util.List;

import com.niqdev.domain.Announcement;

public interface AnnouncementService {

    List<Announcement> getAllAnnouncements();

    List<Announcement> getAnnouncementsByCategory(String category);

    void saveAnnouncement(Announcement announcement);
	
}
