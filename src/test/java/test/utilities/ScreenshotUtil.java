package test.utilities;

import java.io.File; 
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import constants.FrameworkConstants;
import helper.FileHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    /**
     * Captures a screenshot and saves it to the defined path.
     *
     * @param driver      WebDriver instance
     * @param folderName  Folder inside the screenshot base path
     * @param methodName  Name of the method/test case
     * @return Path of the saved screenshot
     * @throws IOException if file operation fails
     */
    public static String captureScreen(WebDriver driver, String folderName, String methodName) throws IOException {
        // Build the target folder path
        String targetFolderPath = FrameworkConstants.BASE_FOLDER + FrameworkConstants.SCREENSHOT_PATH
                + folderName + File.separator;
        FileHelper.createFolderNotExists(targetFolderPath);

        // Generate timestamp
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // Define screenshot name
        String screenshotName = methodName + "_" + timestamp + "_" + FrameworkConstants.SCREENSHOT_NAME;

        // Take screenshot
        TakesScreenshot ts = (TakesScreenshot) driver;
        File sourceFile = ts.getScreenshotAs(OutputType.FILE);

        File targetFile = new File(targetFolderPath + screenshotName);

        // Copy file to target location
        Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return targetFile.getAbsolutePath();
    }
}
