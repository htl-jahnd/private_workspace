package pkgData;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.codec.binary.Base64;

import pkgExceptions.UserException;
import pkgMisc.PasswordUtils;

public class Database
{
	private static Database INSTANCE;
	private static Connection conn = null;
	private static boolean isConnectionSet = false;
	private static String connectionString = "212.152.179.117"; 
	private static final String DB_USER = "d4b12";
	private static final String DB_PWD = "d4b";
	private static User currentUser;

	private Database()
	{
	}

	public static Database newInstance() throws Exception
	{
		if (INSTANCE == null)
		{
			INSTANCE = new Database();
			createConnection();
		}
		return INSTANCE;
	}
	
	
	public void createNewUser(User user) throws NoSuchAlgorithmException, SQLException {
		String salt = PasswordUtils.generateSaltAsString(PasswordUtils.SALT_LENGTH);
		String hashedPassword = PasswordUtils.getSHA512Hash(user.getPassword(), salt);

		String stmtString2 = "INSERT INTO users VALUES(?,?,?,?)";
		PreparedStatement stmt2 = conn.prepareStatement(stmtString2);
		stmt2.setString(1, user.getUsername());
		stmt2.setString(2, hashedPassword);
		stmt2.setString(3, salt);
		stmt2.setBoolean(4, user.isAdmin());
		stmt2.executeUpdate();
	}
	
	private static void createConnection() throws Exception
	{
		if (conn == null)
		{
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		}
		StringBuilder bu = new StringBuilder("jdbc:oracle:thin:@").append(connectionString).append(":1521:ora11g");
		connectionString = bu.toString();
		DriverManager.setLoginTimeout(3);
		conn = DriverManager.getConnection(connectionString, DB_USER, DB_PWD);
		// Connects with database user "d4b12"
		conn.setAutoCommit(true);
		isConnectionSet = true;
	}
	
	public void login(String username, char[] pwd) throws NoSuchAlgorithmException, UserException, SQLException
	{
		String stmtString = "SELECT username, password, isAdmin FROM users WHERE username LIKE ?";

		PreparedStatement stmt = conn.prepareStatement(stmtString);
		stmt.setString(1, username);
		ResultSet rs = stmt.executeQuery();
		boolean isAdmin =false;
		if (rs.next())
		{
			isAdmin = rs.getBoolean(3);
		}
		else throw new UserException("Invalid Username or Password");

		String salt = null;
		String stmtString2 = "SELECT salt FROM users WHERE username LIKE ?";
		PreparedStatement stmt2 = conn.prepareStatement(stmtString2);
		stmt2.setString(1, username);
		ResultSet rs2 = stmt2.executeQuery();
		if (rs2.next())
		{
			salt = rs.getString(1);
		}
		String hashed = PasswordUtils.getSHA512Hash(new String(pwd), salt);

		currentUser =new User(username, hashed, salt, isAdmin);
	}
}
