package seleniumStepDefinitions;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LogoutTest {
	public static WebDriver driver;

	WebElement loginHeader;

	@Before
	public void beforeTest() {
		//setup the chromedriver using WebDriverManager
    	

	    //Create Chrome Options
    	  ChromeOptions options = new ChromeOptions();
          options.addArguments("--start-maximized", "--headless", "--no-sandbox");
          WebDriverManager.chromedriver().version("80.0.3987.106").setup();
          driver = new ChromeDriver(options);
	}

	@After
	public void afterTest() {
		if (driver != null) {
			driver.quit();
		}
	}
	@Given("a user is logged in")
	public void a_user_is_logged_in() throws InterruptedException {
		driver.get("http://localhost:8080/coffeeshop-app");
		System.out.println("User is on the login page");
		WebElement burger = driver.findElement(By.id("burger"));
		burger.click();
		Thread.sleep(3000);
		WebElement login = driver.findElement(By.id("submit1"));
		new WebDriverWait(driver,30).until(ExpectedConditions.elementToBeClickable(By.id("username"))).sendKeys("Customer");
		driver.findElement(By.id("password")).click();
		new WebDriverWait(driver,30).until(ExpectedConditions.elementToBeClickable(By.id("password"))).sendKeys("customer");
		login.click();
	}

	@When("a logged-in user clicks the logout button")
	public void a_logged_in_user_clicks_the_logout_button() throws Throwable {
		driver.findElement(By.id("currentUserName")).click();
//		driver.findElement(By.id("logout")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("logout")).click();
	}

	@Then("the Log in page is displayed")
	public void the_Authentication_page_is_displayed() throws Throwable {
		Thread.sleep(1000);
		WebElement loginButton = driver.findElement(By.id("loginButton"));
		String message = loginButton.getText();
		assertEquals("LOG IN", message);
		System.out.println("User brought to login page");
	}
}
