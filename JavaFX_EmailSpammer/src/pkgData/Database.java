package pkgData;

import java.util.prefs.Preferences;

import pkgController.MailAccount;
import pkgMisc.PasswordUtils;

public class Database
{

	private static Database INSTANCE = null;
	private MailAccount account;
	private static final String MAIL_PROPERTY="mailAddress";
	private static final String PASSWORD_PROPERTY="mailPassword";
	private static final String SMTP_PROPERTY="smtpServer";
	private static final String DARK_PROPERTY="darkMode";
	private static final String LANGUAGE_PROPERTY="language";
	private Preferences preferences;
	private boolean darkMode=false;
	private String language="English";
	
	public static Database newInstance()
	{
		if (INSTANCE == null)
			INSTANCE = new Database();
		return INSTANCE;
	}

	private Database()
	{
		account= new MailAccount();
	}

	public MailAccount getAccount()
	{
		return account;
	}

	public void setAccount(MailAccount account)
	{
		this.account = account;
	}

	public void writePreferences() throws Exception
	{
		String encPwd = PasswordUtils.encrypt(account.getPassword(), PasswordUtils.getSHA512Hash(account.getAddress()));
		preferences = Preferences.userNodeForPackage(getClass());
		preferences.put(MAIL_PROPERTY, account.getAddress());
		preferences.put(PASSWORD_PROPERTY, encPwd);
		preferences.put(SMTP_PROPERTY, account.getProvider().getSmtpHostString());
		preferences.putBoolean(DARK_PROPERTY, darkMode);
		preferences.put(LANGUAGE_PROPERTY, language);
	}
	
	public void readPreferences() throws Exception {
		preferences = Preferences.userNodeForPackage(getClass());
		String mail = preferences.get(MAIL_PROPERTY, "email.address@example.com");
		String pwd = preferences.get(PASSWORD_PROPERTY, "password");
		try {
			account.setPassword(PasswordUtils.decrypt(pwd, PasswordUtils.getSHA512Hash(mail)));
		}catch(Exception ex){
			throw new Exception("Something bad happen while restoring password/email-address");
		}
		account.setAddress(mail);
		account.setProvider(MailProviderSMTP.getProviderOfString(preferences.get(SMTP_PROPERTY, "smtp.gmail.com")));
		this.setDarkMode(preferences.getBoolean(DARK_PROPERTY, false));
		this.setLanguage(preferences.get(LANGUAGE_PROPERTY, "Deutsch"));
	}

	public boolean isDarkMode()
	{
		return darkMode;
	}

	public void setDarkMode(boolean darkMode)
	{
		this.darkMode = darkMode;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}

}
