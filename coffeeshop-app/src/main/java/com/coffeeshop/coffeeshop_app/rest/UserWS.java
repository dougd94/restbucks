package com.coffeeshop.coffeeshop_app.rest;


import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.coffeeshop.coffeeshop_app.data.UserDAO;
import com.coffeeshop.coffeeshop_app.model.Users;


@Path("/users")
@Stateless
@LocalBean
public class UserWS {
	
	@EJB
	private UserDAO userDAO;
	
	@GET
	@Path("/{userName}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response validate(@PathParam("userName") String userName) {
		Users user = userDAO.getUserByUserName(userName);
		return Response.status(200).entity(user).build();
	}	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON })
	public Response findAllUsers() {
		List<Users> userList = userDAO.getAllUsers();
		return Response.status(200).entity(userList).build();
	}
	
	@POST
	@Produces({ MediaType.APPLICATION_JSON})
	public Response create(Users user) {
		userDAO.createNewUser(user);
		return Response.status(201).entity(user).build();
	}
	
	@PUT
	@Path("/{username}")
	@Consumes("application/json")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response upadateUsers(Users user) {
		userDAO.update(user);
		return  Response.status(200).entity(user).build();
	}

	@DELETE
	@Path("/{username}")
	public Response deleteUser(@PathParam("username") String username) {
		userDAO.delete(username);
		return Response.status(204).build();
	}
	
}
