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
	
	private static final int LOCAL = 0;
	private static final int CLOUD = 1;
	
	public static final String EXPENSE = "expense";
	public static final String EARNING = "earning";
	public static final String INVESTMENT = "investment";
	public static final String STOCK = "stock";
	public static final String MISC = "misc";
	public static final String CARD = "card";
	
	private static Connection getConnection(int connectionType, String tableName) throws SQLException {
		if (connectionType == 0)
			return DriverManager.getConnection(CONN_STRING + tableName, USERNAME, PASSWORD);
		return DriverManager.getConnection(String.format(CLOUDCONN_STRING, tableName), CLOUDUSERNAME, CLOUDPASSWORD);
	}
	
	public static void createUser(User u) throws SQLException {
		String query = "INSERT INTO users (username, hashcode, salt, firstname, lastname, acctype) values (?, ?, ?, ?, ?, ?)";
		String getRef = "SELECT * FROM users WHERE username=?";
		String createTable = "(\r\n"
				+ "    ref INT NOT NULL AUTO_INCREMENT,\r\n"
				+ "    username VARCHAR(100),\r\n"
				+ "    item VARCHAR(20),\r\n"
				+ "    note VARCHAR(50),\r\n"
				+ "    tag ENUM('expense', 'earning', 'investment', 'stock', 'misc', 'card'),\r\n"
				+ "    amount FLOAT,\r\n"
				+ "    interest_rate FLOAT,\r\n"
				+ "    interest FLOAT,\r\n"
				+ "    recur BOOL,\r\n"
				+ "    category VARCHAR(20),\r\n"
				+ "    date_start DATE,\r\n"
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
			System.out.println(e.getMessage() + " from createUser()");
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
					throw new IllegalArgumentException("username exists!");
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + " from checkUser()");
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
			System.out.println(e.getMessage() + " from validateUser()");
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
			System.out.println(e.getMessage() + " from changePW()");
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
	public static boolean deleteUser(String username, String pw) {
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
			System.out.println(e.getMessage() + " from deleteUser()");
		}
		return flag;
	}
	
	public static boolean insert(String username, LedgerItem ledger, String type) {
		if (ledger == null) 
			throw new IllegalArgumentException("Invalid input!");
		
		boolean flag = false;
		String item = ledger.getItemName();
		String note = ledger.getNote();
		String tag = null;
		switch (type) {
			case "expense":
				tag = type;
				break;
			case "earning":
				tag = type;
				break;
			case "investment":
				tag = type;
				break;
			case "stock":
				tag = type;
				break;
			case "misc":
				tag = type;
				break;
			case "card":
				tag = type;
				break;
			default:
				throw new IllegalArgumentException("Invalid ledger item type!");
		}
		double amount = ledger.getAmount();
		double interestRate = 0.00;
		double interest = 0.00;
		int recur = ledger.isRecurring() == true ? 1 : 0;
		String category = ledger.getCategory();
		String dateStart = ledger.getDate().toString();
		if (ledger instanceof Stock_Fund)
			amount = ((Stock_Fund) ledger).getCurrent();
		if (ledger instanceof Investment) {
			Investment obj = (Investment) ledger;
			interestRate = obj.getRate();
			interest = obj.getInterest();
		}
		
		String insert = "INSERT INTO " + username + " (username, item, note, tag, amount, interest_rate, interest, recur, category, date_start) VALUES (?, ?, ?, ?, TRUNCATE(?, 2), ?, ?, ?, ?, ?)";
		
		try (
				Connection conn = DBUtil.getConnection(LOCAL, "/test");
				PreparedStatement p = conn.prepareStatement(insert, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				) {
			p.setString(1, username);
			p.setString(2, item);
			p.setString(3, note);
			p.setString(4, tag);
			p.setDouble(5, amount);
			p.setDouble(6, interestRate);
			p.setDouble(7, interest);
			p.setInt(8, recur);
			p.setString(9, category);
			p.setString(10, dateStart);
			p.executeUpdate();
			flag = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage() + " from insert()");
		}
			
		return flag;
	}
	
	public static boolean query() {
		return false;
	}
	
	public static boolean update() {
		return false;
	}
}
