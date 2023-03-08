package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
	
	private static final String CLOUDDB = "/ww";
	
	public static final String EXPENSE = "expense";
	public static final String EARNING = "earning";
	public static final String INVESTMENT = "investment";
	public static final String STOCK = "stock";
	public static final String MISC = "misc";
	public static final String CARD = "card";
	
	public static final String ITEM = "item";
	public static final String NOTE = "note";
	public static final String TAG = "tag";
	public static final String AMOUNT = "amount";
	public static final String INTEREST_RATE = "interest_rate";
	public static final String INTEREST = "interest";
	public static final String RECUR = "recur";
	public static final String CATEGORY = "category";
	public static final String DATE_START = "date_start";
	
	/**
	 * This method returns a Connection object to selected DB.
	 * @param 	connectionType CAN ONLY BE STATIC INT LOCAL OR CLOUD: 
	 * LOCAL(0) will return your local MYSQL connection; 
	 * CLOUD(1) will return Azure cloud MYSQL connection;
	 * @param 	tableName is a String of the table name that goes after the address.
	 * @return	a Connection object to selected DB.
	 * @throws 	SQLException.
	 */
	private static Connection getConnection(int connectionType, String tableName) throws SQLException {
		if (connectionType == 0)
			return DriverManager.getConnection(CONN_STRING + tableName, USERNAME, PASSWORD);
		return DriverManager.getConnection(String.format(CLOUDCONN_STRING, tableName), CLOUDUSERNAME, CLOUDPASSWORD);
	}
	
	/**
	 * This method insert a new user row in users table. Also create an user's ledger item table name by username.
	 * This method will NOT create duplicate user account under the same username.
	 * @param 	u is the User object to be added.
	 * @throws 	SQLException.
	 */
	public static void createUser(User u) throws SQLException {
		String query = "INSERT INTO users (username, hashcode, salt, firstname, lastname, acctype) values (?, ?, ?, ?, ?, ?)";
		String getRef = "SELECT * FROM users WHERE username=?";
		String createTable = "(\r\n"
				+ "    ref INT NOT NULL AUTO_INCREMENT,\r\n"
				+ "    username VARCHAR(100),\r\n"
				+ "    item VARCHAR(100),\r\n"
				+ "    note VARCHAR(200),\r\n"
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
				Connection conn = DBUtil.getConnection(CLOUD, CLOUDDB);
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
				Connection conn = DBUtil.getConnection(CLOUD, CLOUDDB);
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
	 * @param 	username is the row to be removed from users table.
	 * @param	pw is the user's password.
	 * @return	true if username and password matches, false otherwise.
	 */
	@SuppressWarnings("finally")
	public static boolean validateUser(String username, String pw) {
		boolean result = false;
		String query = "SELECT * FROM users";
		
		try (
				Connection conn = DBUtil.getConnection(CLOUD, CLOUDDB);
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
				Connection conn = DBUtil.getConnection(CLOUD, CLOUDDB);
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
	 * @param 	username is the row to be removed from users table.
	 * @param	pw is the user's password.
	 * @return	true is the deletion is success, false otherwise.
	 */
	public static boolean deleteUser(String username, String pw) {
		boolean flag = false;
		
		if (checkUser(username))
			return flag;

		String query = "SELECT * FROM users WHERE username = ?";
		String deleteUser = "DELETE FROM users WHERE username = ?";
		String deleteTable = "DROP TABLE " + username;
		
		try (
				Connection conn = DBUtil.getConnection(CLOUD, CLOUDDB);
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
	
	/**
	 * THIS METHOD ASSUMES username EXISTS!
	 * This method is for insert a new row to the user's ledger table.
	 * @param 	username is the table name to be inserted.
	 * @param 	ledger is the LedgerItem object to be inserted.
	 * @param 	type MUST BE ONE OF THE STATIC STRINGS: 
	 * expense or earning or investment or stock or misc or card.
	 * @return	true if insert operation is success, false otherwise.
	 */
	public static boolean insert(String username, LedgerItem ledger, String type) {
		if (ledger == null) 
			throw new IllegalArgumentException("Invalid input!");
		
		boolean flag = false;
		String item = ledger.getItemName();
		String note = ledger.getNote();
		String tag = getTag(type);
		
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
				Connection conn = DBUtil.getConnection(CLOUD, CLOUDDB);
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
	
	public static boolean delete(String username, int ref) {
		boolean flag = false;
		
		return flag;
	}
	
	/**
	 * 
	 * @param 	username
	 * @param 	column
	 * @param 	value
	 * @return
	 */
	public static JTable query(String username,  String column, String value) {
		String col = "";
		String querry="slect * from"+username;
		String[] columnNames = {ITEM, NOTE,TAG,AMOUNT,INTEREST_RATE,INTEREST,RECUR,CATEGORY,DATE_START};
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
		if (!(column.equals("all"))) 
			if(!(column.equals(ITEM) || column.equals(NOTE))) {
					col = getColumn(column);
					querry+="where "+col +"="+value;
			}
		
		ResultSet rs = null;
		try(Connection conn = DBUtil.getConnection(LOCAL, "/test");
				
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				){
		rs=stmt.executeQuery(querry);
				
		while(rs.next()) {
			String Item=rs.getString(ITEM);
			String Note=rs.getString(NOTE);
			String Amount=rs.getString(AMOUNT);
			String Tag=rs.getString(TAG);
			String IR=rs.getString(INTEREST_RATE);
			String In=rs.getString(INTEREST);
			String Re=rs.getString(RECUR);
			String cate = rs.getString(CATEGORY);
			String date= rs.getString(DATE_START);
			
			String[]data= {Item,Note,Tag,Amount,IR,In,Re,cate,date};
			tableModel.addRow(data);
			
			
			
			
			
			
		}
		
		
		rs.close();
		
		
		}
		catch(SQLException e) {
			System.out.println(e.getMessage() + " from insert()");
			
		}
			
			
		
		
		
		
		
		
		
		
		
		
		
		
		JTable table = new JTable(tableModel);
		return table;
	}
	
	/**
	 * THIS METHOD ASSUMES username EXISTS!
	 * This method update one cell of selected ledger item table.
	 * 
	 * @param 	username is current logged in username.
	 * @param 	ref is the ref# of the row.
	 * @param 	column is the column to be updated.
	 * @param 	value is the value to be updated.
	 * @return	true if update operation is success, false otherwise.
	 */
	public static boolean update(String username, int ref, String column, String value) {
		boolean flag = false;
		
		if (!refExist(username, ref))
			throw new IllegalArgumentException("row not found!");
		
		String col = getColumn(column);
		double num = 0.00;
		String update = "UPDATE " + username + " SET " + col + " = " + value + "WHERE ref = " + ref;
		
		if (column.equals("amount") || column.equals("interest_rate") || column.equals("interest") || column.equals("recur")) 
			num = Double.parseDouble(value);
		
		try (
				Connection conn = DBUtil.getConnection(CLOUD, CLOUDDB);
				Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				) {
			st.execute(update);
			flag = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage() + " from insert()");
		}
		
		return flag;
	}
	
	/**
	 * This method check if input string type is a valid ledger item type.
	 * @param 	type MUST BE ONE OF THE STATIC STRINGS: 
	 * expense or earning or investment or stock or misc or card.
	 * @return	tag name in String.
	 */
	private static String getTag(String type) {
		String tag = null;
		switch (type) {
			case EXPENSE:
				tag = type;
				break;
			case EARNING:
				tag = type;
				break;
			case INVESTMENT:
				tag = type;
				break;
			case STOCK:
				tag = type;
				break;
			case MISC:
				tag = type;
				break;
			case CARD:
				tag = type;
				break;
			default:
				throw new IllegalArgumentException("Invalid ledger item type!");
		}
		
		return tag;
	}
	
	/**
	 *  This method check if input string column is a valid ledger table column name.
	 * @param 	column MUST BE ONE OF THE STATIC STRINGS: 
	 * item, note, tag, amount, interest_rate, interest, recur, category, date_start.
	 * @return	column name in String.
	 */
	private static String getColumn(String column) {
		String col = "";
		switch (column) {
			case ITEM:
				col = column;
				break;
			case NOTE:
				col = column;
				break;
			case TAG:
				col = column;
				break;
			case AMOUNT:
				col = column;
				break;
			case INTEREST_RATE:
				col = column;
				break;
			case INTEREST:
				col = column;
				break;
			case RECUR:
				col = column;
				break;
			case CATEGORY:
				col = column;
				break;
			case DATE_START:
				col = column;
				break;
			default:
				throw new IllegalArgumentException("Invalid column to update!");
		}
		
		return col;
	}
	
	/**
	 * THIS METHOD ASSUMES username EXISTS!
	 * This method returns the number of rows in selected table.
	 * @param 	username is the table name.
	 * @return	the number of rows in the selected table.
	 */
	private static int getMaxRow(String username) {
		int max = 0;
		String query = "SELECT COUNT(*) FROM " + username;
		
		try (
				Connection conn = DBUtil.getConnection(CLOUD, CLOUDDB);
				Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = st.executeQuery(query);
				) {
			while (rs.next()) 
				max = rs.getInt("COUNT(*)");
		} catch (SQLException e) {
			System.out.println(e.getMessage() + " from getMaxRow()");
		}
		
		return max;
	}
	
	/**
	 * THIS METHOD ASSUMES username EXISTS!
	 * This method checks if the ref exist in selected table.
	 * @param 	username is the table name.
	 * @param	ref is the ref number.
	 * @return	true if ref exists, false otherwise.
	 */
	private static boolean refExist(String username, int ref) {
		int flag = 0;
		String query = "SELECT EXISTS(SELECT * FROM " + username + " WHERE ref = " + ref +")";
		String getResult = "EXISTS(SELECT * FROM \" + username + \" WHERE ref = \" + ref +\")";
		
		try (
				Connection conn = DBUtil.getConnection(CLOUD, CLOUDDB);
				Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = st.executeQuery(query);
				) {
			while (rs.next()) 
				flag = rs.getInt(getResult);
		} catch (SQLException e) {
			System.out.println(e.getMessage() + " from getMaxRow()");
		}
		
		return flag != 0 ? true : false;
	}
}
