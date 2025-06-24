package utilitiesTest;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import constants.ConstVariables;
import helper.FileHelper;
import testBase.BaseTest;

public class ScreenshotUtility extends BaseTest {

	public static String captureScreen(String foldername, String methodName) throws IOException {
		// TODO Auto-generated method stub
		String targetFolderPath = ConstVariables.BASE_FOLDER + ConstVariables.ScreenshotPath + foldername + "\\";
		FileHelper.createFolderNotExists(targetFolderPath);

		// Capture Screenshot
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

		// the screenshot will be stored.
		String targetFilePath = targetFolderPath + methodName + "_" + ConstVariables.ScreenshotName;
		File targetFile = new File(targetFilePath);

		// Move screenshot from sourceFile to targetFilePath.
		sourceFile.renameTo(targetFile);
		return targetFilePath;
	}

}