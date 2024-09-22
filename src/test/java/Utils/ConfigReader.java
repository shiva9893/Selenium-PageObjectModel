package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
	public static Properties configProperties;

	public static Properties readConfigFile() {
		try {
			File file = new File(System.getProperty("user.dir") + "\\config.properties");
			FileInputStream fi = new FileInputStream(file);
			configProperties = new Properties();
			configProperties.load(fi);

		} catch (Exception e) {
			System.out.println(e);
		}
		return configProperties;
	}

	public static String getProperty(String key) {
		configProperties = readConfigFile();
		return configProperties.getProperty(key);
	}
}
