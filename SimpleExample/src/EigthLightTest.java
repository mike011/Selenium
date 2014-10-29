import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class EigthLightTest {

	@Test
	public void getArticles() throws ParseException {

		String lastUploadedDateString = "2014/10/10";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date lastUploadedDate = sdf.parse(lastUploadedDateString);

		WebDriver driver = new FirefoxDriver();

		driver.get("http://blog.8thlight.com/");
		List<WebElement> findElements = driver.findElements(By
				.tagName("article"));
		for (WebElement ele : findElements) {
			WebElement key = ele.findElement(By.tagName("a"));
			String href = key.getAttribute("href");
			int s = href.indexOf("2014");
			String dateString = href.substring(s,
					s + lastUploadedDateString.length());
			Date format = sdf.parse(dateString);
			if (format.getTime() > lastUploadedDate.getTime()) {
				System.out.println(href + " (" + key.getText()+")");
			}

		}

		driver.quit();
	}

}
