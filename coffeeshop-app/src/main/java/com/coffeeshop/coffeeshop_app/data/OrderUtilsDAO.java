package com.coffeeshop.coffeeshop_app.data;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class OrderUtilsDAO {

    @PersistenceContext
    private EntityManager em;
    
	public void deleteTable(){
		em.createQuery("DELETE FROM Orders").executeUpdate();
		em.createNativeQuery("ALTER TABLE Orders AUTO_INCREMENT=1")
		.executeUpdate();
		
	}
      
}
