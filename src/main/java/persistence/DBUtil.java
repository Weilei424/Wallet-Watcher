package businessLogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import persistence.User;

public final class DBUtil {
	private static final String USERNAME = "root";
	private static final String PASSWORD = "qwerty";
	private static final String CONN_STRING = "jdbc:mysql://localhost:3306";
	
	private static Connection getConnection(String tableName) throws SQLException {
		return DriverManager.getConnection(CONN_STRING + tableName, USERNAME, PASSWORD);
	}
	
	public static void createUser(User u) throws Exception {
		String query = "INSERT INTO users (username, pd, salt, firstname, lastname, acctype) values (?, ?, ?, ?, ?, ?)";
		String getRef = "SELECT * FROM users WHERE username=?";
		ResultSet rs = null;
		
		try (
				Connection conn = DBUtil.getConnection("/test");
				PreparedStatement p = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				PreparedStatement ref = conn.prepareStatement(getRef, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				) {
			if (!DBUtil.checkUser(u))
				throw new IllegalArgumentException("username is not available");
			p.setString(1, u.getUserName());
			p.setString(2, u.getPassword());
			p.setString(3, u.getSalt());
			p.setString(4, u.getFirstName());
			p.setString(5, u.getLastName());
			p.setString(6, u.getType());
			p.executeUpdate();
			ref.setString(1, u.getUserName());
			rs = ref.executeQuery();
			while (rs.next()) 
				u.setRef(rs.getInt("ref"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) 
				rs.close();
		}
	}
	
	@SuppressWarnings("finally")
	public static boolean checkUser(User u) {
		String query = "SELECT * FROM users";
		boolean result = false;
		try (
				Connection conn = DBUtil.getConnection("/test");
				Statement unique = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = unique.executeQuery(query);
				) {
			while (rs.next()) {
				if (rs.getString("username").equals(u.getUserName())) 
					throw new IllegalArgumentException("username is not available");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return result;
		}
		
	}
}
