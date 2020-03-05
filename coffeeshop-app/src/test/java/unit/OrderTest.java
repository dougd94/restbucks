package unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeeshop.coffeeshop_app.model.Orders;
class OrderTest {

	private Orders orders;
	@BeforeEach
	public void setUp() throws Exception {
		orders = new Orders();
		orders.setCoffee("cof");
		orders.setMilk("milk");
		orders.setOrderID(1);
		orders.setStatus("status");
		orders.setSugar(1);
	}

	@Test
	public void testOrders() {
		assertEquals(1, orders.getSugar());
		assertEquals(1, orders.getOrderID());
		assertEquals("milk", orders.getMilk());
		assertEquals("cof", orders.getCoffee());
		assertEquals("status", orders.getStatus());
	}

}
