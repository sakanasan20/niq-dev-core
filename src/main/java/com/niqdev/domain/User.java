package com.niqdev.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
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
@Table(name = "sys_user")
public class User extends BaseEntity implements UserDetails {

	private static final long serialVersionUID = -7473371552077866862L;

	@EqualsAndHashCode.Include
	@Column(nullable = false, unique = true)
	private String userId;
	
	@Column(nullable = false, unique = true, length = 20)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	private String displayName;
	
	private String email;
	
	private String description;
	
	@Builder.Default
	@ManyToMany(mappedBy = "users", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Role> roles = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		Set<GrantedAuthority> roles = this.roles.stream()
				.map(Role::getRoleName)
				.map(roleName -> "ROLE_" + roleName)
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());
		Set<GrantedAuthority> authorities = this.roles.stream()
				.map(Role::getAuthorities)
				.flatMap(Collection::stream)
				.map(Authority::getAuthorityName)
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());
		grantedAuthorities.addAll(roles);
		grantedAuthorities.addAll(authorities);
		return grantedAuthorities;
	}

	public void addRole(Role role) {
		roles.add(role);
		role.getUsers().add(this);
	}
	
	public void removeRole(Role role) {
		roles.remove(role);
		role.getUsers().remove(this);
	}
	
	public void clearRoles() {
		for (Role role : roles) {
			role.getUsers().remove(this);
		}
		roles = new HashSet<>();
	}
	
}
