package com.niqdev.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niqdev.domain.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

	Optional<Authority> findByAuthorityId(String authorityId);
	
	Optional<Authority> findByAuthorityName(String authorityName);
	
	List<Authority> findByAuthorityNameContainsIgnoreCase(String authorityName);
	
	Optional<Authority> findByDisplayName(String displayName);
	
	List<Authority> findByDisplayNameContains(String displayName);

	void deleteByAuthorityIdIn(List<String> authorityIds);

	List<Authority> findByAuthorityIdIn(List<String> authorityIds);

	Authority findOneByAuthorityNameContainsIgnoreCase(String authorityName);
	
}
