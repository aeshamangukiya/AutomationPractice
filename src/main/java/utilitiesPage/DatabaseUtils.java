package utilitiesPage;

import java.sql.*;

public class DatabaseUtils {

	private static final String URL = "jdbc:mysql://localhost:3306/emp";
	private static final String USER = "root";
	private static final String PASSWORD = "SQL9$am5.f";

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public static void printEmployees() {
		String readEntireTableQuery = "SELECT * FROM employee";
		String readByIDQuery = "SELECT * FROM employee WHERE idemployee = 1";

		try (Connection con = getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(readByIDQuery)) {

			while (rs.next()) {
				System.out.println(rs.getInt("idemployee") + " " + rs.getString("Name") + " " + rs.getInt("Age"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
