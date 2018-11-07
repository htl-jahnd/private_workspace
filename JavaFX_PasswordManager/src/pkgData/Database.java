package pkgData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.YearMonth;
import java.util.ArrayList;

import javax.crypto.SecretKey;
import org.apache.commons.io.IOUtils;

import pkgMisc.PasswordUtils;

public class Database
{
    private static Database instance;
    private static final String DATA_FILE_PATH = "info.txt";
    private static final String SALT_FILE_PATH = "sata.txt";
    private static final String ELEMENT_SEPARATOR = "≤";
    private static final String LINE_SEPARATOR = "°";
    private static final String CATEGORY_SEPARATOR = "±";
    private static final String LOG_FILE_PATH = "./errors.log";
    private static final ArrayList<WebAccount> accounts = new ArrayList<WebAccount>();
    private static final ArrayList<ProgramLicense> licenses = new ArrayList<ProgramLicense>();
    private static final ArrayList<CreditCard> creditCards = new ArrayList<CreditCard>();

    // STORING

    public void doStore(char[] key) throws Exception
    {
	File fileToWrite = new File(DATA_FILE_PATH);
	if (!fileToWrite.exists())
	    fileToWrite.createNewFile();
	PasswordUtils.generateSalt();
	doWriteSalt();
	SecretKey skey = PasswordUtils.generateKey(key);
	key = "000000".toCharArray();
	StringBuilder strB = new StringBuilder();

	strB.append(doStoreWebaccounts());
	strB.append(CATEGORY_SEPARATOR);
	strB.append(doStoreLicenses());
	strB.append(CATEGORY_SEPARATOR);
	strB.append(doStoreCreditCards());

	byte[] text = PasswordUtils.encrypt(strB.toString().getBytes(), skey);
	IOUtils.write(text, new FileOutputStream(fileToWrite));
    }

    private String doStoreWebaccounts() throws Exception
    {
	int len = accounts.size();
	StringBuilder strB = new StringBuilder();
	for (int i = 0; i < len; i++)
	{
	    WebAccount tmp = accounts.get(i);
	    String accN = tmp.getWebsiteName();
	    String url = tmp.getWebsiteURL().toString();
	    String usrN = tmp.getUsername();
	    String pwd = tmp.getPassword();
	    String addI = tmp.getAdditionalInformation();
	    strB.append(accN).append(ELEMENT_SEPARATOR);
	    strB.append(url).append(ELEMENT_SEPARATOR);
	    strB.append(usrN).append(ELEMENT_SEPARATOR);
	    strB.append(pwd).append(ELEMENT_SEPARATOR);
	    strB.append(addI);
	    if (i != len - 1)
		strB.append(LINE_SEPARATOR);
	}
	return strB.toString();
    }

    private String doStoreLicenses() throws Exception
    {
	int len = licenses.size();
	StringBuilder strB = new StringBuilder();
	for (int i = 0; i < len; i++)
	{
	    ProgramLicense tmp = licenses.get(i);
	    String programmN = tmp.getProgramName();
	    String lic = tmp.getLicense();
	    String addI = tmp.getAdditionalInformation();
	    strB.append(programmN).append(ELEMENT_SEPARATOR);
	    strB.append(lic).append(ELEMENT_SEPARATOR);
	    strB.append(addI);
	    if (i != len - 1)
		strB.append(LINE_SEPARATOR);
	}
	return strB.toString();
    }

    private String doStoreCreditCards() throws Exception
    {
	int len = creditCards.size();
	StringBuilder strB = new StringBuilder();

	for (int i = 0; i < len; i++)
	{
	    CreditCard tmp = creditCards.get(i);
	    String cardN = tmp.getCardName();
	    String ownerN = tmp.getOwnerName();
	    String cardNumber = tmp.getCardNumber();
	    String bankN = tmp.getBankName();
	    String validationDate = tmp.getValidationDate().toString();
	    String provider = tmp.getProvider().toString();
	    int cvv = tmp.getSecurityCode();
	    String addI = tmp.getAdditionalInformation();
	    strB.append(cardN).append(ELEMENT_SEPARATOR);
	    strB.append(cardNumber).append(ELEMENT_SEPARATOR);
	    strB.append(ownerN).append(ELEMENT_SEPARATOR);
	    strB.append(validationDate).append(ELEMENT_SEPARATOR);
	    strB.append(provider).append(ELEMENT_SEPARATOR);
	    strB.append(addI);
	    strB.append(bankN).append(ELEMENT_SEPARATOR);
	    strB.append(cvv).append(ELEMENT_SEPARATOR);

	    if (i != len - 1)
		strB.append(LINE_SEPARATOR);
	}
	return strB.toString();
    }

    public void doWriteSalt() throws FileNotFoundException, IOException
    {
	File fileToWrite = new File(SALT_FILE_PATH);
	if (!fileToWrite.exists())
	    fileToWrite.createNewFile();
	fileToWrite.setWritable(true);
	IOUtils.write(PasswordUtils.getSalt(), new FileOutputStream(fileToWrite));
	fileToWrite.setReadOnly();
    }

    // RESTORING

    public void doRestore(char[] key) throws Exception
    {
	File fileToRead = new File(DATA_FILE_PATH);
	if (!fileToRead.exists())
	    fileToRead.createNewFile();
	doReadSalt();
	SecretKey skey = PasswordUtils.generateKey(key);
	String text = PasswordUtils.decrypt(IOUtils.toByteArray(new FileInputStream(fileToRead)), skey);
	String[] categories = text.split(CATEGORY_SEPARATOR);
	doRestoreWebaccounts(categories[0]);
	doRestoreLicenses(categories[1]);
	doRestoreCreditCards(categories[2]);
    }

    private void doRestoreLicenses(String text)
    {
	ArrayList<ProgramLicense> ret = new ArrayList<ProgramLicense>();
	String[] lines = text.split(LINE_SEPARATOR);
	for (int i = 0; i < lines.length; i++)
	{
	    String[] attributes = lines[i].split(ELEMENT_SEPARATOR);
	    ProgramLicense c = new ProgramLicense(attributes[0], attributes[1], attributes[2]);
	    ret.add(c);
	}
	licenses.addAll(ret);
    }

    private void doRestoreCreditCards(String text) throws FileNotFoundException, IOException, Exception
    {
	ArrayList<CreditCard> ret = new ArrayList<CreditCard>();
	String[] lines = text.split(LINE_SEPARATOR);
	for (int i = 0; i < lines.length; i++)
	{
	    String[] attributes = lines[i].split(ELEMENT_SEPARATOR);
	    CreditCard c = new CreditCard(attributes[0], attributes[1], attributes[2], YearMonth.parse(attributes[3]),
		    ECreditCardsProviders.getProvider(attributes[4]), attributes[5], attributes[6],
		    Integer.valueOf(attributes[7]));
	    ret.add(c);
	}
	creditCards.addAll(ret);
    }

    private void doRestoreWebaccounts(String text) throws FileNotFoundException, IOException, Exception
    {
	ArrayList<WebAccount> ret = new ArrayList<WebAccount>();
	String[] lines = text.split(LINE_SEPARATOR);
	for (int i = 0; i < lines.length; i++)
	{
	    String[] attributes = lines[i].split(ELEMENT_SEPARATOR);
	    WebAccount c = new WebAccount(attributes[0], new URL(attributes[1]), attributes[2], attributes[3],
		    attributes[4]);
	    ret.add(c);
	}
	accounts.addAll(ret);
    }

    public void doReadSalt() throws FileNotFoundException, IOException
    {
	File fileToRead = new File(SALT_FILE_PATH);
	if (!fileToRead.exists())
	    throw new FileNotFoundException("ERROR: Salt file not found");
	PasswordUtils.setSalt(IOUtils.toByteArray(new FileInputStream(fileToRead)));
	fileToRead.setReadOnly();
    }

    // ELSE
    public static Database newInstance()
    {
	if (instance == null)
	{
	    instance = new Database();
	}
	return instance;
    }

    private Database()
    {
    }

    public static String getLogFilePath()
    {
	return LOG_FILE_PATH;
    }

    public static ArrayList<WebAccount> getAccounts()
    {
	return accounts;
    }

    public static ArrayList<ProgramLicense> getLicenses()
    {
	return licenses;
    }

    public static ArrayList<CreditCard> getCreditcards()
    {
	return creditCards;
    }

}
