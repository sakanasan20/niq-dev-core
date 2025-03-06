package com.niqdev.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niqdev.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByRoleId(String roleId);
	
	Optional<Role> findByRoleName(String roleName);

	void deleteByRoleIdIn(List<String> roleIds);

	List<Role> findByRoleIdIn(List<String> roleIds);
	
}
