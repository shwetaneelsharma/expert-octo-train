package hackathonapplitools.grid.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Utils {
	private WebDriver driver;

	public Utils(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getElementID(String id) {
		return driver.findElement(By.id(id));
	}

	public WebElement getElementClass(String className) {
		return driver.findElement(By.className(className));
	}

	public WebElement getElementXpath(String xpath) {
		return driver.findElement(By.xpath(xpath));
	}

	public WebElement getElementLink(String link) {
		return driver.findElement(By.linkText(link));
	}
}
