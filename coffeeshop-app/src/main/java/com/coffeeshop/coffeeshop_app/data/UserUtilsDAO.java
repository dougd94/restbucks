package com.coffeeshop.coffeeshop_app.data;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
@LocalBean
public class UserUtilsDAO {

    @PersistenceContext
    private EntityManager em;
    
	public void deleteTable(){
		em.createQuery("DELETE FROM Users").executeUpdate();
		em.createNativeQuery("ALTER TABLE Users AUTO_INCREMENT=1")
		.executeUpdate();
		
	}
      
}
