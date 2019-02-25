package pkgMisc;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base64;


public class PasswordUtils
{

	public static final int SALT_LENGTH = 16;
	private static final Charset STANDARD_CHARSET = StandardCharsets.UTF_8;

	public static String generateSaltAsString(int length)
	{
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[length];
		random.nextBytes(bytes);
		String salt = new Base64().encodeToString(bytes);
		return salt;
	}

	public static String getSHA512Hash(String passwordToHash, String salt) throws NoSuchAlgorithmException
	{
		String generatedPassword = null;
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		md.update(salt.getBytes(STANDARD_CHARSET));
		byte[] bytes = md.digest(passwordToHash.getBytes(STANDARD_CHARSET));
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++)
		{
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		generatedPassword = sb.toString();
		return generatedPassword;
	}

}
