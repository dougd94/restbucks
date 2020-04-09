package unit;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;

import com.coffeeshop.coffeeshop_app.data.UserDAO;
import com.coffeeshop.coffeeshop_app.model.Users;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;



public class UserDAOTest {
	private EntityManager em;
	private UserDAO dao;
	private Query query;
	private List<Users> expected, actual;

	@Before
	public void setup() {
		em = mock(EntityManager.class);
		dao = new UserDAO();
		dao.setEntityManager(em);
		query = mock(Query.class);
		when(em.createQuery("SELECT u FROM Users u")).thenReturn(query);
		expected = new ArrayList<>();
		when(query.getResultList()).thenReturn(expected);
		actual = dao.getAllUsers();
	}
	
	@Test
	public void testGetAllUsers() {
		assertSame(expected, actual);
	}
	

}
