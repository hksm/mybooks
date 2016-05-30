package io.github.hksm.controller;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.stereotype.Component;

import io.github.hksm.dao.CountryDao;
import io.github.hksm.model.Country;

@Component @Path("/countries") 
public class CountryController {

	@Inject
	CountryDao countryDao;
	
	@GET @Produces("application/json")
	public List<Country> getAll() {
		return countryDao.findAll();
	}
	
	@GET @Path("/{id}") @Produces("application/json")
	public Country getById(@PathParam("id") Long id) {
		return countryDao.findOne(id);
	}
	
	@GET @Path("/query") @Produces("application/json")
	public Country getByCodeOrName(@QueryParam("code") String code, 
								   @QueryParam("name") String name) {
		Country country = null;
		if (!code.isEmpty()) {
			country = countryDao.findByCode(code);
		} else {
			country = countryDao.findByName(name);
		}
		return country;
	}
}
