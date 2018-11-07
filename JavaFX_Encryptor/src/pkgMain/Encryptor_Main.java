package pkgMain;

import java.io.File;
import pkgData.Database;
import pkgMisc.TextUtils;

public class Encryptor_Main
{

	public static void main(String[] args) throws Exception
	{
		Database db = Database.newInstance();
		// String test = "12345678a123";
		// String encrypt = db.doEncrypt(test, "Bar12345Bar12345".toCharArray());
		// System.out.println(encrypt);
		// String decrypt= db.doDecrypt(encrypt, "Bar12345Bar12345".toCharArray());
		//
		// System.out.println(decrypt);

		File f = new File("/Users/david.jahn/Desktop/Test/normal.txt");
		String everything = TextUtils.readText(f);
		String enc = db.doEncrypt(everything, "Hello World".toCharArray());
		File f1 = new File("/Users/david.jahn/Desktop/Test/normal.enc");
		f1.createNewFile();
		TextUtils.writeText(f1, enc);

		f = new File("/Users/david.jahn/Desktop/Test/normal.enc");
		everything = TextUtils.readText(f);
		String dec = db.doDecrypt(everything, "Hello World".toCharArray());
		f1 = new File("/Users/david.jahn/Desktop/Test/normal1.txt");
		f1.createNewFile();
		TextUtils.writeText(f1, dec);
	}

}
