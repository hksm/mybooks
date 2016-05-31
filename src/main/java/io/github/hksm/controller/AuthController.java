package io.github.hksm.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

import io.github.hksm.dao.UserDao;
import io.github.hksm.model.UserData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component @Path("/auth") 
public class AuthController {

	@Inject
	private UserDao userDao;
	
	private final Map<String, List<String>> userDb = new HashMap<>();
	private String secretkey = "thisismysecretkey";

    public AuthController() {
        userDb.put("user", Arrays.asList("user"));
        userDb.put("admin", Arrays.asList("user", "admin"));
    }
	
	@POST @Path("/login") @Produces("application/json")
	public LoginResponse login(final UserLogin login) throws ServletException {
		if (login.username == null || login.password == null) {
			throw new ServletException("Invalid login");
		}
		
		UserData userData = userDao.findByUsername(login.username);
		if (userData == null ||	!BCrypt.checkpw(login.password, userData.getPassword())) {
		    throw new ServletException("Invalid login");
		}
		
		return new LoginResponse(Jwts.builder().setSubject(login.username)
		    .claim("roles", userData.getRoles()).setIssuedAt(new Date())
		    .signWith(SignatureAlgorithm.HS256, secretkey).compact());
	}

	private static class UserLogin {
		public String username;
		public String password;
	}

	@SuppressWarnings("unused")
	private static class LoginResponse {
		public String token;
	
		public LoginResponse(final String token) {
			this.token = token;
		}
	}

	@SuppressWarnings("unchecked")
	@GET @Path("/role/{roleParam}")
	public Boolean checkRole(@PathParam("roleParam") final String role,
			@Context HttpServletRequest request) throws ServletException {
		final Claims claims = (Claims) request.getAttribute("claims");
		return ((List<String>) claims.get("roles")).contains(role);
	}
	
	@POST @Path("/register")
	public Response add(UserData user) {
		try {
			user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
			Set<String> roles = new HashSet<>();
			roles.add("user");
			user.setRoles(roles);
			userDao.insert(user);
		} catch(Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		return Response.ok().build();
	}
}