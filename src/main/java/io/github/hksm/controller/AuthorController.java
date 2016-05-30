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

import io.github.hksm.dao.AuthorDao;
import io.github.hksm.model.Author;

@Component @Path("/authors") 
public class AuthorController {

	@Inject
	AuthorDao authorDao;
	
	@GET @Produces("application/json")
	public List<Author> getAll() {
		return authorDao.findAll();
	}
	
	@GET @Path("/{id}") @Produces("application/json")
	public Author getById(@PathParam("id") Long id) {
		return authorDao.findOne(id);
	}
	
	@GET @Path("/query") @Produces("application/json")
	public List<Author> getByName(@QueryParam("name") String name) {
		return authorDao.findByFirstOrLastName(name);
	}
	
	@POST
	public Response add(Author author) {
		try {
			authorDao.insert(author);
		} catch(Exception e) {
			return Response.serverError().build();
		}
		return Response.ok().build();
	}
	
	@PUT @Path("/{id}")
	public Response replace(@PathParam("id") Long id, Author author) {
		try {
			authorDao.update(author);
		} catch(Exception e) {
			return Response.serverError().build();
		}
		return Response.ok().build();
	}
	
	@DELETE @Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		try {
			authorDao.remove(id);
		} catch(Exception e) {
			return Response.serverError().build(); 
		}
		return Response.status(200).build();
	}
}
