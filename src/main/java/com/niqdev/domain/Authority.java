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
@ToString(exclude = { "roles" })
@JsonIgnoreProperties(value = { "roles" })
@Entity
@Table(name = "sys_authority")
public class Authority extends BaseEntity {

	@EqualsAndHashCode.Include
	@Column(nullable = false, unique = true)
	private String authorityId;
	
	@Column(nullable = false, unique = true)
	private String authorityName;
	
	private String displayName;
	
	private String description;
	
	@Builder.Default
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinTable(name = "sys_authority_role", 
			joinColumns = { @JoinColumn(name = "authority_id", referencedColumnName = "id") }, 
			inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") })
	private Set<Role> roles = new HashSet<>();
	
	public void addRole(Role role) {
		roles.add(role);
		role.getAuthorities().add(this);
	}
	
	public void removeRole(Role role) {
		roles.remove(role);
		role.getAuthorities().remove(this);
	}
	
	public void clearRoles() {
		for (Role role : roles) {
			role.getAuthorities().remove(this);
		}
		roles = new HashSet<>();
	}
	
}
