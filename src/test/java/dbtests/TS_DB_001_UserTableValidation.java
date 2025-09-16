package dbtests;

import db.DatabaseUtils;

import db.QueryBuilder;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TS_DB_001 - Database validation tests for Users table
 */
public class TS_DB_001_UserTableValidation {

    @Test(priority = 1)
    public void TC_DB_001_VerifyUserExists() {
        String query = QueryBuilder.getUserById(1);
        List<Map<String, Object>> result = DatabaseUtils.executeQuery(query);

        DatabaseUtils.printResult(result, DatabaseUtils.OutputFormat.TABLE);

        DatabaseAssertions.assertNotEmpty(result, "User should exist in DB");
        DatabaseAssertions.assertRowCount(result, 1, "Expected exactly one user");
        DatabaseAssertions.assertColumnValue(result, "idemployee",
                "1", "idemployee does not match");

        Map<String, Object> expectedRow = new HashMap<>();
        expectedRow.put("idemployee", "1");
        expectedRow.put("Name", "TestUserName");
        expectedRow.put("Age", "25"); // normalized check
        DatabaseAssertions.assertRowEquals(result, expectedRow,
                "User details do not match expected values");
    }

    @Test(priority = 2)
    public void TC_DB_002_VerifyUserCount() {
        String query = QueryBuilder.countUsers();
        Object count = DatabaseUtils.getSingleValue(query);

        DatabaseAssertions.assertUserCountGreaterThan(count, 0,
                "User count should be greater than 0");
    }

}
