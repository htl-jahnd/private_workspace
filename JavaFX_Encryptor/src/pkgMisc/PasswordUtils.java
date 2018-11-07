package pkgMisc;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class PasswordUtils
{

    public static String encrypt(byte[] plainText, SecretKey secret) throws Exception
    {
    	// encrypt the text
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] encrypted = cipher.doFinal(plainText);
        return Base64.getMimeEncoder().withoutPadding().encodeToString(encrypted);
    }

    public static String decrypt(byte[] encryptedText, SecretKey secret) throws Exception
    {
    	// decrypt the text
    	Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secret);
        byte[] decordedValue = Base64.getMimeDecoder().decode(encryptedText);
        byte[] decValue = cipher.doFinal(decordedValue);
        return new String(decValue);
    }
    
    public static SecretKey generateKey(char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException
    {
	/* Derive the key, given password and salt. */
    	byte[] key = new String(password).getBytes("UTF-8");
    	MessageDigest sha = MessageDigest.getInstance("SHA-1");
    	key = sha.digest(key);
    	key = Arrays.copyOf(key, 16); // use only first 128 bit
    	SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
    	return secretKeySpec;
    }
    
    


    

}
