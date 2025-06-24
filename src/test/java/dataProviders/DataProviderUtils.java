package dataProviders;

import java.io.IOException;

import org.testng.annotations.DataProvider;

import constants.ConstVariables;
import utilitiesTest.ExcelReader;
import utilitiesTest.LoggerUtility;

public class DataProviderUtils {

	//LoginDataExcel
	@DataProvider(name = "LoginDataExcel")
	public String[][] getData() throws IOException {
		LoggerUtility.info("Call DataProvider: *getData* Method");
		// D:\GIT\AutopilotQA\autopilotqaweb\testdata\accounts\login\LoginTestData.xlsx
		String excelFilePath = ConstVariables.ExcelSheetPath;

		ExcelReader xlutil = new ExcelReader(excelFilePath);// creating an object for XLUtility

		int totalrows = xlutil.getRowCount("Sheet1");
		int totalcols = xlutil.getCellCount("Sheet1", 1);

		String logindata[][] = new String[totalrows][totalcols];// created for two dimension array which can store the
																// data user and password

		for (int i = 1; i <= totalrows; i++) // 1 //read the data from xl storing in two deminsional array
		{
			for (int j = 0; j < totalcols; j++) // 0 i is rows j is col
			{
				logindata[i - 1][j] = xlutil.getCellData("Sheet1", i, j); // 1,0
			}
		}
		return logindata;// returning two dimension array

	}

	//LoginDataStatic
	@DataProvider(name = "LoginDataStatic")
	public static Object[][] loginTestData() {
		String validEmailUsername = ConstVariables.getConfigPropertiesValue("qaUsername");
		String validPassword = ConstVariables.getConfigPropertiesValue("qaPassword");

		return new Object[][] { 
			{ validEmailUsername, validPassword, "Success" }, // Valid 
		};
	}

	// RegistrationData

	// DataProvider 3

	// DataProvider 4

}
