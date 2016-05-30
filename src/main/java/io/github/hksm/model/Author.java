package io.github.hksm.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

@Entity
public class Author implements Serializable {

	private static final long serialVersionUID = -8585531012431779901L;

	@Id @GeneratedValue
	private Long id;
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	private DateTime birthDate;
	
	@ManyToOne
	private Country countryBorn;
	
	private String website;
	
	public Author() {
	}

	public Author(Long id, String firstName, String lastName, DateTime birthDate, Country countryBorn,
			String website) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.countryBorn = countryBorn;
		this.website = website;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public DateTime getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(DateTime birthDate) {
		this.birthDate = birthDate;
	}

	public Country getCountryBorn() {
		return countryBorn;
	}

	public void setCountryBorn(Country countryBorn) {
		this.countryBorn = countryBorn;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
}
