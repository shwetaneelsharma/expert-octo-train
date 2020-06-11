package hackathonapplitools.grid.tests.traditionalv1;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import hackathonapplitools.grid.common.BaseTest;
import hackathonapplitools.grid.common.SelectorConstants;

public class CrossDeviceElementsTest extends BaseTest {

	// Define browsers and viewports
	String[][] viewPorts = new String[][] {
			{ BROWSER_CHROME, "1200X700", "Laptop" },
			{ BROWSER_FIREFOX, "1200X700", "Laptop" },
			{ BROWSER_SAFARI, "1200X700", "Laptop" },
			{ BROWSER_CHROME, "768X700", "Tablet" },
			{ BROWSER_FIREFOX, "768X700", "Tablet" },
			{ BROWSER_SAFARI, "768X700", "Tablet" },
			{ BROWSER_CHROME, "500X700", "Phone" },
			{ BROWSER_FIREFOX, "500X700", "Phone" },
			{ BROWSER_SAFARI, "500X700", "Phone" } };

	int viewIndex = 0;

	@BeforeClass
	public void init() {
		resultFile = "Traditional-V1-TestResults.txt";
	}

	@BeforeMethod
	public void registerDriver() {
		browser = viewPorts[viewIndex][0];
		viewport = viewPorts[viewIndex][1];
		device = viewPorts[viewIndex][2];
		viewIndex++;

		super.registerDriver();

		// Launch the Applitools hackathon Website
		driver.get("https://demo.applitools.com/gridHackathonV1.html");
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		driver.manage().window()
				.setSize(new Dimension(Integer.parseInt(viewport.split("X")[0]),
						Integer.parseInt(viewport.split("X")[1])));
	}

	@Test(priority = 1, invocationCount = 3)
	// Verify the elements' visibility on Laptop
	public void elementVisibilityOnLaptop() {

		verifyById(1,
				new String[] { "Search field is displayed",
						"Search icon is displayed", "Main menu is displayed",
						"Logo is displayed", "Access link is displayed",
						"Wishlist is displayed", "Cart is displayed",
						"Filter is displayed", "Viewgrid option is displayed",
						"Viewlist option is displayed", "Footer is displayed" },
				new String[] { SelectorConstants.SELECTOR_SEARCH_FIELD_ID,
						SelectorConstants.SELECTOR_SEARCH_ICON_ID,
						SelectorConstants.SELECTOR_MAIN_MENU_ID,
						SelectorConstants.SELECTOR_LOGO_IMAGE_ID,
						SelectorConstants.SELECTOR_ACCESS_LINK_ID,
						SelectorConstants.SELECTOR_WISHLIST_ID,
						SelectorConstants.SELECTOR_CART_ID,
						SelectorConstants.SELECTOR_FILTER_ID,
						SelectorConstants.SELECTOR_VIEWGRID_ID,
						SelectorConstants.SELECTOR_VIEWLIST_ID,
						SelectorConstants.SELECTOR_FOOTER_ID },
				new boolean[] { true, true, true, true, true, true, true, true,
						true, true, true });
	}

	@Test(priority = 2, invocationCount = 3)
	// Verify the elements' visibility on Tablet
	public void elementVisibilityOnTablet() {
		verifyById(1,
				new String[] { "Search field is displayed",
						"Search icon is displayed", "Main menu is displayed",
						"Logo is displayed", "Access link is displayed",
						"Wishlist is displayed", "Cart is displayed",
						"Filter is displayed", "Viewgrid option is displayed",
						"Viewlist option is displayed", "Footer is displayed" },
				new String[] { SelectorConstants.SELECTOR_SEARCH_FIELD_ID,
						SelectorConstants.SELECTOR_SEARCH_ICON_ID,
						SelectorConstants.SELECTOR_MAIN_MENU_ID,
						SelectorConstants.SELECTOR_LOGO_IMAGE_ID,
						SelectorConstants.SELECTOR_ACCESS_LINK_ID,
						SelectorConstants.SELECTOR_WISHLIST_ID,
						SelectorConstants.SELECTOR_CART_ID,
						SelectorConstants.SELECTOR_FILTER_ID,
						SelectorConstants.SELECTOR_VIEWGRID_ID,
						SelectorConstants.SELECTOR_VIEWLIST_ID,
						SelectorConstants.SELECTOR_FOOTER_ID },
				new boolean[] { true, true, false, true, true, false, true,
						false, true, false, false });

	}

	@Test(priority = 3, invocationCount = 3)
	// Verify the elements' visibility on Phone
	public void elementVisibilityOnPhone() {

		verifyById(1,
				new String[] { "Search field is displayed",
						"Search icon is displayed", "Main menu is displayed",
						"Logo is displayed", "Access link is displayed",
						"Wishlist is displayed", "Cart is displayed",
						"Filter is displayed", "Viewgrid option is displayed",
						"Viewlist option is displayed", "Footer is displayed" },
				new String[] { SelectorConstants.SELECTOR_SEARCH_FIELD_ID,
						SelectorConstants.SELECTOR_SEARCH_ICON_ID,
						SelectorConstants.SELECTOR_MAIN_MENU_ID,
						SelectorConstants.SELECTOR_LOGO_IMAGE_ID,
						SelectorConstants.SELECTOR_ACCESS_LINK_ID,
						SelectorConstants.SELECTOR_WISHLIST_ID,
						SelectorConstants.SELECTOR_CART_ID,
						SelectorConstants.SELECTOR_FILTER_ID,
						SelectorConstants.SELECTOR_VIEWGRID_ID,
						SelectorConstants.SELECTOR_VIEWLIST_ID,
						SelectorConstants.SELECTOR_FOOTER_ID },
				new boolean[] { false, false, false, true, true, false, true,
						false, false, false, true });

	}

}