package io.github.hksm.controller;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import io.github.hksm.dao.GenreDao;
import io.github.hksm.model.Genre;

@Component @Path("/genres") 
public class GenreController {

	@Inject
	GenreDao genreDao;
	
	@GET @Produces("application/json")
	public List<Genre> getAll() {
		return genreDao.findAll();
	}
	
	@GET @Path("/{id}") @Produces("application/json")
	public Genre getById(@PathParam("id") Long id) {
		return genreDao.findOne(id);
	}
	
	@GET @Path("/query") @Produces("application/json")
	public List<Genre> getByName(@QueryParam("name") String name) {
		return genreDao.findByName(name);
	}
	
	@POST
	public Response add(Genre genre) {
		try {
			genreDao.insert(genre);
		} catch(Exception e) {
			return Response.serverError().build();
		}
		return Response.ok().build();
	}
	
	@PUT @Path("/{id}")
	public Response replace(@PathParam("id") Long id, Genre genre) {
		try {
			genreDao.update(genre);
		} catch(Exception e) {
			return Response.serverError().build();
		}
		return Response.ok().build();
	}
	
	@DELETE @Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		try {
			genreDao.remove(id);
		} catch(Exception e) {
			return Response.serverError().build(); 
		}
		return Response.ok().build();
	}
}
