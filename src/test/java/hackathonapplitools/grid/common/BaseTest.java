package hackathonapplitools.grid.common;

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.asserts.SoftAssert;

public abstract class BaseTest {
	protected static final String BROWSER_CHROME = "Chrome";
	protected static final String BROWSER_FIREFOX = "Firefox";
	protected static final String BROWSER_SAFARI = "Safari";

	protected WebDriver driver;
	protected WebDriverWait wait;
	protected Utils util;

	protected String browser;
	protected String viewport;
	protected String device;

	protected String resultFile;

	protected SoftAssert softAssert = new SoftAssert();

	public void registerDriver() {

		switch (browser) {
			case BROWSER_CHROME:
				System.setProperty("webdriver.chrome.driver",
						"/path/to/your/chromedriver");
				driver = new ChromeDriver();
				break;

			case BROWSER_FIREFOX:
				System.setProperty("webdriver.gecko.driver",
						"/path/to/your/geckodriver");
				driver = new FirefoxDriver();
				break;

			case BROWSER_SAFARI:
				System.setProperty("webdriver.safari.driver",
						"/usr/bin/safaridriver");
				driver = new SafariDriver();
				break;

			default:
				throw new RuntimeException("Unsupported browser: " + browser);
		}
		util = new Utils(driver);
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@AfterClass
	public void afterAllTests() {
		try (BufferedWriter writer = new BufferedWriter(
				new FileWriter(resultFile, true))) {
			writer.write(
					"=================================================================================================================");
			writer.newLine();
		} catch (Exception e) {
			System.out.println("Error writing to report file");
			e.printStackTrace();
		}
	}

	/**
	 * A Helper to print the test result in the following format: Task: <Task
	 * Number>, Test Name: <Test Name>, DOM Id:: <id>, Browser: <Browser>,
	 * Viewport: <Width x Height>, Device<Device type>, Status: <Pass | Fail>
	 * 
	 * Example: Task: 1, Test Name: Search field is displayed, DOM Id:
	 * DIV__customsear__41, Browser: Chrome, Viewport: 1200 x 700, Device:
	 * Laptop, Status: Pass
	 * 
	 * @param task             int - 1, 2 or 3
	 * @param                  testName. string - Something meaningful. E.g. 1.1
	 *                         Search field is displayed
	 * @param domId            string - DOM ID of the element
	 * @param comparisonResult boolean - The result of comparing the "Expected"
	 *                         value and the "Actual" value.
	 * @return boolean - returns the same comparison result back so that it can
	 *         be used for further Assertions in the test code.
	 */
	protected boolean hackathonReporter(int task, String testName, String domId,
			boolean comparisonResult) {
		try (BufferedWriter writer = new BufferedWriter(
				new FileWriter(resultFile, true))) {
			writer.write("Task: " + task + ", Test Name: " + testName
					+ ", DOM Id: " + domId + ", Browser: " + browser
					+ ", Viewport: " + viewport + ", Device: " + device
					+ ", Status: " + (comparisonResult ? "Pass" : "Fail"));
			writer.newLine();
		} catch (Exception e) {
			System.out.println("Error writing to report file");
			e.printStackTrace();
		}
		// returns the result so that it can be used for further Assertions in
		// the test code.
		return comparisonResult;
	}

	public void verifyById(int taskNumber, String[] testNames,
			String[] elementLocators, boolean[] assertTrue) {
		for (int index = 0; index < testNames.length; index++) {
			verifyById(taskNumber, testNames[index], elementLocators[index],
					assertTrue[index]);
		}
	}

	public void verifyById(int taskNumber, String testName,
			String elementLocator, boolean assertTrue) {
		if (assertTrue) {
			softAssert.assertTrue(
					hackathonReporter(taskNumber, testName, elementLocator,
							util.getElementID(elementLocator).isDisplayed()));
		} else {
			softAssert.assertFalse(
					hackathonReporter(taskNumber, testName, elementLocator,
							util.getElementID(elementLocator).isDisplayed()));
		}
	}

	public void verifyByClass(int taskNumber, String[] testNames,
			String[] elementLocators, boolean[] assertTrue) {
		for (int index = 0; index < testNames.length; index++) {
			verifyByClass(taskNumber, testNames[index], elementLocators[index],
					assertTrue[index]);
		}
	}

	public void verifyByClass(int taskNumber, String testName,
			String elementLocator, boolean assertTrue) {
		if (assertTrue) {
			softAssert.assertTrue(hackathonReporter(taskNumber, testName,
					elementLocator,
					util.getElementClass(elementLocator).isDisplayed()));
		} else {
			softAssert.assertFalse(hackathonReporter(taskNumber, testName,
					elementLocator,
					util.getElementClass(elementLocator).isDisplayed()));
		}
	}

	public void verifyByLink(int taskNumber, String[] testNames,
			String[] elementLocators, boolean[] assertTrue) {
		for (int index = 0; index < testNames.length; index++) {
			verifyByLink(taskNumber, testNames[index], elementLocators[index],
					assertTrue[index]);
		}
	}

	public void verifyByLink(int taskNumber, String testName,
			String elementLocator, boolean assertTrue) {
		if (assertTrue) {
			softAssert.assertTrue(
					hackathonReporter(taskNumber, testName, elementLocator,
							util.getElementLink(elementLocator).isDisplayed()));
		} else {
			softAssert.assertFalse(
					hackathonReporter(taskNumber, testName, elementLocator,
							util.getElementLink(elementLocator).isDisplayed()));
		}
	}

}
