package com.niqdev.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.niqdev.domain.User;

public interface UserService extends UserDetailsService {

	List<User> findAll();

	User findByUserId(String userId);
	
	User create(User user);
	
	User update(User user);
	
	void deleteAll(List<String> userIds);

}
