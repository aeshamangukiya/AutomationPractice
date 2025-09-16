package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import constants.FrameworkConstants;

public final class DatabaseConnectionManager {

    private DatabaseConnectionManager() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                FrameworkConstants.DB_URL,
                FrameworkConstants.DB_USERNAME,
                FrameworkConstants.DB_PASSWORD
        );
    }
}
