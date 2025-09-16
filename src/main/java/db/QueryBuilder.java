package db;

/**
 * QueryBuilder - utility class to generate SQL queries dynamically.
 * 
 * Centralizes all SQL for maintainability and consistency.
 * Uses constants for table/column names to avoid hardcoding.
 */
public final class QueryBuilder {

    private QueryBuilder() {} // prevent instantiation

    // ==========================
    // Table Names
    // ==========================
    public static final String USER_TABLE = "employee";

    // ==========================
    // Column Names
    // ==========================
    public static final String COL_EMP_ID   = "idemployee";
    public static final String COL_EMP_NAME = "Name";
    public static final String COL_EMP_AGE  = "Age";
    public static final String COL_EMP_EMAIL  = "abc@test.com";
    public static final String COL_EMP_PASS  = "123#Abc";
    
    // ==========================
    // Query Generators
    // ==========================

    /** Get user by Email */
    public static String getUserByEmail(String email) {
        return String.format(
                "SELECT * FROM %s WHERE %s = '%s'",
                USER_TABLE, COL_EMP_EMAIL, escape(email));
    }

    /** Get user by ID */
    public static String getUserById(int userId) {
        return String.format(
                "SELECT * FROM %s WHERE %s = %d",
                USER_TABLE, COL_EMP_ID, userId);
    }

    /** Insert new user */
    public static String insertUser(String email, String firstName, String lastName) {
        return String.format(
                "INSERT INTO %s (%s, %s, %s) VALUES ('%s', '%s', '%s')",
                USER_TABLE, COL_EMP_EMAIL, COL_EMP_NAME, COL_EMP_AGE,
                escape(email), escape(firstName), escape(lastName));
    }

    /** Update user password */
    public static String updateUserPassword(int userId, String newPassword) {
        return String.format(
                "UPDATE %s SET %s = '%s' WHERE %s = %d",
                USER_TABLE, COL_EMP_PASS, escape(newPassword), COL_EMP_ID, userId);
    }

    /** Delete user by ID */
    public static String deleteUserById(int userId) {
        return String.format(
                "DELETE FROM %s WHERE %s = %d",
                USER_TABLE, COL_EMP_ID, userId);
    }

    /** Count all users */
    public static String countUsers() {
        return String.format("SELECT COUNT(*) FROM %s", USER_TABLE);
    }

    /** Count users with custom condition */
    public static String countUsers(String condition) {
        return String.format("SELECT COUNT(*) FROM %s WHERE %s", USER_TABLE, condition);
    }

    // ==========================
    // Private Helpers
    // ==========================

    /**
     * Escapes single quotes in input to prevent SQL errors.
     * NOTE: Not a replacement for prepared statements, but helps in tests.
     */
    private static String escape(String input) {
        return input == null ? "" : input.replace("'", "''");
    }
}
