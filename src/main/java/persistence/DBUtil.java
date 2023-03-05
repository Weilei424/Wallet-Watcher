package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import businessLogic.Util;

public final class DBUtil {
	private static final String USERNAME = "root";
	private static final String PASSWORD = "qwerty";
	private static final String CONN_STRING = "jdbc:mysql://localhost:3306";
	
	private static final String CLOUDUSERNAME = "team7";
	private static final String CLOUDPASSWORD = "eecs2311!";
	private static final String CLOUDCONN_STRING = "jdbc:mysql://wallet-watcher.mysql.database.azure.com:3306%s?useSSL=true";
	
	private final static int LOCAL = 0;
	private final static int CLOUD = 1;
	
	private static Connection getConnection(int connectionType, String tableName) throws SQLException {
		if (connectionType == 0)
			return DriverManager.getConnection(CONN_STRING + tableName, USERNAME, PASSWORD);
		return DriverManager.getConnection(String.format(CLOUDCONN_STRING, tableName), CLOUDUSERNAME, CLOUDPASSWORD);
	}
	
	public static void createUser(User u) throws SQLException {
		String query = "INSERT INTO users (username, hashcode, salt, firstname, lastname, acctype) values (?, ?, ?, ?, ?, ?)";
		String getRef = "SELECT * FROM users WHERE username=?";
		ResultSet rs = null;
		
		try (
				Connection conn = DBUtil.getConnection(LOCAL, "/test");
				PreparedStatement p = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				PreparedStatement ref = conn.prepareStatement(getRef, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				) {
			if (!DBUtil.checkUser(u)) {
				throw new IllegalArgumentException("username not available");
			}
			p.setString(1, u.getUserName());
			p.setString(2, Util.encrypt(u.getPassword(), u.getSalt()));
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
		boolean flag = true;
		try (
				Connection conn = DBUtil.getConnection(LOCAL, "/test");
				Statement unique = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = unique.executeQuery(query);
				) {
			while (rs.next()) {
				if (rs.getString("username").equals(u.getUserName())) {
					flag = false;
					throw new IllegalArgumentException();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return flag;
		}
	}
	
	@SuppressWarnings("finally")
	public static boolean changePW(String username, String oldpw, String newpw) {
		String query = "SELECT * FROM users";
		String change = "UPDATE users SET hashcode = ? WHERE ref = ?";
		boolean flag = false;
		try (
				Connection conn = DBUtil.getConnection(LOCAL, "/test");
				Statement acc = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				PreparedStatement update = conn.prepareStatement(change, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = acc.executeQuery(query);
				) {
			while (rs.next()) {
				if (rs.getString("username").equals(username) && Util.encrypt(oldpw, rs.getString("salt")).equals(rs.getString("hashcode"))) {
					update.setString(1, Util.encrypt(newpw, rs.getString("salt")));
					update.setInt(2, rs.getInt("ref"));
					update.executeUpdate();
					flag = true;
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return flag;
		}
	}
}
