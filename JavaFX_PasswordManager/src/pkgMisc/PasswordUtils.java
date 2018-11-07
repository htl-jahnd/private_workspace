package pkgMisc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Optional;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class PasswordUtils
{
    private static byte[] salt;
    private static final int SALT_LEN = 8;
    private static byte[] ivspec_bytes;

    public static byte[] encrypt(byte[] plainText, SecretKey secret) throws Exception
    {
	final SecureRandom rng = new SecureRandom();
	final byte[] ciphertext;
	final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        final Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
        final IvParameterSpec ivForCBC = createIV(aesCBC.getBlockSize(), Optional.of(rng));
        aesCBC.init(Cipher.ENCRYPT_MODE, secret, ivForCBC);

        baos.write(ivForCBC.getIV());
        try (final CipherOutputStream cos = new CipherOutputStream(baos, aesCBC)) {
            cos.write(plainText);
        }

        ciphertext = baos.toByteArray();
        return ciphertext;
    }

    public static String decrypt(byte[] encryptedText, SecretKey secret) throws Exception
    {
	final byte[] decrypted;
	final ByteArrayInputStream bais = new ByteArrayInputStream(encryptedText);

        final Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
        final IvParameterSpec ivForCBC = readIV(aesCBC.getBlockSize(), bais);
        aesCBC.init(Cipher.DECRYPT_MODE, secret, ivForCBC);

        final byte[] buf = new byte[1_024];
        try (final CipherInputStream cis = new CipherInputStream(bais, aesCBC);
                final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int read;
            while ((read = cis.read(buf)) != -1) {
                baos.write(buf, 0, read);
            }
            decrypted = baos.toByteArray();
        }
        return new String(decrypted,StandardCharsets.UTF_8);
    }
    
    public static IvParameterSpec readIV(final int ivSizeBytes, final InputStream is) throws IOException {
        final byte[] iv = new byte[ivSizeBytes];
        int offset = 0;
        while (offset < ivSizeBytes) {
            final int read = is.read(iv, offset, ivSizeBytes - offset);
            if (read == -1) {
                throw new IOException("Too few bytes for IV in input stream");
            }
            offset += read;
        }
        return new IvParameterSpec(iv);
    }

    public static SecretKey generateKey(char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
	/* Derive the key, given password and salt. */
	SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
	SecretKey tmp = factory.generateSecret(spec);
	return new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    public static void generateSalt()
    {
	final Random r = new SecureRandom();
	salt = new byte[SALT_LEN];
	r.nextBytes(salt);
    }

    public static IvParameterSpec createIV(final int ivSizeBytes, final Optional<SecureRandom> rng) {
        final byte[] iv = new byte[ivSizeBytes];
        final SecureRandom theRNG = rng.orElse(new SecureRandom());
        theRNG.nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static byte[] getSalt()
    {
	return salt;
    }

    public static void setSalt(byte[] salt)
    {
	PasswordUtils.salt = salt;
    }

    public static byte[] getIvspec()
    {
	return ivspec_bytes;
    }

    public static void setIvspec(byte[] ivspec)
    {
	PasswordUtils.ivspec_bytes = ivspec;
    }

}
