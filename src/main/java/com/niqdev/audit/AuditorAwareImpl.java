package com.niqdev.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.niqdev.domain.User;

public class AuditorAwareImpl implements AuditorAware<String> {
	
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.of("System");
        }
        return Optional.of(((User) authentication.getPrincipal()).getUsername()); // 取得登入帳號名稱
    }
	
}
