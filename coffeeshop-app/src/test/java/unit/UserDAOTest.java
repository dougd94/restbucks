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
	
	@Test
	public void testSaveUser() {
		Users user = new Users();
		user.setUserID(20);
		user.setType("Admin");
		user.setUsername("Admin2020");
		user.setPassword("asssword2020");
		expected.add(user);
		when(em.merge(user)).thenReturn(user);
		actual.add(em.merge(user));
		dao.update(user);
		when(em.createQuery("SELECT u FROM Users u where User.userID = ?")).thenReturn(query);
		when(query.setParameter(0, 20)).thenReturn(query);
		when(query.getResultList()).thenReturn(expected);
		Users loaded = dao.getUserByID(user.getUserID());
		assertEquals(user, loaded);
	}
	

}
