package io.github.hksm.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class UserData {

	@Id @GeneratedValue
	private Long id;
	
	@NotNull @Column(unique=true)
	private String username;
	
	@NotNull
	private String password;
	
	@NotNull @Column(unique=true)
	private String email;
	
	@NotNull @ElementCollection(fetch=FetchType.EAGER)
	private Set<String> roles;
	
	public UserData() {
	}
	
	public UserData(Long id, String username, String password, String email, Set<String> roles) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
}