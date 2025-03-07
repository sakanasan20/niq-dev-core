package com.niqdev.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(exclude = {})
@JsonIgnoreProperties(value = {})
@Entity
@Table(name = "announcements")
public class Announcement extends BaseEntity {
	
	@EqualsAndHashCode.Include
	@Column(nullable = false, unique = true)
	private String announcementId;
	
    private String title;
    
    private String content;
    
    @Enumerated(EnumType.STRING)
    private AnnouncementCategory category;
    
}
