package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeeshop.coffeeshop_app.data.OrdersDAO;
import com.coffeeshop.coffeeshop_app.model.Orders;


class OrderDAOTest {
	private EntityManager em;
	private OrdersDAO dao;
	private Query query;
	private List<Orders> expected, actual;

	@Before
	public void setup() {
		em = mock(EntityManager.class);
		dao = new OrdersDAO();
		dao.setEntityManager(em);
		query = mock(Query.class);
		when(em.createQuery("SELECT u FROM Orders u")).thenReturn(query);
		expected = new ArrayList<>();
		when(query.getResultList()).thenReturn(expected);
		actual = dao.getAllOrders();
	}
	
	@Test
	public void testGetAllOrders() {
		assertSame(expected, actual);
	}
	

	
}
