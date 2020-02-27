package com.coffeeshop.coffeeshop_app.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
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

import com.coffeeshop.coffeeshop_app.data.OrdersDAO;
import com.coffeeshop.coffeeshop_app.data.UserDAO;
import com.coffeeshop.coffeeshop_app.model.Orders;
import com.coffeeshop.coffeeshop_app.model.Users;

@Path("/orders")
@Stateless
@LocalBean
public class OrderWS {
	@EJB
	private OrdersDAO OrderDAO;
	
	@GET
	@Path("/{orderID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response validate(@PathParam("orderID") int orderID) {
		Orders order = OrderDAO.getOrderByID(orderID);
		return Response.status(200).entity(order).build();
	}	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON })
	public Response findAllOrders() {
		List<Orders> orderList = OrderDAO.getAllOrders();
		return Response.status(200).entity(orderList).build();
	}
	@POST
	@Produces({ MediaType.APPLICATION_JSON})
	public Response create(Orders order) {
		OrderDAO.createNewOrder(order);
		return Response.status(201).entity(order).build();
	}
	
	@PUT
	@Path("/{orderID}")
	@Consumes("application/json")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateOrder(Orders order) {
		OrderDAO.update(order);
		return  Response.status(200).entity(order).build();
	}

	@DELETE
	@Path("/{OrderID}")
	public Response deleteOrder(@PathParam("OrderID") int OrderID) {
		OrderDAO.delete(OrderID);
		return Response.status(204).build();
	}
}
