package Tests;
import java.awt.RenderingHints.Key;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import com.google.common.base.Functions;

import PageObjects.PageObject;
import Utils.BaseClass;


public class Tests extends BaseClass {
	WebDriver driver ;
	PageObject pageObject;
	
	@BeforeClass
	public void setup() {		
			
	}
	 
	@Test
	public void loginTest()
	{
		driver = launchBrowser();
		pageObject = new PageObject(driver);
		
		logger.info("Browser Launched And Tying to Login");
		pageObject.logintoApplication();
		
	}
	
}