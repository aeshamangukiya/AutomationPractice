package dbtests;

import org.testng.Assert;
import java.util.*;

/**
 * Assertion helper class for validating database query results.
 * Works with results returned by DatabaseUtils.executeQuery().
 *
 * All comparisons are normalized (via String) to avoid DB-type mismatches
 * (e.g., Boolean true vs. String "true" vs. Integer 1).
 */
public final class DatabaseAssertions {

    private DatabaseAssertions() {}

    public static void assertNotEmpty(List<Map<String, Object>> results, String message) {
        Assert.assertFalse(results.isEmpty(), message);
    }

    public static void assertRowCount(List<Map<String, Object>> results, int expectedCount, String message) {
        Assert.assertEquals(results.size(), expectedCount, message);
    }

    public static void assertColumnValue(List<Map<String, Object>> results,
                                         String column,
                                         Object expectedValue,
                                         String message) {
        Assert.assertFalse(results.isEmpty(), "No rows returned to assert column value");
        Object actualValue = results.get(0).get(column);

        String actualStr = normalizeValue(actualValue);
        String expectedStr = normalizeValue(expectedValue);

        Assert.assertEquals(actualStr, expectedStr, message + " | Column: " + column);
    }

    public static void assertRowEquals(List<Map<String, Object>> results,
                                       Map<String, Object> expectedRow,
                                       String message) {
        Assert.assertFalse(results.isEmpty(), "No rows returned to assert row values");
        Map<String, Object> actualRow = results.get(0);

        for (Map.Entry<String, Object> entry : expectedRow.entrySet()) {
            String actualStr = normalizeValue(actualRow.get(entry.getKey()));
            String expectedStr = normalizeValue(entry.getValue());

            Assert.assertEquals(actualStr, expectedStr,
                    message + " | Column: " + entry.getKey());
        }
    }

    public static void assertColumnValues(List<Map<String, Object>> results,
                                          String column,
                                          List<?> expectedValues,
                                          String message) {
        List<String> actualValues = new ArrayList<>();
        for (Map<String, Object> row : results) {
            actualValues.add(normalizeValue(row.get(column)));
        }

        List<String> expectedStrValues = new ArrayList<>();
        for (Object v : expectedValues) {
            expectedStrValues.add(normalizeValue(v));
        }

        Assert.assertEqualsNoOrder(actualValues.toArray(), expectedStrValues.toArray(), message);
    }
    
    /**
     * Assert that the total user count is greater than the given minimum.
     *
     * This method directly validates COUNT(*) results from QueryBuilder.countUsers().
     *
     * @param countObj  value returned from DatabaseUtils.getSingleValue(query)
     * @param min       minimum allowed count (exclusive)
     * @param message   custom assertion message
     */
    public static void assertUserCountGreaterThan(Object countObj, int min, String message) {
        if (!(countObj instanceof Number)) {
            Assert.fail("Expected numeric result for user count but got: " + countObj);
        }
        int actualCount = ((Number) countObj).intValue();
        Assert.assertTrue(actualCount > min,
                message + " | Expected > " + min + " but found " + actualCount);
    }

    // 🔧 Normalization helper
    private static String normalizeValue(Object value) {
        if (value == null) return "NULL";
        if (value instanceof Boolean) return ((Boolean) value) ? "true" : "false";
        if (value instanceof Number) return String.valueOf(((Number) value).longValue());
        return value.toString().trim();
    }
    
    
}
