package data.providers;

import java.io.IOException;

import org.testng.annotations.DataProvider;

import constants.FrameworkConstants;
import configuration.ConfigReader;
import test.utilities.ExcelReader;
import test.utilities.LoggerUtil;

/**
 * Utility class to provide TestNG DataProviders for various test cases.
 * 
 * Naming convention: - Each DataProvider method name clearly indicates the data
 * source and purpose. - DataProvider "name" matches test annotation usage.
 */
public class DataProviderUtil {

	// ===============================
	// Constants
	// ===============================
	private static final String LOGIN_SHEET_NAME = "Sheet1";

	// ===============================
	// Login Data Providers
	// ===============================

	/**
	 * Reads login data from Excel file located at the path specified in
	 * ConstVariables.
	 * 
	 * @return 2D array of login data [username, password]
	 */
	@DataProvider(name = "LoginDataExcel")
	public static String[][] getLoginDataFromExcel() throws IOException {
		LoggerUtil.info("DataProvider: Reading login data from Excel");

		String excelFilePath = FrameworkConstants.EXCEL_SHEET_PATH;
		ExcelReader excelReader = new ExcelReader(excelFilePath);

		int totalRows = excelReader.getRowCount(LOGIN_SHEET_NAME);
		int totalCols = excelReader.getCellCount(LOGIN_SHEET_NAME, 1);

		String[][] loginData = new String[totalRows][totalCols];

		for (int rowIndex = 1; rowIndex <= totalRows; rowIndex++) {
			for (int colIndex = 0; colIndex < totalCols; colIndex++) {
				loginData[rowIndex - 1][colIndex] = excelReader.getCellData(LOGIN_SHEET_NAME, rowIndex, colIndex);
			}
		}
		return loginData;
	}

	/**
	 * Provides static login credentials from config.properties.
	 * 
	 * @return 2D array containing one valid [username, password, status]
	 */
	@DataProvider(name = "LoginDataStatic")
	public static Object[][] getLoginDataFromConfig() {
		LoggerUtil.info("DataProvider: Providing static login data from config.properties");

		// Load config.properties first
		ConfigReader.loadProperties(ConfigReader.ConfigFile.CONFIG);

		String validEmailUsername = ConfigReader.getConfigPropertyValue("qaUsername");
		String validPassword = ConfigReader.getConfigPropertyValue("qaPassword");

		return new Object[][] { { validEmailUsername, validPassword, "Success" } };
	}

	// ===============================
	// Registration Data Providers
	// ===============================
	// TODO: Implement Registration data provider methods when data source is ready.

}
