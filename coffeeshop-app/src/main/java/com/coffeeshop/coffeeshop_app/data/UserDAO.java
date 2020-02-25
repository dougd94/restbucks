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
	
	public Users getUserByID(int id) {
		Query query = em.createQuery("SELECT u FROM Users u where User.userID = ?");
		query.setParameter(0, id);
		return (Users) query.getResultList().get(0);
	}

    public Users getUserByUserName(String username) {
        return em.find(Users.class, username);
    }
	

	public void update(Users user) {
		em.merge(user);
	}

	public void delete(String username) {
		
		em.remove(getUserByUserName(username));
	}
	
	
	public void createNewUser(Users user) {
		em.persist(user);
		System.out.println("User created");
	}
	
	
}