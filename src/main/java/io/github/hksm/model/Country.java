package io.github.hksm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Immutable;

@Entity @Immutable
public class Country implements Serializable {

	private static final long serialVersionUID = 9159772055553426178L;

	@Id @GeneratedValue
	private Long id;
	
	@NotNull @Column(unique=true) @Size(max = 2)
	private String code;
	
	@NotNull @Column(unique=true)
	private String name;

	public Country() {
	}
	
	public Country(Long id, String code, String name) {
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
