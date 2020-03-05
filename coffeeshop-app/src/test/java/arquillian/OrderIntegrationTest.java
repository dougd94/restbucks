package arquillian;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.coffeeshop.coffeeshop_app.data.OrderUtilsDAO;
import com.coffeeshop.coffeeshop_app.data.OrdersDAO;
import com.coffeeshop.coffeeshop_app.model.Orders;
import com.coffeeshop.coffeeshop_app.rest.JaxRsActivator;
import com.coffeeshop.coffeeshop_app.rest.OrderWS;


@RunWith(Arquillian.class)
public class OrderIntegrationTest {

	@Deployment
	public static Archive<?> createTestArchive() {

	    
		return ShrinkWrap
				.create(JavaArchive.class, "Test.jar")
				.addClasses(Orders.class, OrdersDAO.class,
						JaxRsActivator.class, OrderWS.class, OrderUtilsDAO.class)
//				.addPackage(org.apache.commons.codec.digest.DigestUtils.class.getPackage())
//				.addPackage(org.apache.commons.codec.binary.Hex.class.getPackage())
				//    .addPackage(EventCauseDAO.class.getPackage())
				//this line will pick up the production db
				.addAsManifestResource("META-INF/persistence.xml",
						"persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

	}

	@EJB
	private OrderWS WS;
	@EJB
	private OrdersDAO dao;
	@EJB
	private OrderUtilsDAO utils;

	private Orders orders;

	@Before
	public void setUp() {
		//this function means that we start with an empty table
		//it should be possible to test with an in memory db for efficiency
		utils.deleteTable();
		orders = new Orders();
		orders.setCoffee("cof");
		orders.setMilk("milk");
		orders.setOrderID(1);
		orders.setStatus("status");
		orders.setSugar(1);
		dao.createNewOrder(orders);
	}

	@Test
	public void testGetAllOrder() {
		List<Orders> orderList = dao.getAllOrders();
		assertEquals("Data fetch = data persisted", orderList.size(), 1);
	}
	
	@Test
	public void updateOrder() {
		orders.setMilk("milk2");
		dao.update(orders);
		List<Orders> orderList = dao.getAllOrders();
		assertEquals("Data fetch = data persisted", orderList.size(), 1);
	}
	
	@Test
	public void deleteOrder() {
		dao.delete(1);
		List<Orders> orderList = dao.getAllOrders();
		assertEquals("Data fetch = data persisted", orderList.size(), 0);
	}
}
