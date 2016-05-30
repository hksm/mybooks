package io.github.hksm.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

@Entity
public class Book implements Serializable {

	private static final long serialVersionUID = -5955428965989994381L;

	@Id @GeneratedValue
	private Long id;
	
	@NotNull
	private String title;
	
	@ManyToMany(fetch=FetchType.EAGER) @NotNull
	private Set<Author> authors;
	
	@ManyToMany(fetch=FetchType.EAGER) @NotNull
	private Set<Genre> genres;
	
	@NotNull @ManyToOne
	private Language language;
	
	@NotNull
	private DateTime publishingDate;
	
	private String publisher;
	private Integer pages;
	private String coverLink;
	
	public Book() {
	}

	public Book(Long id, String title, Set<Author> authors, Set<Genre> genres, Language language, 
			DateTime publishingDate, String publisher, Integer pages, String coverLink) {
		this.id = id;
		this.title = title;
		this.authors = authors;
		this.genres = genres;
		this.language = language;
		this.publisher = publisher;
		this.pages = pages;
		this.publishingDate = publishingDate;
		this.coverLink = coverLink;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public DateTime getPublishingDate() {
		return publishingDate;
	}

	public void setPublishingDate(DateTime publishingDate) {
		this.publishingDate = publishingDate;
	}

	public String getCoverLink() {
		return coverLink;
	}

	public void setCoverLink(String coverLink) {
		this.coverLink = coverLink;
	}

	@Override
	public String toString() {
		return title;
	}
}