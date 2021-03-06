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
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;

import com.coffeeshop.coffeeshop_app.data.UserDAO;
import com.coffeeshop.coffeeshop_app.data.UserUtilsDAO;
import com.coffeeshop.coffeeshop_app.model.Users;
import com.coffeeshop.coffeeshop_app.rest.JaxRsActivator;
import com.coffeeshop.coffeeshop_app.rest.UserWS;


@RunWith(Arquillian.class)
public class UserIntegrationTest {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				
				.create(JavaArchive.class, "Test.jar")
				.addClasses(Users.class, UserDAO.class,
						JaxRsActivator.class, UserWS.class, UserUtilsDAO.class)
				.addAsManifestResource("META-INF/persistence.xml",
						"persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

	}

	@EJB
	private UserWS userWS;
	@EJB
	private UserDAO userDAO;
	@EJB
	private UserUtilsDAO utilsDAO;

	private Users user;

	@Before
	public void setUp() {
		//this function means that we start with an empty table
		//it should be possible to test with an in memory db for efficiency

	}

	@Test
	public void testGetAllUsers() {
		List<Users> userList = userDAO.getAllUsers();
		assertEquals("Data fetch = data persisted", userList.size(), 2);
	}

}
