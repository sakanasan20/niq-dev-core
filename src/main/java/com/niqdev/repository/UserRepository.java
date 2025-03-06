package com.niqdev.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niqdev.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findByUserIdIn(List<String> userIds);
	
	Optional<User> findByUserId(String userId);
	
	Optional<User> findByUsername(String username);

	void deleteByUserIdIn(List<String> userIds);
	
}
