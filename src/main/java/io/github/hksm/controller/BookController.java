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
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import io.github.hksm.dao.BookDao;
import io.github.hksm.model.Book;

@Component @Path("/books") 
public class BookController {

	@Inject
	BookDao bookDao;
	
	@GET @Produces("application/json")
	public List<Book> getAll() {
		return bookDao.findAll();
	}
	
	@GET @Path("/{id}") @Produces("application/json")
	public Book getById(@PathParam("id") Long id) {
		return bookDao.findOne(id);
	}
	
	@GET @Path("/query") @Produces("application/json")
	public List<Book> getByTitle(@PathParam("title") String title) {
		return bookDao.findByTitle(title);
	}
		
	@POST
	public Response add(Book book) {
		try {
			bookDao.insert(book);
		} catch(Exception e) {
			return Response.serverError().build();
		}
		return Response.ok().build();
	}
	
	@PUT @Path("/{id}")
	public Response replace(@PathParam("id") Long id, Book book) {
		try {
			bookDao.update(book);
		} catch(Exception e) {
			return Response.serverError().build();
		}
		return Response.ok().build();
	}
	
	@DELETE @Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		try {
			bookDao.remove(id);
		} catch(Exception e) {
			return Response.serverError().build(); 
		}
		return Response.ok().build();
	}
}
