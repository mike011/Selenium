import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.apple.IOSPage;
import com.apple.IPadPage;
import com.apple.LandingPage;

@SuppressWarnings("unused")
public class PageObjectTest {

	WebDriver driver;

	@Before
	public void setupBrowser() throws Exception {
		// Setup the Browser
		driver = new FirefoxDriver();

		// Slow loading pages, always try and wait up to 5 seconds
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@After
	public void tearDownBrowser() {
		driver.quit();
	}

	@Test
	public void testAppleNavigation() throws Exception {
		driver.get("http://apple.com");

		LandingPage landingPage = new LandingPage(driver);
		landingPage.goToIPad();
		
		IPadPage iPadPage = new IPadPage(driver);
		iPadPage.ios();
		
		IOSPage iosPage = new IOSPage(driver);
		Thread.sleep(2000);
		iosPage.goHome();
		
		landingPage = new LandingPage(driver);
		landingPage.searchFor("Bill Gates");

		Thread.sleep(5000);
	}

}
