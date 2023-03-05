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
		String createTable = "(    ref INT NOT NULL AUTO_INCREMENT,\r\n"
				+ "    username VARCHAR(100),\r\n"
				+ "    item VARCHAR(20),\r\n"
				+ "    note VARCHAR(50),\r\n"
				+ "    amount FLOAT,\r\n"
				+ "    recur BOOL,\r\n"
				+ "    category VARCHAR(20),\r\n"
				+ "    date_ DATE,\r\n"
				+ "    PRIMARY KEY(ref)\r\n"
				+ "    )";
		ResultSet rs = null;
		
		try (
				Connection conn = DBUtil.getConnection(LOCAL, "/test");
				PreparedStatement p = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				PreparedStatement ref = conn.prepareStatement(getRef, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				Statement newTable = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				) {
			if (!DBUtil.checkUser(u.getUserName())) {
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
			newTable.execute("CREATE TABLE " + u.getUserName() + createTable);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) 
				rs.close();
		}
	}
	
	/**
	 * This method check if the input username exists in the DB.
	 * @param 	u is an username in String.
	 * @return	false is exist, true otherwise.
	 */
	@SuppressWarnings("finally")
	public static boolean checkUser(String username) {
		String query = "SELECT * FROM users";
		boolean flag = true;
		
		try (
				Connection conn = DBUtil.getConnection(LOCAL, "/test");
				Statement unique = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = unique.executeQuery(query);
				) {
			while (rs.next()) {
				if (rs.getString("username").equals(username)) {
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
	
	/**
	 * This method check if username and password matches.
	 * @param 	username
	 * @param 	pw
	 * @return	true if username and password matches, false otherwise.
	 */
	@SuppressWarnings("finally")
	public static boolean validateUser(String username, String pw) {
		boolean result = false;
		String query = "SELECT * FROM users";
		
		try (
				Connection conn = DBUtil.getConnection(LOCAL, "/test");
				Statement unique = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = unique.executeQuery(query);
				) {
			while (rs.next()) {
				if (rs.getString("username").equals(username) && Util.encrypt(pw, rs.getString("salt")).equals(rs.getString("hashcode"))) {
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return result;
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
	
	/**
	 * This method removes the user account from DB (in both users table and its own ledger table).
	 * @param 	username
	 * @param	pw
	 * @return	true is the deletion is success, false otherwise.
	 */
	public static boolean delectUser(String username, String pw) {
		boolean flag = false;
		
		if (checkUser(username)) {
			return flag;
		}
		String query = "SELECT * FROM users WHERE username = ?";
		String deleteUser = "DELETE FROM users WHERE username = ?";
		String deleteTable = "DROP TABLE " + username;
		
		try (
				Connection conn = DBUtil.getConnection(LOCAL, "/test");
				PreparedStatement p = conn.prepareStatement(deleteUser, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				PreparedStatement q = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = null;
				) {
			if (validateUser(username, pw)) {
				p.setString(1, username);
				p.executeUpdate();
				stmt.execute(deleteTable);
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public static boolean insert() {
		return false;
	}
	
	public static boolean query() {
		return false;
	}
	
	public static boolean update() {
		return false;
	}
}
