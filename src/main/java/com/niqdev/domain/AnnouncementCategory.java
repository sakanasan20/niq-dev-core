package com.niqdev.domain;

public enum AnnouncementCategory {
	
    NEWS("📰 最新消息"),
    EVENT("📅 活動公告"),
    SYSTEM("⚙ 系統更新");

    private final String displayName;

    AnnouncementCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
    
}
