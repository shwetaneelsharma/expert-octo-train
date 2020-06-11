package hackathonapplitools.grid.tests.traditionalv2;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import hackathonapplitools.grid.common.BaseTest;
import hackathonapplitools.grid.common.SelectorConstants;

public class ProductDetailsTest extends BaseTest {

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

	@Test(invocationCount = 6)
	public void verifyProductDetailsPage() throws Throwable {
		// If block to apply filter by colour black
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
		// Click the first shoe after applying filter
		util.getElementID(SelectorConstants.FIRST_SHOE_ID).click();
		softAssert.assertTrue(hackathonReporter(3, "Product info is displayed",
				SelectorConstants.PROD_INFO_VERSION_ID,
				util.getElementID(SelectorConstants.PROD_INFO_VERSION_ID)
						.isDisplayed()));

		// Verify the details displayed on PDP
		verifyById(3, new String[] {
				"Header is displayed on Product details page",
				"Shoe name is displayed on Product details page",
				"Product info version is displayed on Product details page",
				"Product rating is displayed on Product details page",
				"Prod options are displayed on Product details page", },
				new String[] { SelectorConstants.SELECTOR_PAGE_HEADER_ID,
						SelectorConstants.SHOE_NAME_ID,
						SelectorConstants.PROD_INFO_VERSION_ID,
						SelectorConstants.PROD_INFO_RATING_ID,
						SelectorConstants.PROD_OPTIONS_ID, },
				new boolean[] { true, true, true, true, true });

		// Verify the header and footer on various devices and viewports on PDP
		if (device != "Phone") {

			verifyByLink(3, new String[] {
					"About us link is displayed in the footer on product details page",
					"Faq link is displayed in the footer on product details page",
					"Faq link is displayed in the footer on product details page",
					"My account link is displayed in the footer on product details page",
					"Blog link is displayed in the footer on product details page",
					"Contacts link is displayed in the footer on product details page",
					"Email link is displayed in the footer on product details page",
					"Terms and Conditions link is displayed in the footer on product details page",
					"Privacy link is displayed in the footer on product details page" },
					new String[] { SelectorConstants.ABOUT_US_LINK,
							SelectorConstants.FAQ_LINK,
							SelectorConstants.HELP_LINK,
							SelectorConstants.MY_ACCOUNT_LINK,
							SelectorConstants.BLOG_LINK,
							SelectorConstants.CONTACTS_LINK,
							SelectorConstants.EMAIL_LINK,
							SelectorConstants.TANDC_LINK,
							SelectorConstants.PRIVACY_LINK },
					new boolean[] { true, true, true, true, true, true, true,
							true, true });
		} else {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			driver.findElement(By.xpath("//footer/div/div/div/h3")).click();
			verifyByLink(3, new String[] {
					"About us link is displayed in the footer on product details page",
					"Faq link is displayed in the footer on product details page",
					"Faq link is displayed in the footer on product details page",
					"My account link is displayed in the footer on product details page",
					"Blog link is displayed in the footer on product details page", },
					new String[] { SelectorConstants.ABOUT_US_LINK,
							SelectorConstants.FAQ_LINK,
							SelectorConstants.HELP_LINK,
							SelectorConstants.MY_ACCOUNT_LINK,
							SelectorConstants.BLOG_LINK, },
					new boolean[] { true, true, true, true, true });
			driver.findElement(By.xpath("//footer/div/div/div[2]/h3")).click();
			verifyByLink(3, new String[] {
					"Contacts link is displayed in the footer on product details page",
					"Email link is displayed in the footer on product details page", },
					new String[] { SelectorConstants.CONTACTS_LINK,
							SelectorConstants.EMAIL_LINK, },
					new boolean[] { true, true });
			driver.findElement(By.xpath("//footer/div/div/div[3]/h3")).click();
			Thread.sleep(3000);
			verifyById(3, new String[] {
					"Subscription box is displayed in the footer on Product details page" },
					new String[] { SelectorConstants.SELECTOR_NEWSLETTER_ID },
					new boolean[] { true });

		}
		verifyByClass(3,
				new String[] { "Language selector is displayed in the footer",
						"Currency selector is displayed in the footer", },
				new String[] { SelectorConstants.LANGUAGE_SELECTOR_CLASS,
						SelectorConstants.CURRENCY_SELECTOR_CLASS, },
				new boolean[] { true, true });

	}

}
