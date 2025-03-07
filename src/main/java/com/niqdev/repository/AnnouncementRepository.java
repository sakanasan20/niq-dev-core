package com.niqdev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niqdev.domain.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

	List<Announcement> findByCategory(String category);
	
}
