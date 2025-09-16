package constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.testng.Assert;

public class FrameworkConstants {

	public static final String BASE_FOLDER = System.getProperty("user.dir");

	public static final String TIMESTAMP = new SimpleDateFormat("dd_MM_yyy_HH_mm_ss").format(new Date());

	public static final String CONFIG_BASE_PATH = "./src/test/resources/config/";

	public static final String LOG_FILE_PATH = (".\\logs\\");
	public static final String LOG_FILE_NAME = Paths.get(TIMESTAMP + ".log").toString();

	public static final String SCREENSHOT_PATH = (".\\screenshots\\");
	public static final String SCREENSHOT_NAME = TIMESTAMP + ".png";

	public static final String REPORT_PATH = (BASE_FOLDER + "/report/");
	public static final String REPORT_NAME = TIMESTAMP + ".html";

	public static final String EXCEL_SHEET_PATH = (BASE_FOLDER + "\\testdata\\accounts\\login\\LoginTestData.xlsx");

	public static void failTestCase(String message) {
		Assert.fail("Test failed due to an exception: " + message);
	}
	
    
 // Database Config
	public static final String DB_URL = "jdbc:mysql://localhost:3306/emp";
	public static final String DB_USERNAME = "root";
	public static final String DB_PASSWORD = "SQL$am5.5";

	private FrameworkConstants() {
		// Prevent instantiation
	}
}
