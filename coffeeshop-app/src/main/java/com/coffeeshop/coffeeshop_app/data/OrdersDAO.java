package com.coffeeshop.coffeeshop_app.data;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.coffeeshop.coffeeshop_app.model.Orders;
import com.coffeeshop.coffeeshop_app.model.Users;

@Stateless
@LocalBean
public class OrdersDAO {
	
    @PersistenceContext
    private EntityManager em;
    
    public void setEntityManager(EntityManager em) {
    	this.em = em;
    }
    
	@SuppressWarnings("unchecked")
	public List<Orders> getAllOrders() {
    	Query query=em.createQuery("SELECT u FROM Orders u");
        return query.getResultList();
    }
	
	public Orders getOrderByID(int orderID) {
		Query query = em.createQuery("SELECT u FROM Orders u where Order.orderID = ?");
		query.setParameter(0, orderID);
		return (Orders) query.getResultList().get(0);
	}
	

	public void update(Orders order) {
		em.merge(order);
	}

	public void delete(int OrderID) {
		em.remove(getOrderByID(OrderID));
	}
	
	
	public void createNewOrder(Orders order) {
		em.persist(order);
		System.out.println("Order created");
	}

}
