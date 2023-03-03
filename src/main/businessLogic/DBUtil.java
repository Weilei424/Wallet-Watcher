package main.businessLogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.persistence.User;

public final class DBUtil {
	private static final String USERNAME = "root";
	private static final String PASSWORD = "qwerty";
	private static final String CONN_STRING = "jdbc:mysql:/localhost:3306/";
	
	private static Connection getConnection(String tableName) throws SQLException {
		return DriverManager.getConnection(CONN_STRING + tableName, USERNAME, PASSWORD);
	}
	
	public static void createUser(User u) throws Exception {
		String query = "INSERT INTO users (username, pd, salt, firstname, lastname, acctype) values (?, ?, ?, ?, ?, ?)";
		ResultSet rs = null;
		
		try (
				Connection conn = DBUtil.getConnection("users");
				PreparedStatement p = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				) {
			p.setString(1, u.getUserName());
			p.setString(2, u.getPassword());
			p.setString(3, u.getSalt());
			p.setString(3, u.getFirstName());
			p.setString(4, u.getLastName());
			p.setString(5, u.getType());
			rs = p.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
		}
	}
}
