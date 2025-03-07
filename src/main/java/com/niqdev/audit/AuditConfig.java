package com.niqdev.audit;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider", dateTimeProviderRef = "dateTimeProvider")
public class AuditConfig {

	@Bean
	AuditorAware<String> auditorProvider() {
		return new AuditorAwareImpl();
	}

	@Bean
	DateTimeProvider dateTimeProvider() {
		return () -> Optional.of(LocalDateTime.now());
	}

}
