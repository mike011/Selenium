import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class BasicScenarioTest {

	@Ignore
	@Test
	public void testWikipediaSearch() {
		WebDriver driver = new FirefoxDriver();

		driver.get("http://en.wikipedia.org");
		driver.findElement(By.id("searchInput")).sendKeys("The Matrix");
		driver.findElement(By.id("searchButton")).click();

		String title = driver.getTitle();
		assertThat(title, containsString("The Matrix"));

		driver.quit();
	}

	@Ignore
	@Test
	public void htmlUnitExample() {
		// Create a new instance of the html unit driver
		// Notice that the remainder of the code relies on the interface,
		// not the implementation.
		WebDriver driver = new HtmlUnitDriver();

		// And now use this to visit Google
		driver.get("http://www.google.com");

		// Find the text input element by its name
		WebElement element = driver.findElement(By.name("q"));

		// Enter something to search for
		element.sendKeys("Cheese!");

		// Now submit the form. WebDriver will find the form for us from the
		// element
		element.submit();

		// Check the title of the page
		System.out.println("Page title is: " + driver.getTitle());

		driver.quit();
	}

	@Test
	public void firefoxExample() {
		// The Firefox driver supports javascript
		WebDriver driver = new FirefoxDriver();

		// Go to the Google Suggest home page
		driver.get("http://www.google.com/webhp?complete=1&hl=en");

		// Enter the query string "Cheese"
		WebElement query = driver.findElement(By.name("q"));
		query.sendKeys("Cheese");

		List<WebElement> lst = driver.findElements(By
				.cssSelector(".gssb_e > .gssb_m > tbody > tr"));
		for (int i = 1; i <= lst.size(); i++) {
			By selector = By
					.cssSelector(".gssb_e > .gssb_m > tbody > tr:nth-of-type(i) > td > div > table  > tbody  > tr > td:nth-of-type(1) > span");
			WebElement el = driver.findElement(selector);
			System.out.println(el.getText());
		}

		driver.quit();
	}
}
