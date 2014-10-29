import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MeetupTest {

	private RemoteWebDriver driver;

	String meetup = "seleniumlondon"; // meetup name from URL
	String event = "14712022"; // event ID from URL

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

	@Ignore
	@Test
	public void listResponses() throws Exception {

		// open the event page
		String link = "http://www.meetup.com/" + meetup + "/calendar/" + event
				+ "/";
		link = "http://www.meetup.com/Doctor-Who-viewing-party-in-Cambridge-Ontario/";
		driver.get(link);
		if (driver
				.findElement(
						By.xpath("id('C_document')/descendant::dl[@class='stats'][2]/dt"))
				.getText().equals("Who’s coming?")) {
			// this event has not yet occurred
			listMembers(
					"id('C_document')//li[div[@class='D_attendeeHeader D_yes']]//li[starts-with(@id, 'member_')]",
					"Yes");
			listMembers(
					"id('C_document')//li[div[@class='D_attendeeHeader D_maybe']]//li[starts-with(@id, 'member_')]",
					"Maybe");
			listMembers(
					"id('C_document')//li[div[@class='D_attendeeHeader D_no']]//li[starts-with(@id, 'member_')]",
					"No");
		} else {
			// this event is in the past
			listMembers("id('C_document')//li[starts-with(@id, 'member_')]",
					"Attended");
		}
	}

	public void listMembers(String xpath, String label) {

		// get a list of all members that have responded or attended
		List<WebElement> memberElements = driver.findElements(By.xpath(xpath));

		int guestTotal = 0; // set guest total to zero
		List<Member> members = new ArrayList<Member>(); // create a list to
														// store our members
		for (WebElement member : memberElements) {
			String name = member.findElement(By.className("D_name")).getText(); // member
																				// name

			int guests = 0; // set member's guests to zero

			// get the number of guests for this member
			try {
				// upcoming events use a different class for the guest count as
				// past events
				String guestsTemp = member.findElement(By.className("guests"))
						.getText();
				guests = new Integer(guestsTemp.substring(
						guestsTemp.indexOf("+") + 1, guestsTemp.indexOf(" ")));
			} catch (NoSuchElementException e) {
			}

			try {
				String guestsTemp = member
						.findElement(By.className("D_guests")).getText();
				// past events use a different format for the guest count
				guests = new Integer(guestsTemp.substring(
						guestsTemp.indexOf("+") + 1, guestsTemp.indexOf(")")));
			} catch (NoSuchElementException e) {
			}

			guestTotal = guestTotal + guests; // update the total number of
												// guests for this event
			members.add(new Member(name, guests)); // add the current member to
													// our list
		}

		// output a label for this list including total member and guest counts
		System.out.println("\n" + members.size() + " " + label
				+ getGuestSuffix(guestTotal));

		for (int i = 0; i < members.size(); i++) {
			// output details for each member
			System.out.println(i + 1 + ". " + members.get(i).name
					+ getGuestSuffix(members.get(i).guests));
		}
	}

	public String getGuestSuffix(int guests) {
		// return a suffix to indicate the number of guests
		if (guests > 0) {
			return " (+" + guests + " guest" + (guests > 1 ? "s" : "") + ")";
		} else {
			return "";
		}
	}

	// inner class for members
	public class Member {

		String name;
		int guests;

		public Member(String name, int guests) {
			this.name = name;
			this.guests = guests;
		}

	}

	@Ignore
	@Test
	public void listPizzas() {
		driver.get("http://www.thecheesecakefactory.com/");
		driver.findElement(By.linkText("Menu")).click();
		driver.findElement(By.linkText("Pizza")).click();
		List<WebElement> cheesecakes = driver.findElements(By
				.xpath("id('leftNav_levelTwo')//li"));

//		System.out.println(cheesecakes.size() + " cheesecakes:");
//		for (int i = 0; i < cheesecakes.size(); i++) {
//			System.out.println(i + 1 + ". " + cheesecakes.get(i).getText());
//		}
		assertFalse(cheesecakes.isEmpty());
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