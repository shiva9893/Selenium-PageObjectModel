package Tests;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.reporters.jq.NavigatorPanel;

import dev.failsafe.internal.util.Durations;

public class Test {	


	public static void main(String[] args) throws InterruptedException {

		try {
			ChromeOptions co = new ChromeOptions();			
			FirefoxOptions fo = new FirefoxOptions();
			
			
			DesiredCapabilities ds = new DesiredCapabilities(co);
			ds.acceptInsecureCerts();
			
			
			
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\Kumar\\eclipse-workspace\\ChromeDriver\\chromedriver.exe");
			WebDriver driver = new ChromeDriver();
			
			RemoteWebDriver rw = new RemoteWebDriver(null);
			

			driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
			driver.get("https:www.google.com");
			driver.navigate().to("https://amazon.com");
			Navigation nv = driver.navigate();
			Thread.sleep(5000);
			
		

			// Implicit Wait
			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

			// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

			// Explicit wait
			// WebDriverWait explicitwait = new WebDriverWait(driver,
			// Duration.ofSeconds(5));
			driver.manage().window().maximize();
			
			
			
			driver.switchTo().frame(0);//frame 1
			driver.switchTo().frame(1);//childframe
			driver.switchTo().frame(2);//grandchild
			driver.switchTo().frame(3);		
			
			driver.switchTo().parentFrame();
			driver.switchTo().defaultContent();

			WebElement userName = driver.findElement(By.name("username"));
			userName.sendKeys("Admin");

			// Actions Class which is used to automate Keyboard and Mouse actions/operations
			Actions action = new Actions(driver);	
			
			

			WebElement passWord = driver.findElement(By.name("password"));
			passWord.sendKeys("admin123");

			WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
			// explicitwait.until(ExpectedConditions.elementToBeClickable(loginButton));
			loginButton.click();
			Thread.sleep(5000);
			List<WebElement> menuOptions = driver.findElements(By.xpath(
					"//ul[@class='oxd-main-menu']//a//child::span[@class='oxd-text oxd-text--span oxd-main-menu-item--name']"));
			for (int i = 0; i < menuOptions.size(); i++) {
				String linkName = menuOptions.get(i).getText();
				
				if (linkName.equalsIgnoreCase("Leave")) {
					menuOptions.get(i).click();
				}
				System.out.println(linkName);
			}

			/*
			 * WebElement sidepanel =
			 * driver.findElement(By.xpath("//div[@class='oxd-sidepanel-body']"));
			 * 
			 * List<WebElement> menuOptions1 = sidepanel.findElements(By.tagName("span"));
			 */

			driver.findElement(By.xpath("//span[contains(text(),'Entitlements')]")).click();
			Thread.sleep(3000);
			WebElement dropDown = driver.findElement(By.className("oxd-dropdown-menu"));
			
			
			
			Select select = new Select(dropDown);

			Point location = dropDown.getLocation();
			int x = location.getX();
			int y = location.getY();
			
			///Window Handles
			
			String parentWindow = driver.getWindowHandle();
			
			Set<String> allWindows = driver.getWindowHandles();
			
			Object[] arrayofwindows = allWindows.toArray();
			
			 driver.switchTo().window(arrayofwindows[3].toString());
			 driver.switchTo().window(arrayofwindows[0].toString());
			
			for(String value :allWindows)
			{
				
				driver.switchTo().window(value);
				
			}
			
			
			  for(int i=0;i<arrayofwindows.length;i++) 
				  
			  {
				  driver.switchTo().window(arrayofwindows[i].toString());
			  }
			  driver.switchTo().window(parentWindow);

			System.out.println("Closing the Driver");
			driver.close();
		} catch (Exception e) {
			System.out.println("The Exception is >>>" + e);
		}

	}
}
