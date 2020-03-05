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

import com.coffeeshop.coffeeshop_app.data.UserDAO;
import com.coffeeshop.coffeeshop_app.data.UserUtilsDAO;
import com.coffeeshop.coffeeshop_app.model.Users;
import com.coffeeshop.coffeeshop_app.rest.JaxRsActivator;
import com.coffeeshop.coffeeshop_app.rest.UserWS;



@RunWith(Arquillian.class)
public class UserWSTest {

	
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(JavaArchive.class, "Test.jar")
				.addClasses(UserDAO.class, Users.class,
						JaxRsActivator.class,UserWS.class,
						UserUtilsDAO.class)
				
				.addAsManifestResource("META-INF/persistence.xml",
						"persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@EJB
	private UserWS userWs;
	
	@EJB
	private UserDAO userDAO;
	
	@EJB
	private UserUtilsDAO utilsDAO;
	
	private Users user;
	
	@Before
	public void setUp() throws Exception {
		utilsDAO.deleteTable();
		user = new Users();
		Users user2 = new Users();
		user.setUsername("Admin");
		user.setPassword("test");
		user.setType("Admin");
		userDAO.createNewUser(user);
		user2.setUsername("test");
		user2.setPassword("1");
		user2.setType("type");
		userDAO.createNewUser(user2);
		
	}

	@Test
	public void testGetAllUserWS() {
	Response response=userWs.findAllUsers();
	@SuppressWarnings("unchecked")
	List<Users> userList = (List<Users>) response.getEntity();
	assertEquals(HttpStatus.SC_OK, response.getStatus());
	assertEquals("Data fetch = data persisted", userList.size(), 2);
	Users user =userList.get(0);
	assertEquals("Admin",user.getUsername());
	Users user2 =userList.get(1);
	assertEquals("test",user2.getUsername());
	} 
	
	@Test
	public void testAddUser() {
		Response response=userWs.create(user);
		assertEquals(HttpStatus.SC_CREATED, response.getStatus());
		user = (Users) response.getEntity();
		assertEquals(user.getUsername(), "Admin");
		assertEquals(user.getPassword(), "test");
		assertEquals(user.getType(), "Admin");
	}
	
	
	@Test
	public void testUpdateUser() {
		Response response=userWs.validate("Admin");
		Users user = (Users) response.getEntity();
		user.setType("newtype");
		response=userWs.upadateUsers(user);
		assertEquals(HttpStatus.SC_OK, response.getStatus());
		user = (Users) response.getEntity();
		assertEquals(user.getUsername(), "Admin");
		assertEquals(user.getType(), "newtype");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteUser() {
		Response response=userWs.findAllUsers();
		List<Users> userList = (List<Users>) response.getEntity();
		assertEquals(userList.size(), 2);
		response=userWs.deleteUser("Admin");
		assertEquals(HttpStatus.SC_NO_CONTENT, response.getStatus());
		response=userWs.findAllUsers();
		userList = (List<Users>) response.getEntity();
		assertEquals(userList.size(), 1);
		response=userWs.validate("Admin");
		Users user = (Users) response.getEntity();
		assertEquals(HttpStatus.SC_OK, response.getStatus());
		assertEquals(null, user);
		
	}
}


