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
	@Produces({MediaType.APPLICATION_JSON })
	public Response findAllUsers() {
		List<Users> userList = userDAO.getAllUsers();
		return Response.status(200).entity(userList).build();
	}
	

}
