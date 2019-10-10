package pkgData;

import javax.crypto.SecretKey;

import pkgMisc.PasswordUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Database
{

	private static final String LOGFILE_PATH = "/logs/errors.log";
	private static final File LOGFILE = new File(new File("").getAbsolutePath() + Database.getLogFilePath());
	private final String DEFAULT_PASSSWORD = "6dnx8IHJFlFtf5REJkKIbIYe78DvAC6+zqjko7vkHg8=hellaasf";
	private static Database instance;

	private Database()
	{
	}

	public static Database newInstance()
	{
		if (instance == null)
			instance = new Database();
		return instance;
	}

	public String doDecrypt(String enc, char[] key) throws IOException, Exception
	{
		SecretKey skey = PasswordUtils.generateKey(key);
		key = "000000".toCharArray();
		return PasswordUtils.decrypt(enc.getBytes(StandardCharsets.UTF_8), skey);
	}

	public String doEncrypt(String toEncrypt, char[] key) throws Exception
	{
		SecretKey skey = PasswordUtils.generateKey(key);
		key = "000000".toCharArray();

		String text = PasswordUtils.encrypt(toEncrypt.getBytes(StandardCharsets.UTF_8), skey);
		return text;
	}

	public static String getLogFilePath()
	{
		return LOGFILE_PATH;
	}

	/**
	 * @return the lOGFILE
	 */
	public static File getLogFile()
	{
		return LOGFILE;
	}

	public char[] getDefaultPasssword()
	{
		return DEFAULT_PASSSWORD.toCharArray();
	}

}
