import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class StravaTest {

	private RemoteWebDriver driver;

	@Before
	public void startDriver() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.MINUTES);
		//driver.getCapabilities().
	}

	@After
	public void stopDriver() {
		driver.close();
	}

	@Test
	public void strava() {
		driver.get("http://www.strava.com/");
		driver.findElement(By.linkText("Log In")).click();
		WebElement query = driver.findElement(By.name("email"));
		query.sendKeys("mike_ch_1@hotmail.com");
		
		query = driver.findElement(By.name("password"));
		query.sendKeys(Password.password);
		
		query = driver.findElement(By.id("login-button"));
		query.click();
		
		driver.get("http://www.strava.com/athlete/training");

		//baseTable = driver.findElement(By.xpath("//[tr]"));
		//List<WebElement> trs = driver.findElementsByXPath("//tr[@class='training-activity-row']");
		List<WebElement> trs = driver.findElementsByClassName("training-activity-row");
		assertFalse(trs.isEmpty());
		List<String> links = new ArrayList<String>();
		for(WebElement row : trs) {
			WebElement key = row.findElement(By.tagName("a"));
			links.add(key.getAttribute("href"));
		}
		
		for(String link : links) {
			driver.get(link);
			
			query = driver.findElementByClassName("marginless.activity-name");
			query.getText();
		}
		
	}

}