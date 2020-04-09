package com.coffeeshop.coffeeshop_app.data;


import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.coffeeshop.coffeeshop_app.model.Users;




@Stateless
@LocalBean
public class UserDAO {
	
    @PersistenceContext
    private EntityManager em;
    
    public void setEntityManager(EntityManager em) {
    	this.em = em;
    }
    

	public List<Users> getAllUsers() {
    	Query query=em.createQuery("SELECT u FROM Users u");
        return query.getResultList();
    }
	
}