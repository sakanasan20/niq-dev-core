package com.niqdev.bootstrap;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.niqdev.domain.Authority;
import com.niqdev.domain.Role;
import com.niqdev.domain.User;
import com.niqdev.repository.AuthorityRepository;
import com.niqdev.repository.RoleRepository;
import com.niqdev.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

	private final AuthorityRepository authorityRepository;
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	@Override
	public void run(String... args) throws Exception {
		loadAuthorities();
		loadRoles();
		loadUsers();
	}

	@Transactional
	private void loadAuthorities() {
		authorityRepository.saveAll(Arrays.asList(
				Authority.builder().authorityId(UUID.randomUUID().toString()).authorityName("create").displayName("新增").build(), 
				Authority.builder().authorityId(UUID.randomUUID().toString()).authorityName("read").displayName("讀取").build(), 
				Authority.builder().authorityId(UUID.randomUUID().toString()).authorityName("update").displayName("修改").build(), 
				Authority.builder().authorityId(UUID.randomUUID().toString()).authorityName("delete").displayName("刪除").build()
		));
	}

	@Transactional
	private void loadRoles() {
		Set<Authority> authorities = authorityRepository.findAll().stream().collect(Collectors.toSet());
		
		Role roleAdmin = Role.builder().roleId(UUID.randomUUID().toString()).roleName("ADMIN").displayName("管理者").authorities(authorities).build();
		Role roleUser = Role.builder().roleId(UUID.randomUUID().toString()).roleName("USER").displayName("一般使用者").build();
		
		authorities.stream().forEach(authority -> roleAdmin.addAuthority(authority));
		
		roleRepository.saveAll(Arrays.asList(roleAdmin, roleUser));
	}

	@Transactional
	private void loadUsers() {
		Role roleAdmin = roleRepository.findByRoleName("ADMIN").orElseThrow(RuntimeException::new);
		Role roleUser = roleRepository.findByRoleName("USER").orElseThrow(RuntimeException::new);
		User admin = User.builder()
				.userId(UUID.randomUUID().toString())
				.username("admin")
				.password(bCryptPasswordEncoder.encode("admin"))
				.displayName("管理員")
				.email("admin@nicore.com")
				.description("管理帳號")
				.build();
		admin.addRole(roleAdmin);
		admin.addRole(roleUser);
		userRepository.saveAll(Arrays.asList(admin));
		
		for (int i = 1; i <= 10; i++) {
			User user = User.builder()
				.userId(UUID.randomUUID().toString())
				.username("user"+ i)
				.password(bCryptPasswordEncoder.encode("user" + i))
				.email("user"+ i + "@nicore.com")
				.displayName("測試員" + i)
				.description("測試帳號")
				.build();
			user.addRole(roleUser);
			userRepository.save(user);
		}
	}

}
