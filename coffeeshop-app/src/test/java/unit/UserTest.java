package unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeeshop.coffeeshop_app.model.Users;

class UserTest {

	private Users user;
	@BeforeEach
	public void setUp() throws Exception {
		user = new Users();
		user.setPassword("1");
		user.setType("admin");
		user.setUserID(1);
		user.setUsername("admin");
	}

	@Test
	public void testUser() {
		assertEquals(1, user.getUserID());
		assertEquals("1", user.getPassword());
		assertEquals("admin", user.getType());
		assertEquals("admin", user.getUsername());
	}

}
