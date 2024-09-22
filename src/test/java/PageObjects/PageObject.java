package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utils.BaseClass;
import Utils.ConfigReader;

public class PageObject extends BaseClass {
	WebDriver driver;

	public PageObject(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(name = "username")
	WebElement userName;

	@FindBy(name = "password")
	WebElement password;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement submitbutton;

	public void logintoApplication() {
		try {
			logger.info("**** Logging into Application ****");
			userName.sendKeys(ConfigReader.getProperty("username"));
			logger.info("**** Entered UserName ****");
			password.sendKeys(ConfigReader.getProperty("password"));
			logger.info("**** Entered Password ****");
			submitbutton.click();
			logger.info("**** Clicked on Login Button ****");

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
