package seleniumStepDefinitions;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.coffeeshop.coffeeshop_app.data.UserDAO;
import com.coffeeshop.coffeeshop_app.data.UserUtilsDAO;
import com.coffeeshop.coffeeshop_app.model.Users;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {

	public static WebDriver driver;

	WebElement order;
	private UserDAO dao = new UserDAO();
	private UserUtilsDAO utilsDAO;
	@Before
	public void beforeTest() {
		//setup the chromedriver using WebDriverManager
		//Create Chrome Options
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized", "--headless", "--no-sandbox");
		WebDriverManager.chromedriver().version("80.0.3987.106").setup();
		driver = new ChromeDriver(options);
		utilsDAO.deleteTable();
		Users users = new Users();
		users.setPassword("customer");
		users.setType("Customer");
		users.setUserID(1);
		users.setUsername("Customer");
		dao.createNewUser(users);
		Users users2 = new Users();
		users2.setPassword("worker");
		users2.setType("Worker");
		users2.setUserID(2);
		users2.setUsername("Worker");
		dao.createNewUser(users2);
		//			System.setProperty("webdriver.chrome.driver", "C:\\Users\\a00207675\\Documents\\chromedriver.exe");
		//			driver = new ChromeDriver();
	}


	@After
	public void afterTest() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Given("^the user is on the login page$")
	public void userOnLoginPage() throws InterruptedException {
		driver.get("http://localhost:8080/coffeeshop-app");
		System.out.println("User is on the login page");
	}

	@When("^the user enters username as (.*)$")
	public void userEntersUsername(String username) throws InterruptedException {
		WebElement burger = driver.findElement(By.id("burger"));
		burger.click();
		Thread.sleep(3000);
		WebElement login = driver.findElement(By.id("loginButton"));
		login.click();
		//		WebElement user = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/form/div[1]/input"));
		new WebDriverWait(driver,30).until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div/div[2]/form/div[1]/input"))).sendKeys(username);
		//		user.sendKeys(username);
		//		driver.findElement(By.id("username")).sendKeys(username);
		System.out.println("User enters valid Username");
	}

	@And("^user enters password as (.*)$")
	public void userEntersPasword(String password) {
		driver.findElement(By.id("password")).click();
		new WebDriverWait(driver,30).until(ExpectedConditions.elementToBeClickable(By.id("password"))).sendKeys(password);
		//		WebElement passsword =driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/form/div[2]/input")).sendKeys(password);
		System.out.println("User enters valid Password");
	}

	@And("^the user clicks on login$")
	public void userClicksLogin() throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(By.id("submit1")).click();
		System.out.println("User Clicks Login");
	}


	@Then("the user should see order or see order ORDER COFFEE")
	public void the_user_should_see_order_or_see_order_ORDER_COFFEE() throws InterruptedException {
		WebElement burger = driver.findElement(By.id("burger"));
		burger.click();
		Thread.sleep(1000);
		order  = driver.findElement(By.id("order"));
		String message = order.getText();
		assertEquals("ORDER COFFEE", message);
		System.out.println("User sees order coffee");
	}

	@Then("the user should see order or see order SEE ORDERS")
	public void the_user_should_see_order_or_see_order_SEE_ORDERS() throws InterruptedException {
		WebElement burger = driver.findElement(By.id("burger"));
		burger.click();
		Thread.sleep(1000);
		order = driver.findElement(By.id("SeeOrders"));
		String message = order.getText();
		assertEquals("SEE ORDERS", message);
		System.out.println("User sees see orders");
		//	    throw new io.cucumber.java.PendingException();
	}



}