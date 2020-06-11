package hackathonapplitools.grid.tests.modern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultContainer;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;

import hackathonapplitools.grid.common.SelectorConstants;
import hackathonapplitools.grid.common.Utils;

public class ModernTestsV1 {

	private final int viewPortWidth = 800;
	private final int viewPortHeight = 600;
	String myEyesServer = "https://eyes.applitools.com/"; // set to your
															// server/cloud URL
	String appName = "Applifashion app";
	String batchName = "UFG Hackathon";
	private String apiKey = System.getenv("APPLITOOLS_API_KEY");
	private EyesRunner runner = null;
	private Configuration suiteConfig;
	private Eyes eyes;
	private WebDriver webDriver;
	String url = "https://demo.applitools.com/gridHackathonV1.html";
	private Utils util;

	@BeforeSuite
	public void beforeTestSuite() {
		System.setProperty("webdriver.chrome.driver",
				"/path/to/your/chromedriver");
		runner = new VisualGridRunner(10);
		// Create a configuration object, we will use this when setting up each
		// test
		suiteConfig = new Configuration();
		// Visual Grid configurations
		suiteConfig.addBrowser(1200, 700, BrowserType.CHROME)
				.addBrowser(1200, 700, BrowserType.FIREFOX)
				.addBrowser(1200, 700, BrowserType.EDGE_CHROMIUM)
				.addBrowser(1200, 700, BrowserType.CHROME)
				.addBrowser(768, 700, BrowserType.FIREFOX)
				.addBrowser(768, 700, BrowserType.EDGE_CHROMIUM)
				.addBrowser(768, 600, BrowserType.EDGE_CHROMIUM)
				.addDeviceEmulation(DeviceName.iPhone_X,
						ScreenOrientation.PORTRAIT)
				// Checkpoint configurations
				// Test specific configurations ....
				.setViewportSize(
						new RectangleSize(viewPortWidth, viewPortHeight))
				// Test suite configurations
				.setApiKey(apiKey).setServerUrl(myEyesServer)
				.setAppName(appName).setBatch(new BatchInfo(batchName)
				/* ...other configurations */ );
	}

	@BeforeMethod
	public void beforeEachTest(ITestResult result) {
		// Create the Eyes instance for the test and associate it with the
		// runner
		eyes = new Eyes(runner);
		eyes.setConfiguration(suiteConfig);
		webDriver = new ChromeDriver();
		util = new Utils(webDriver);
	}

	@Test
	public void crossDeviceElementsTest() {
		// Update the Eyes configuration with test specific values
		Configuration testConfig = eyes.getConfiguration();
		testConfig.setTestName("Task 1");
		eyes.setConfiguration(testConfig);

		// Open Eyes, the application,test name and viewport size are allready
		// configured
		WebDriver driver = eyes.open(webDriver);

		// Now run the test

		// Visual checkpoint #1.
		driver.get(url); // navigate
							// to
							// website
		eyes.checkWindow("Cross-Device Elements Test");
	}

	@Test
	public void shoppingExperienceTest() throws Throwable {
		// Update the Eyes configuration with test specific values
		Configuration testConfig = eyes.getConfiguration();
		testConfig.setTestName("Task 2");
		eyes.setConfiguration(testConfig);

		// Open Eyes, the application,test name and viewport size are already
		// configured
		WebDriver driver = eyes.open(webDriver);

		// Visual checkpoint #1.
		driver.get(url); // navigate
							// to
							// website
		// Apply filter by black colour

		util.getElementID(SelectorConstants.MOBILE_FILTER_ID).click();
		Thread.sleep(2000);
		driver.findElement(
				By.xpath("//div[@id='sidebar_filters']/div[2]/div/ul/li"))
				.click();
		util.getElementID(SelectorConstants.SELECTOR_FILTER_BUTTON_ID).click();
		Thread.sleep(2000);
		eyes.checkRegion(By.id("product_grid"), "Filter Results");
	}

	@Test
	public void productDetailsTest() throws Throwable {
		// Update the Eyes configuration with test specific values
		Configuration testConfig = eyes.getConfiguration();
		testConfig.setTestName("Task 3");
		eyes.setConfiguration(testConfig);

		// Open Eyes, the application,test name and viewport size are already
		// configured
		WebDriver driver = eyes.open(webDriver);

		// Now run the test

		driver.get(url); // navigate

		util.getElementID(SelectorConstants.MOBILE_FILTER_ID).click();
		Thread.sleep(2000);
		driver.findElement(
				By.xpath("//div[@id='sidebar_filters']/div[2]/div/ul/li"))
				.click();
		util.getElementID(SelectorConstants.SELECTOR_FILTER_BUTTON_ID).click();
		Thread.sleep(2000);
		util.getElementID(SelectorConstants.FIRST_SHOE_ID).click();

		// Visual checkpoint
		eyes.checkWindow("Product Details Test");

	}

	@AfterMethod
	public void afterEachTest(ITestResult result) {
		// check if an exception was thrown
		boolean testFailed = result.getStatus() == ITestResult.FAILURE;
		if (!testFailed) {
			// Close the Eyes instance, no need to wait for results, we'll get
			// those at the end in afterTestSuite
			eyes.closeAsync();
		} else {
			// There was an exception so the test may be incomplete - abort the
			// test
			eyes.abortAsync();
		}
		webDriver.quit();
	}

	@AfterSuite
	public void afterTestSuite(ITestContext testContext) {
		// Wait until the test results are available and retrieve them
		TestResultsSummary allTestResults = runner.getAllTestResults(false);
		for (TestResultContainer result : allTestResults) {
			handleTestResults(result);
		}
	}

	void handleTestResults(TestResultContainer summary) {
		Throwable ex = summary.getException();
		if (ex != null) {
			System.out.printf("System error occured while checking target.\n");
		}
		TestResults result = summary.getTestResults();
		if (result == null) {
			System.out.printf("No test results information available\n");
		} else {
			System.out.printf(
					"URL = %s, AppName = %s, testname = %s, Browser = %s,OS = %s, viewport = %dx%d, matched = %d,mismatched = %d, missing = %d,aborted = %s\n",
					result.getUrl(), result.getAppName(), result.getName(),
					result.getHostApp(), result.getHostOS(),
					result.getHostDisplaySize().getWidth(),
					result.getHostDisplaySize().getHeight(),
					result.getMatches(), result.getMismatches(),
					result.getMissing(),
					(result.isAborted() ? "aborted" : "no"));
		}
	}

}
