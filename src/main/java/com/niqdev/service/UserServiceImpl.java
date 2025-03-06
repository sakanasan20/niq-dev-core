package com.niqdev.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.niqdev.domain.User;
import com.niqdev.repository.RoleRepository;
import com.niqdev.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
	}

	@Transactional
	@Override
	public List<User> findAll() {
		List<User> users = userRepository.findAll();
		return users;
	}

	@Transactional
	@Override
	public User findByUserId(String userId) {
		return userRepository.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException(userId));
	}

	@Transactional
	@Override
	public User create(User user) {
		final String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setUserId(UUID.randomUUID().toString());
		user.setPassword(encryptedPassword);
		user.addRole(roleRepository.findByRoleName("USER").orElseThrow(RuntimeException::new));
		return userRepository.save(user);
	}

	@Transactional
	@Override
	public User update(User user) {
		User userToUpdate = findByUserId(user.getUserId());
		userToUpdate.setUsername(user.getUsername());
		userToUpdate.setDisplayName(user.getDisplayName());
		userToUpdate.setEmail(user.getEmail());
		userToUpdate.setDescription(user.getDescription());
		return userRepository.save(userToUpdate);
	}

	@Transactional
	@Override
	public void deleteAll(List<String> userIds) {
		List<User> users = userRepository.findByUserIdIn(userIds);
		for(User user : users) {
			user.clearRoles();
			userRepository.save(user);
		}
		userRepository.deleteByUserIdIn(userIds);
	}

}
