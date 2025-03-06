package com.niqdev.domain;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@ToString(exclude = { "users" })
@JsonIgnoreProperties(value = { "users" })
@Entity
@Table(name = "sys_role")
public class Role extends BaseEntity {

	@EqualsAndHashCode.Include
	@Column(nullable = false, unique = true)
	private String roleId;
	
	@Column(nullable = false, unique = true)
	private String roleName;
	
	private String displayName;
	
	private String description;
	
	@Builder.Default
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinTable(name = "sys_role_user", 
			joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") }, 
			inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") })
	private Set<User> users = new HashSet<>();
	
	@Builder.Default
	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Authority> authorities = new HashSet<>();
	
	public void addUser(User user) {
		users.add(user);
		user.getRoles().add(this);
	}
	
	public void removeUser(User user) {
		users.remove(user);
		user.getRoles().remove(this);
	}
	
	public void clearUsers() {
		for (User user : users) {
			user.getRoles().remove(this);
		}
		users = new HashSet<>();
	}
	
	public void addAuthority(Authority authority) {
		authorities.add(authority);
		authority.getRoles().add(this);
	}
	
	public void removeAuthority(Authority authority) {
		authorities.remove(authority);
		authority.getRoles().remove(this);
	}
	
	public void clearAuthorities() {
		for (Authority authority : authorities) {
			authority.getRoles().remove(this);
		}
		authorities = new HashSet<>();
	}
	
}
