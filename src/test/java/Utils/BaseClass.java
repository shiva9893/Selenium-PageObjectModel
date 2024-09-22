package Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.apache.commons.io.FileUtils;

import io.opentelemetry.sdk.autoconfigure.spi.ConfigProperties;

public class BaseClass {

	public static WebDriver driver;
	public static Logger logger;
	
	
	
	@BeforeSuite
	public void configWebDriver()
	{
		//Logs
				logger = LogManager.getLogger(this.getClass());	
		//updateChromeVersion();
	}
	@AfterSuite
	public void closeWebDriver()
	{
		driver.quit();
	}
	
	public static void updateChromeVersion()
	{
		 try {
	            String chromeVersion = getChromeVersion();
	            if (chromeVersion != null) {
	                String majorVersion = chromeVersion.split("\\.")[0]; // Get the major version
	                System.out.println("Detected Chrome version: " + chromeVersion);

	                // Get the latest compatible ChromeDriver version for your Chrome browser version
	                String driverVersion = getChromeDriverVersion(majorVersion);
	                if (driverVersion != null) {
	                    System.out.println("Downloading ChromeDriver version: " + driverVersion);
	                    downloadAndReplaceChromeDriver(driverVersion);
	                } else {
	                    System.out.println("Unable to find a compatible ChromeDriver version.");
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	 // Method to get current Chrome version from the system
    private static String getChromeVersion() throws IOException {
        String chromeVersion = null;
        ProcessBuilder processBuilder = new ProcessBuilder();
        
        // Command to get Chrome version
        // For Windows:
        processBuilder.command("cmd.exe", "/c", "reg query \"HKEY_CURRENT_USER\\Software\\Google\\Chrome\\BLBeacon\" /v version");
        
        // For Mac/Linux: Uncomment the appropriate one
        // processBuilder.command("google-chrome", "--version");
        // processBuilder.command("/Applications/Google\\ Chrome.app/Contents/MacOS/Google\\ Chrome", "--version");

        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("version")) {
                chromeVersion = line.split("    ")[3]; // Adjust depending on output format
            }
        }
        return chromeVersion;
    }
    
    // Fetch compatible ChromeDriver version using the ChromeDriver API
    private static String getChromeDriverVersion(String chromeMajorVersion) throws IOException {
        String url = "https://chromedriver.storage.googleapis.com/LATEST_RELEASE_" + chromeMajorVersion;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        return reader.readLine();
    }
    // Download the latest ChromeDriver version and replace the current one
    private static void downloadAndReplaceChromeDriver(String driverVersion) throws IOException {
        String downloadUrl = "https://chromedriver.storage.googleapis.com/" + driverVersion + "/chromedriver_win32.zip";
        
        // Download the zip file
        File tempZip = new File("chromedriver.zip");
        FileUtils.copyURLToFile(new URL(downloadUrl), tempZip);
        
        // Unzip the downloaded file and replace the existing chromedriver
        File destDir = new File("C:\\Users\\Kumar\\eclipse-workspace\\SeleniumTutorial\\ChromeDriver\\chromedriver.exe");
        FileUtils.deleteDirectory(destDir); // Remove old chromedriver
        unzip(tempZip, destDir); // Unzip the new chromedriver

        // Clean up
        Files.delete(tempZip.toPath());
        System.out.println("ChromeDriver updated successfully.");
    }

    // Unzip downloaded chromedriver file
    private static void unzip(File zipFile, File destDir) throws IOException {
        java.util.zip.ZipFile zip = new java.util.zip.ZipFile(zipFile);
        zip.stream().forEach(entry -> {
            try {
                File file = new File(destDir, entry.getName());
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    Files.copy(zip.getInputStream(entry), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        zip.close();
    }


	public static WebDriver launchBrowser() {
		try {			
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("useAutomationExtension", false);
			options.addArguments("--ignore-certificate-errors");
			options.addArguments("--allow-insecure-localhost");
			options.addArguments("--disable-infobars");
			String path = System.getProperty("user.dir") + "\\ChromeDriver\\chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", path);
			driver = new ChromeDriver();
			driver.get(ConfigReader.getProperty("applicationURL"));
			driver.manage().window().maximize();
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println(e);
		}
		return driver;
	}

	

}
