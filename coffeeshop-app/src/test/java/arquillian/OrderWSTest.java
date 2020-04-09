package arquillian;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
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
public class OrderWSTest {


	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(JavaArchive.class, "Test.jar")
				.addClasses(OrdersDAO.class, Orders.class,
						JaxRsActivator.class,OrderWS.class,
						OrderUtilsDAO.class)
				.addAsManifestResource("META-INF/persistence.xml",
						"persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@EJB
	private OrderWS Ws;

	@EJB
	private OrdersDAO dao;

	@EJB
	private OrderUtilsDAO  utilDAO;

	private Orders order;

	@Before
	public void setUp() throws Exception {
		utilDAO.deleteTable();
		order = new Orders();
		order.setCoffee("coff");
		order.setMilk("milk");
		order.setOrderID(1);
		order.setStatus("staus");
		order.setSugar(1);
		dao.createNewOrder(order);
	}

	@Test
	public void testGetAllOrderWS() {
		Response response=Ws.findAllOrders();
		@SuppressWarnings("unchecked")
		List<Orders> list = (List<Orders>) response.getEntity();
		assertEquals(HttpStatus.SC_OK, response.getStatus());
		assertEquals("Data fetch = data persisted", list.size(), 1);
		Orders order  =list.get(0);
		assertEquals("coff", order.getCoffee());
	} 

	@Test
	public void testAddCoffee() {
		order = new Orders();
		order.setCoffee("coff");
		order.setMilk("milk");
		order.setOrderID(2);
		order.setStatus("staus");
		order.setSugar(1);
		Response response=Ws.create(order);
		assertEquals(HttpStatus.SC_CREATED, response.getStatus());
		order = (Orders) response.getEntity();
		assertEquals(order.getCoffee(), "coff");
		assertEquals(order.getSugar(), 1);
		assertEquals(order.getMilk(), "milk");
		assertEquals(order.getStatus(), "staus");
		assertEquals(order.getOrderID(), 2);
	}




	@Test
	public void testUpdateOrder() {
		Response response=Ws.validate(1);
		order = (Orders) response.getEntity();
		order.setCoffee("coffnew");
		response=Ws.updateOrder(order);
		assertEquals(HttpStatus.SC_OK, response.getStatus());
		order = (Orders) response.getEntity();
		assertEquals(order.getCoffee(), "coffnew");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteOrder() {
		Response response=Ws.findAllOrders();
		List<Orders> list = (List<Orders>) response.getEntity();
		assertEquals(list.size(), 1);
		response=Ws.deleteOrder(1);
		assertEquals(HttpStatus.SC_NO_CONTENT, response.getStatus());
		response=Ws.findAllOrders();
		list = (List<Orders>) response.getEntity();
		assertEquals(list.size(),0);
		response=Ws.validate(1);
		order = (Orders) response.getEntity();
		assertEquals(HttpStatus.SC_OK, response.getStatus());
		assertEquals(null, order);

	}
}


