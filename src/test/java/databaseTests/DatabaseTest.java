package databaseTests;

import org.testng.annotations.Test;

import utilitiesPage.DatabaseUtils;

public class DatabaseTest {
	
	@Test
	public void dbTest() {
		DatabaseUtils.printEmployees();
	}

}
