package db;

import java.sql.*;
import java.util.*;

/**
 * Utility class for executing SQL queries and printing results in different formats.
 * 
 * Note: Requires DatabaseConnectionManager to provide a valid Connection.
 */
public final class DatabaseUtils {

    /** Enum for supported output formats */
    public enum OutputFormat {
        TABLE, RAW, ROW, JSON
    }

    /** Prevent instantiation */
    private DatabaseUtils() {}

    // ----------------------------------------------------------------------
    // QUERY EXECUTION METHODS
    // ----------------------------------------------------------------------

    /**
     * Execute a SELECT query and return results as a list of rows.
     * Each row is represented as a Map (columnLabel -> value).
     *
     * @param query SQL SELECT query
     * @return List of rows (as maps), or empty list if no results
     */
    public static List<Map<String, Object>> executeQuery(String query) {
        List<Map<String, Object>> results = new ArrayList<>();

        try (Connection conn = DatabaseConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                for (int i = 1; i <= colCount; i++) {
                    row.put(meta.getColumnLabel(i), rs.getObject(i));
                }
                results.add(row);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error executing query: " + query, e);
        }

        return results;
    }

    /**
     * Execute an UPDATE/INSERT/DELETE statement.
     *
     * @param query SQL update query
     * @return Number of affected rows
     */
    public static int executeUpdate(String query) {
        try (Connection conn = DatabaseConnectionManager.getConnection();
             Statement stmt = conn.createStatement()) {

            return stmt.executeUpdate(query);

        } catch (SQLException e) {
            throw new RuntimeException("Error executing update: " + query, e);
        }
    }

    /**
     * Execute a query expected to return a single value
     * (e.g., COUNT(*), MAX(id), etc.).
     *
     * @param query SQL query
     * @return First column of first row, or null if no results
     */
    public static Object getSingleValue(String query) {
        try (Connection conn = DatabaseConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getObject(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing query: " + query, e);
        }
        return null;
    }

    // ----------------------------------------------------------------------
    // RESULT PRINTING METHODS
    // ----------------------------------------------------------------------

    /** Print query results in default TABLE format. */
    public static void printResult(List<Map<String, Object>> results) {
        printResult(results, OutputFormat.TABLE);
    }

    /** Print query results in the given format. */
    public static void printResult(List<Map<String, Object>> results, OutputFormat format) {
        if (results == null || results.isEmpty()) {
            System.out.println("[INFO] No records found.");
            return;
        }

        switch (format) {
            case RAW:
                printRaw(results);
                break;
            case ROW:
                printRow(results);
                break;
            case JSON:
                printJson(results);
                break;
            case TABLE:
            default:
                printTable(results);
                break;
        }
    }

    // ----------------------------------------------------------------------
    // PRIVATE HELPER METHODS FOR FORMATTED PRINTING
    // ----------------------------------------------------------------------

    private static void printRaw(List<Map<String, Object>> results) {
        for (Map<String, Object> row : results) {
            row.forEach((k, v) -> System.out.print(k + "=" + (v != null ? v : "NULL") + " "));
            System.out.println();
        }
    }

    private static void printRow(List<Map<String, Object>> results) {
        for (Map<String, Object> row : results) {
            row.forEach((k, v) -> System.out.println(k + " = " + (v != null ? v : "NULL")));
            System.out.println("---");
        }
    }

    private static void printJson(List<Map<String, Object>> results) {
        for (Map<String, Object> row : results) {
            System.out.println("{");
            row.forEach((k, v) ->
                    System.out.println("  \"" + k + "\": \"" + (v != null ? v.toString() : "NULL") + "\""));
            System.out.println("}");
        }
    }

    private static void printTable(List<Map<String, Object>> results) {
        Set<String> headers = results.get(0).keySet();
        Map<String, Integer> columnWidths = new HashMap<>();

        // Calculate max width for each column
        for (String header : headers) {
            int maxLength = header.length();
            for (Map<String, Object> row : results) {
                Object value = row.get(header);
                if (value != null) {
                    maxLength = Math.max(maxLength, value.toString().length());
                }
            }
            columnWidths.put(header, maxLength + 2); // padding
        }

        // Print header row
        StringBuilder headerRow = new StringBuilder("|");
        for (String header : headers) {
            headerRow.append(String.format(" %-" + columnWidths.get(header) + "s|", header));
        }
        System.out.println(headerRow);

        // Print separator
        StringBuilder separator = new StringBuilder("+");
        for (String header : headers) {
            separator.append("-".repeat(columnWidths.get(header) + 1)).append("+");
        }
        System.out.println(separator);

        // Print data rows with alignment
        for (Map<String, Object> row : results) {
            StringBuilder rowBuilder = new StringBuilder("|");
            for (String header : headers) {
                Object value = row.get(header);
                String formattedValue = value != null ? value.toString() : "NULL";

                if (value instanceof Number) {
                    // Right-align numbers
                    rowBuilder.append(String.format(" %" + columnWidths.get(header) + "s|", formattedValue));
                } else {
                    // Left-align text
                    rowBuilder.append(String.format(" %-" + columnWidths.get(header) + "s|", formattedValue));
                }
            }
            System.out.println(rowBuilder);
        }
    }
}
