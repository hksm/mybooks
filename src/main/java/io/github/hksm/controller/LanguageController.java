package io.github.hksm.controller;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.stereotype.Component;

import io.github.hksm.dao.LanguageDao;
import io.github.hksm.model.Language;

@Component @Path("/languages") 
public class LanguageController {

	@Inject
	LanguageDao languageDao;
	
	@GET @Produces("application/json")
	public List<Language> getAll() {
		return languageDao.findAll();
	}
	
	@GET @Path("/{id}") @Produces("application/json")
	public Language getById(@PathParam("id") Long id) {
		return languageDao.findOne(id);
	}
	
	@GET @Path("/query") @Produces("application/json")
	public Language getByCodeOrName(@QueryParam("code") String code, 
									@QueryParam("name") String name) {
		Language language = null;
		if (!code.isEmpty()) {
			language = languageDao.findByCode(code);
		} else {
			language = languageDao.findByName(name);
		}
		return language;
	}
}
