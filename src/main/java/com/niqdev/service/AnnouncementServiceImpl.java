package com.niqdev.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.niqdev.domain.Announcement;
import com.niqdev.repository.AnnouncementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository repository;

    @Override
    public List<Announcement> getAllAnnouncements() {
        return repository.findAll();
    }

    @Override
    public List<Announcement> getAnnouncementsByCategory(String category) {
        return category.equals("all") ? repository.findAll() : repository.findByCategory(category);
    }

    @Override
    public void saveAnnouncement(Announcement announcement) {
        repository.save(announcement);
    }
	
}
