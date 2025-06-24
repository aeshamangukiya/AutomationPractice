package constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.testng.Assert;

public class ConstVariables {

	static Properties properties = new Properties();
	public static final String BASE_FOLDER = System.getProperty("user.dir");

	public static final String TIMESTAMP = new SimpleDateFormat("dd_MM_yyy_HH_mm_ss").format(new Date());

	public static final String ConfigFilePath = ("./src\\test\\resources\\config\\config.properties");

	public static final String logFilePath = (".\\logs\\");
	public static final String logFileName = Paths.get(TIMESTAMP + ".log").toString();

	public static final String ScreenshotPath = (".\\screenshots\\");
	public static final String ScreenshotName = TIMESTAMP + ".png";

	public static final String ReportPath = (BASE_FOLDER + "/report/");
	public static final String ReportName = TIMESTAMP + ".html";
	
	public static final String ExcelSheetPath = (BASE_FOLDER + "\\testdata\\accounts\\login\\LoginTestData.xlsx");
	
	public static final String pageCurrentURLExp = "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";
	
	public static void failTestCase(String message) {
		Assert.fail("Test failed due to an exception: " + message);
	}

	public static String getConfigPropertiesValue(String key) {
		try {
			// Load the properties file
			FileInputStream fis = new FileInputStream(ConfigFilePath);
			properties.load(fis);
			// Get the values from the properties file
			String value = properties.getProperty(key);
			return value;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";

		}
	}
	
	  public ConstVariables() {
	        try (FileInputStream input = new FileInputStream(ConfigFilePath)) {
	            properties = new Properties();
	            properties.load(input);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public Properties getProperties() {
	        return properties;
	    }


}
