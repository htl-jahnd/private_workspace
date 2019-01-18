package pkgMisc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Locale;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class PasswordUtils
{

	public static final int SALT_LENGTH = 16;
	private static final Charset STANDARD_CHARSET = StandardCharsets.UTF_8;



	public static byte[] generateSalt(int length)
	{
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[length];
		random.nextBytes(bytes);
		return bytes;
	}

	public static String generateSaltAsString(int length)
	{
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[length];
		random.nextBytes(bytes);
		String salt = new Base64().encodeToString(bytes);
		return salt;
	}

	public static String encrypt(String str, String password)
			throws NoSuchAlgorithmException, NoSuchPaddingException, IOException, IllegalBlockSizeException,
			BadPaddingException, InvalidParameterSpecException, InvalidKeyException, InvalidKeySpecException
	{
		byte[] saltBytes = generateSalt(SALT_LENGTH);

		// Derive the key
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, 65536, 256);

		SecretKey secretKey = factory.generateSecret(spec);
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

		// encrypt the message
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();
		byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
		byte[] encryptedTextBytes = cipher.doFinal(str.getBytes(STANDARD_CHARSET));

		// prepend the salt and IV
		byte[] buffer = new byte[saltBytes.length + ivBytes.length + encryptedTextBytes.length];
		System.arraycopy(saltBytes, 0, buffer, 0, saltBytes.length);
		System.arraycopy(ivBytes, 0, buffer, saltBytes.length, ivBytes.length);
		System.arraycopy(encryptedTextBytes, 0, buffer, saltBytes.length + ivBytes.length, encryptedTextBytes.length);
		return new Base64().encodeToString(buffer);

	}

	public static String decrypt(String str, String password) throws InvalidKeySpecException, NoSuchAlgorithmException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
			NoSuchPaddingException, UnsupportedEncodingException
	{
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		// strip off the salt and IV
		ByteBuffer buffer = ByteBuffer.wrap(new Base64().decode(str));
		byte[] saltBytes = new byte[SALT_LENGTH];
		buffer.get(saltBytes, 0, saltBytes.length);
		byte[] ivBytes = new byte[cipher.getBlockSize()];
		buffer.get(ivBytes, 0, ivBytes.length);
		byte[] encryptedTextBytes = new byte[buffer.capacity() - saltBytes.length - ivBytes.length];
		buffer.get(encryptedTextBytes);

		// Derive the key
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, 65536, 256);

		SecretKey secretKey = factory.generateSecret(spec);
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
		cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));

		byte[] decryptedTextBytes = null;
		decryptedTextBytes = cipher.doFinal(encryptedTextBytes);

		return new String(decryptedTextBytes, STANDARD_CHARSET);
	}

	public static String getSHA512Hash(String passwordToHash) throws NoSuchAlgorithmException
	{
		String generatedPassword = null;
		MessageDigest md = MessageDigest.getInstance("SHA-512");
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
