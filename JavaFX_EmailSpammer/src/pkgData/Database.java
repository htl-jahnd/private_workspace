package pkgData;

import java.util.prefs.Preferences;

import pkgMisc.PasswordUtils;

public class Database
{

	private static Database INSTANCE = null;
	private MailAccount account;
	private static final String MAIL_PROPERTY = "mailAddress";
	private static final String PASSWORD_PROPERTY = "mailPassword";
	private static final String SMTP_PROPERTY = "smtpServer";
	private static final String DARK_PROPERTY = "darkMode";
	private static final String LANGUAGE_PROPERTY = "language";
	private static final String MAIL_FROM_NAME = "mailFromName";
	private Preferences preferences;
	private boolean darkMode = false;
	private ELanguages language = ELanguages.English;

	public static Database newInstance()
	{
		if (INSTANCE == null)
			INSTANCE = new Database();
		return INSTANCE;
	}

	private Database()
	{
		account = new MailAccount();
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
		preferences.put(LANGUAGE_PROPERTY, language.getString());
		preferences.put(MAIL_FROM_NAME, account.getName());
	}

	public void readPreferences() throws Exception
	{
		preferences = Preferences.userNodeForPackage(getClass());
		String mail = preferences.get(MAIL_PROPERTY, "email.address@example.com");
		String pwd = preferences.get(PASSWORD_PROPERTY, "password");
		try
		{
			account.setPassword(PasswordUtils.decrypt(pwd, PasswordUtils.getSHA512Hash(mail)));
		} catch (Exception ex)
		{
			account = new MailAccount();
			throw new Exception("Something bad happen while restoring password/email-address");
		}
		account.setAddress(mail);
		account.setProvider(MailProviderSMTP.getProviderOfString(preferences.get(SMTP_PROPERTY, "smtp.gmail.com")));
		account.setName(preferences.get(MAIL_FROM_NAME, "Example Name"));
		this.setDarkMode(preferences.getBoolean(DARK_PROPERTY, false));
		String lan = preferences.get(LANGUAGE_PROPERTY, ELanguages.English.getString());
		this.setLanguage(ELanguages.getLanguage(lan));
	}

	public boolean isDarkMode()
	{
		return darkMode;
	}

	public void setDarkMode(boolean darkMode)
	{
		this.darkMode = darkMode;
	}

	public ELanguages getLanguage()
	{
		return language;
	}

	public void setLanguage(ELanguages language)
	{
		this.language = language;
	}

}
