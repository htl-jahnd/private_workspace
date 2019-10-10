package pkgData;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.collections.ObservableList;
import pkgExceptions.UserException;
import pkgMisc.PasswordUtils;

public class Database
{
	private static Database INSTANCE;
	private static Connection conn = null;
	private static boolean isConnectionSet = false;
	private static String connectionString ="212.152.179.117";// "192.168.128.152" ;
	private static final String DB_USER = "d4b12";
	private static final String DB_PWD = "d4b";
	private static User currentUser;
	private ArrayList<Activity> activities;
	private ArrayList<Entry> ownEntries;
	private ArrayList<Entry> allEntries;
	private ArrayList<Entry> userEntries;
	private ArrayList<User> users;

	private Database()
	{
		activities = new ArrayList<Activity>();
		ownEntries = new ArrayList<Entry>();
		userEntries = new ArrayList<>();
		allEntries = new ArrayList<>();
		users = new ArrayList<>();
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

	public Collection<Activity> getAllActivities()
	{
		return activities;
	}

	public Collection<Entry> getOwnEntries()
	{
		return ownEntries;
	}

	public Collection<Entry> getAllEntries()
	{
		return allEntries;
	}

	public Collection<Entry> getUserEntries()
	{
		return userEntries;
	}

	public void createNewUser(String usrname, char[] pwd, boolean isAdmin) throws NoSuchAlgorithmException, SQLException
	{
		String salt = PasswordUtils.generateSaltAsString(PasswordUtils.SALT_LENGTH);
		String hashedPassword = PasswordUtils.getSHA512Hash(new String(pwd), salt);
		User usr = new User(usrname, hashedPassword, salt, isAdmin);
		String stmtString2 = "INSERT INTO users VALUES(?,?,?,?)";
		PreparedStatement stmt2 = conn.prepareStatement(stmtString2);
		stmt2.setString(1, usr.getUsername());
		stmt2.setString(2, usr.getPassword());
		stmt2.setString(3, usr.getSalt());
		stmt2.setBoolean(4, usr.isAdmin());
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
		boolean isAdmin = false;
		if (rs.next())
		{
			isAdmin = rs.getBoolean(3);
		} else
			throw new UserException("Invalid Username or Password");

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
		currentUser = new User(username, hashed, salt, isAdmin);
		if (isAdmin)
		{
			allEntries = new ArrayList<>();
			userEntries = new ArrayList<>();
		}
	}

	public void selectAllActivities() throws SQLException
	{
		String stmtString = "SELECT id, name from activities";
		PreparedStatement stmt = conn.prepareStatement(stmtString);
		ResultSet rs = stmt.executeQuery();
		activities.clear();
		while (rs.next())
		{
			activities.add(new Activity(rs.getInt(1), rs.getString(2)));
		}

	}

	// ***********************
	// CRUD for activities
	// ***********************

	public Activity addActivity(String name) throws SQLException
	{
		int id = -1;
		String select = "SELECT seqActivity.nextval from dual";
		PreparedStatement stmt = conn.prepareStatement(select);
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
			id = rs.getInt(1);

		String insert = "insert into activities values(?, ?)";
		stmt = conn.prepareStatement(insert);
		stmt.setInt(1, id);
		stmt.setString(2, name);
		stmt.executeUpdate();
		return new Activity(id, name);
	}

	public void updateActivity(Activity act) throws SQLException
	{
		String update = "UPDATE activities SET name=? WHERE id =?";
		PreparedStatement stmt = conn.prepareStatement(update);
		stmt.setString(1, act.getName());
		stmt.setInt(2, act.getActivityId());
		int i = stmt.executeUpdate();
		if (i < 1)
			throw new SQLException("Activity not found");
	}

	public void deleteActivity(Activity act) throws SQLException
	{
		String delete = "Delete from activities where id =?";
		PreparedStatement stmt = conn.prepareStatement(delete);
		stmt.setInt(1, act.getActivityId());
		int i = stmt.executeUpdate();
		if (i < 1)
			throw new SQLException("Activity not found");
	}

	// ***********************
	// CRUD for entries
	// ***********************
	public void addEntry(Entry entr) throws SQLException
	{
		LocalDateTime ldts = LocalDateTime.of(entr.getDate(), entr.getTimeStart());
		Timestamp st = Timestamp.valueOf(ldts);

		LocalDateTime ldte = LocalDateTime.of(entr.getDate(), entr.getTimeEnd());
		Timestamp et = Timestamp.valueOf(ldte);

		String insert = "insert into entries values(seqEntry.nextval, ?,?,?,?,?,?)";
		PreparedStatement stmt = conn.prepareStatement(insert);
		stmt.setInt(1, entr.getActivity().getActivityId());
		stmt.setString(2, entr.getTitle());
		stmt.setString(3, entr.getMessage());
		stmt.setString(4, entr.getUser().getUsername());
		stmt.setTimestamp(5, st);
		stmt.setTimestamp(6, et);
		stmt.executeUpdate();
	}

	public void updateListEntries(List<Entry> entrs) throws SQLException
	{
		String update = "update entries set activity_id =?, title = ?, message=?, user_id=?, start_time=?, end_time=? WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(update);
		for (Entry entr : entrs)
		{
			LocalDateTime ldts = LocalDateTime.of(entr.getDate(), entr.getTimeStart());
			Timestamp st = Timestamp.valueOf(ldts);

			LocalDateTime ldte = LocalDateTime.of(entr.getDate(), entr.getTimeEnd());
			Timestamp et = Timestamp.valueOf(ldte);
			stmt.setInt(1, entr.getActivity().getActivityId());
			stmt.setString(2, entr.getTitle());
			stmt.setString(3, entr.getMessage());
			stmt.setString(4, entr.getUser().getUsername());
			stmt.setTimestamp(5, st);
			stmt.setTimestamp(6, et);
			stmt.setInt(7, entr.getEntryId());
			stmt.addBatch();
		}
		stmt.executeBatch();
	}

	public void updateEntry(Entry entr) throws SQLException
	{

		LocalDateTime ldts = LocalDateTime.of(entr.getDate(), entr.getTimeStart());
		Timestamp st = Timestamp.valueOf(ldts);

		LocalDateTime ldte = LocalDateTime.of(entr.getDate(), entr.getTimeEnd());
		Timestamp et = Timestamp.valueOf(ldte);
		String update = "update entries set activity_id =?, title = ?, message=?, user_id=?, start_time=?, end_time=? WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(update);
		stmt.setInt(1, entr.getActivity().getActivityId());
		stmt.setString(2, entr.getTitle());
		stmt.setString(3, entr.getMessage());
		stmt.setString(4, entr.getUser().getUsername());
		stmt.setTimestamp(5, st);
		stmt.setTimestamp(6, et);
		stmt.setInt(7, entr.getEntryId());
		int i = stmt.executeUpdate();
		if (i < 1)
			throw new SQLException("Entry not found");
	}

	public void deleteListEntries(List<Entry> entriesToDelete) throws SQLException
	{
		String delete = "DELETE FROM entries WHERE id =?";
		PreparedStatement stmt = conn.prepareStatement(delete);
		for (Entry etr : entriesToDelete)
		{
			stmt.setInt(1, etr.getEntryId());
			stmt.addBatch();
		}
		stmt.executeBatch();
	}

	public void deleteEntry(Entry entr) throws SQLException
	{
		String delete = "DELETE FROM entries WHERE id =?";
		PreparedStatement stmt = conn.prepareStatement(delete);
		stmt.setInt(1, entr.getEntryId());
		int i = stmt.executeUpdate();
		if (i < 1)
			throw new SQLException("Entry not found");
	}

	// ***********************
	// SELECTS for Entries
	// ***********************

	public void selectUserEntries(User usr) throws Exception
	{
		if (!currentUser.isAdmin())
			throw new Exception("User is no admin");
		ArrayList<Entry> tmp = new ArrayList<Entry>();
		String select = "SELECT id, activity_id,message, start_time, end_time, title FROM entries WHERE user_id = ?";
		PreparedStatement stmt = conn.prepareStatement(select);
		stmt.setString(1, usr.getUsername());
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
		{
			int actId = rs.getInt(2);
			Activity act = activities.stream().filter(o -> o.getActivityId() == actId).findAny().orElse(null);
			if (act != null)
				tmp.add(new Entry(rs.getInt(1), act, usr, rs.getTimestamp(4).toLocalDateTime().toLocalDate(),
						rs.getTimestamp(4).toLocalDateTime().toLocalTime(),
						rs.getTimestamp(5).toLocalDateTime().toLocalTime(), rs.getString("title"), rs.getString(3)));
		}
		userEntries.clear();
		userEntries.addAll(tmp);
	}

	public void selectOwnEntries() throws Exception
	{
		ArrayList<Entry> tmp = new ArrayList<Entry>();
		String select = "SELECT id, activity_id,message, start_time, end_time, title FROM entries WHERE user_id = ?";
		PreparedStatement stmt = conn.prepareStatement(select);
		stmt.setString(1, currentUser.getUsername());
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
		{
			int actId = rs.getInt(2);
			Activity act = activities.stream().filter(o -> o.getActivityId() == actId).findAny().orElse(null);
			if (act != null)
				tmp.add(new Entry(rs.getInt(1), act, currentUser, rs.getTimestamp(4).toLocalDateTime().toLocalDate(),
						rs.getTimestamp(4).toLocalDateTime().toLocalTime(),
						rs.getTimestamp(5).toLocalDateTime().toLocalTime(), rs.getString("title"), rs.getString(3)));
		}
		ownEntries.clear();
		ownEntries.addAll(tmp);
	}

	public void selectAllEntries() throws Exception
	{
		if (!currentUser.isAdmin())
			throw new Exception("User is no admin");
		ArrayList<Entry> tmp = new ArrayList<Entry>();
		String select = "SELECT id, activity_id,message, start_time, end_time, title, username, password, salt, isadmin FROM entries INNER JOIN users ON entries.user_id = users.username";
		PreparedStatement stmt = conn.prepareStatement(select);
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
		{
			User usr = new User(rs.getString("username"), rs.getString("password"), rs.getString("salt"),
					rs.getBoolean("isAdmin"));
			int actId = rs.getInt(2);
			Activity act = activities.stream().filter(o -> o.getActivityId() == actId).findAny().orElse(null);
			if (act != null)
				tmp.add(new Entry(rs.getInt(1), act, usr, rs.getTimestamp(4).toLocalDateTime().toLocalDate(),
						rs.getTimestamp(4).toLocalDateTime().toLocalTime(),
						rs.getTimestamp(5).toLocalDateTime().toLocalTime(), rs.getString("title"), rs.getString(3)));
		}
		allEntries.clear();
		allEntries.addAll(tmp);
	}

	public void selectAllUsers() throws SQLException
	{
		ArrayList<User> tmp = new ArrayList<>();
		String select = "SELECT username, password, salt, isadmin from users";
		PreparedStatement stmt = conn.prepareStatement(select);
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
		{
			tmp.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4)));
		}
		users.clear();
		users.addAll(tmp);
	}

	public User getCurrentUser()
	{
		return currentUser;
	}

	public Collection<User> getAllUsers()
	{
		return users;
	}

	public void setAutoCommit(boolean b) throws SQLException
	{
		conn.setAutoCommit(b);

	}

	public void commit() throws SQLException
	{
		conn.commit();
	}

	public void rollback() throws SQLException
	{
		conn.rollback();
	}

	public Activity getNewActivity() throws SQLException
	{
		Activity ret = null;
		String select = "SELECT seqActivity.nextval from dual";
		PreparedStatement stmt = conn.prepareStatement(select);
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
			ret = new Activity(rs.getInt(1), "Activity");
		return ret;
	}

	public void updateListUsers(List<User> listUsers) throws SQLException
	{
		String update = "update users set isadmin=? WHERE username = ?";
		PreparedStatement stmt = conn.prepareStatement(update);
		for (User usr : listUsers)
		{
			stmt.setBoolean(1, usr.isAdmin());
			
			stmt.setString(2, usr.getUsername());
			stmt.addBatch();
		}
		stmt.executeBatch();
		
	}

	public void deleteListUsers(List<User> usersToDelete) throws SQLException
	{
		String delete = "DELETE FROM entries WHERE username =?";
		PreparedStatement stmt = conn.prepareStatement(delete);
		for (User usr : usersToDelete)
		{
			stmt.setString(1,usr.getUsername());
			stmt.addBatch();
		}
		stmt.executeBatch();
		
	}

}
