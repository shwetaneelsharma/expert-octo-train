package hackathonapplitools.grid.tests.traditionalv2;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import hackathonapplitools.grid.common.BaseTest;
import hackathonapplitools.grid.common.SelectorConstants;

public class ShoppingExperienceTest extends BaseTest {

	// Define browsers and viewports

	String[][] viewPorts = new String[][] {
			{ BROWSER_CHROME, "1200X700", "Laptop" },
			{ BROWSER_FIREFOX, "1200X700", "Laptop" },
			{ BROWSER_CHROME, "768X700", "Tablet" },
			{ BROWSER_FIREFOX, "768X700", "Tablet" },
			{ BROWSER_CHROME, "500X700", "Phone" },
			{ BROWSER_FIREFOX, "500X700", "Phone" }, };

	int viewIndex = 0;

	SoftAssert softAssert = new SoftAssert();

	@BeforeClass
	public void init() {
		resultFile = "Traditional-V2-TestResults.txt";
	}

	@BeforeMethod
	public void registerDriver() {
		browser = viewPorts[viewIndex][0];
		viewport = viewPorts[viewIndex][1];
		device = viewPorts[viewIndex][2];
		viewIndex++;
		if (viewIndex == 6) {
			viewIndex = 0;
		}

		super.registerDriver();

		// Launch the Applitools hackathon Website
		driver.get("https://demo.applitools.com/gridHackathonV2.html");
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		driver.manage().window()
				.setSize(new Dimension(Integer.parseInt(viewport.split("X")[0]),
						Integer.parseInt(viewport.split("X")[1])));

	}

	// Method to apply filter by colour black on any device or viewport
	public void applyFilter() throws Throwable {

		if (device != "Laptop") {
			util.getElementID(SelectorConstants.MOBILE_FILTER_ID).click();
			Thread.sleep(2000);
			driver.findElement(
					By.xpath("//div[@id='sidebar_filters']/div[2]/div/ul/li"))
					.click();
			util.getElementID(SelectorConstants.SELECTOR_FILTER_BUTTON_ID)
					.click();
			Thread.sleep(2000);
		} else {
			util.getElementID(SelectorConstants.SELECTOR_BLACK_COLOUR_ID)
					.click();
			util.getElementID(SelectorConstants.SELECTOR_FILTER_BUTTON_ID)
					.click();
		}

	}

	@Test(priority = 1, invocationCount = 6)
	public void verifyFilter() throws Throwable {
		String expectedCount;
		// Verify the number of items displayed after the filter is applied
		if (device != "Laptop") {
			util.getElementID(SelectorConstants.MOBILE_FILTER_ID).click();
			Thread.sleep(2000);
			expectedCount = driver
					.findElement(By.xpath(
							"//label[@id=\"LABEL__containerc__104\"]/small"))
					.getText();
			driver.findElement(
					By.xpath("//div[@id='sidebar_filters']/div[2]/div/ul/li"))
					.click();
			util.getElementID(SelectorConstants.SELECTOR_FILTER_BUTTON_ID)
					.click();
			Thread.sleep(2000);
		} else {
			applyFilter();
			expectedCount = driver.findElement(By.id("SMALL____105")).getText();
		}

		List<WebElement> gridItems = driver.findElements(
				By.className(SelectorConstants.SELECTOR_GRID_ITEM_CLASS));

		softAssert.assertTrue(
				hackathonReporter(2, "Filter for Black Shoes is working",
						SelectorConstants.SELECTOR_GRID_ITEM_CLASS,
						(null != gridItems && gridItems.size() == Integer
								.parseInt(expectedCount))));
		verifyGridItems();

		// Verify the header and footer is displayed on PLP after filter is
		// displayed
		if (device == "Laptop") {
			verifyById(2,
					new String[] { "Header is displayed after applying filter",
							"Filters are displayed after applying any filter",
							"Footer is displayed after applying filter" },
					new String[] { SelectorConstants.SELECTOR_PAGE_HEADER_ID,
							SelectorConstants.SELECTOR_FILTER_ID,
							SelectorConstants.V2_SELECTOR_PAGE_FOOTER_ID },
					new boolean[] { true, true, true });
		} else {
			util.getElementID(SelectorConstants.MOBILE_FILTER_ID).click();
			Thread.sleep(3000);
			verifyById(2,
					new String[] {
							"Filters are displayed after applying any filter" },
					new String[] { SelectorConstants.SELECTOR_FILTER_ID, },
					new boolean[] { true });
			util.getElementID(
					SelectorConstants.SELECTOR_CLOSE_FILTER_SIDEBAR_ID).click();
			Thread.sleep(2000);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			verifyByLink(2, new String[] {
					"About us link is displayed in the footer on Product listing page",
					"Faq link is displayed in the footer on Product listing page",
					"Faq link is displayed in the footer on Product listing page",
					"My account link is displayed in the footer on Product listing page",
					"Blog link is displayed in the footer on Product listing page" },
					new String[] { SelectorConstants.ABOUT_US_LINK,
							SelectorConstants.FAQ_LINK,
							SelectorConstants.HELP_LINK,
							SelectorConstants.MY_ACCOUNT_LINK,
							SelectorConstants.BLOG_LINK, },
					new boolean[] { true, true, true, true, true });

			driver.findElement(By.xpath("//footer/div/div/div[2]/h3")).click();
			verifyByLink(2, new String[] {
					"Contacts link is displayed in the footer on Product listing page",
					"Email link is displayed in the footer on Product listing page", },
					new String[] { SelectorConstants.CONTACTS_LINK,
							SelectorConstants.EMAIL_LINK, },
					new boolean[] { true, true });
			driver.findElement(By.xpath("//footer/div/div/div[3]/h3")).click();
			Thread.sleep(3000);
			verifyById(2, new String[] {
					"Subscription box is displayed in the footer on Product listing page" },
					new String[] { SelectorConstants.SELECTOR_NEWSLETTER_ID },
					new boolean[] { true });
		}

	}

	// Method to verify reset filter functionality
	@Test(priority = 2, invocationCount = 6)
	public void verifyResetFilter() throws Throwable {
		applyFilter();
		if (device != "Laptop") {
			util.getElementID(SelectorConstants.MOBILE_FILTER_ID).click();
		}
		Thread.sleep(3000);
		util.getElementID(SelectorConstants.SELECTOR_RESET_FILTER_BUTTON_ID)
				.click();
		Thread.sleep(3000);

		// Verify the items displayed after the filter is reset
		List<WebElement> gridItems = driver.findElements(
				By.className(SelectorConstants.SELECTOR_GRID_ITEM_CLASS));
		softAssert.assertTrue(hackathonReporter(2, "Reset Filter is working",
				SelectorConstants.SELECTOR_GRID_ITEM_CLASS,
				(null != gridItems && gridItems.size() == 9)));

	}

	public void verifyGridItems() throws Throwable {
		List<WebElement> gridItems = driver.findElements(
				By.className(SelectorConstants.SELECTOR_GRID_ITEM_CLASS));

		// Verify price is displayed for each item in the grid
		for (WebElement we : gridItems) {
			softAssert.assertFalse(hackathonReporter(2,
					"Price box in the grid item is displayed",
					SelectorConstants.SELECTOR_PRICE_BOX_CLASS,
					we.findElement(By.className(
							SelectorConstants.SELECTOR_PRICE_BOX_CLASS))
							.isDisplayed()));

			// Hover over each item in the grid
			Actions actions = new Actions(driver);
			if (device == "Laptop") {
				actions.moveToElement(we).perform();
				Thread.sleep(3000);
			}

			// Verify add to favourite icon is displayed on hovering in each
			// grid item
			softAssert.assertTrue(hackathonReporter(2,
					"Add to Favorites in the grid item is displayed",
					SelectorConstants.SELECTOR_ADD_TO_FAVORITES_CLASS,
					we.findElement(By.className(
							SelectorConstants.SELECTOR_ADD_TO_FAVORITES_CLASS))
							.isDisplayed()));

			// Verify add to compare icon is displayed on hovering in each
			// grid item
			softAssert.assertTrue(hackathonReporter(2,
					"Add to compare in the grid item is displayed",
					SelectorConstants.SELECTOR_ADD_TO_COMPARE_CLASS,
					we.findElement(By.className(
							SelectorConstants.SELECTOR_ADD_TO_COMPARE_CLASS))
							.isDisplayed()));

			// Verify add to cart icon is displayed on hovering in each
			// grid item
			softAssert.assertTrue(hackathonReporter(2,
					"Add to cart in the grid item is displayed",
					SelectorConstants.SELECTOR_ADD_TO_CART_CLASS,
					we.findElement(By.className(
							SelectorConstants.SELECTOR_ADD_TO_CART_CLASS))
							.isDisplayed()));
		}
	}

}
