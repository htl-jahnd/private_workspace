package pkgData;

import java.util.prefs.Preferences;

import pkgMisc.PasswordUtils;

public class Database
{

	private static Database INSTANCE = null;
	private MailAccount account;
	private static final String MAIL_PROPERTY = "mailAddress"; //$NON-NLS-1$
	private static final String PASSWORD_PROPERTY = "mailPassword"; //$NON-NLS-1$
	private static final String SMTP_PROPERTY = "smtpServer"; //$NON-NLS-1$
	private static final String DARK_PROPERTY = "darkMode"; //$NON-NLS-1$
	private static final String LANGUAGE_PROPERTY = "language"; //$NON-NLS-1$
	private static final String MAIL_FROM_NAME = "mailFromName"; //$NON-NLS-1$
	private Preferences preferences;
	private boolean darkMode = false;
	private ELanguage language = ELanguage.English;

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
		String mail = preferences.get(MAIL_PROPERTY, "email.address@example.com"); //$NON-NLS-1$
		String pwd = preferences.get(PASSWORD_PROPERTY, "password"); //$NON-NLS-1$
		try
		{
			account.setPassword(PasswordUtils.decrypt(pwd, PasswordUtils.getSHA512Hash(mail)));
		} catch (Exception ex)
		{
			account = new MailAccount();
			throw new Exception(Messages.getString("Database.ReadPrefs.ExceptionText")); //$NON-NLS-1$
		}
		account.setAddress(mail);
		account.setProvider(EMailProviderSMTP.getProviderOfString(preferences.get(SMTP_PROPERTY, "smtp.gmail.com"))); //$NON-NLS-1$
		account.setName(preferences.get(MAIL_FROM_NAME, "Example Name")); //$NON-NLS-1$
		this.setDarkMode(preferences.getBoolean(DARK_PROPERTY, false));
		String lan = preferences.get(LANGUAGE_PROPERTY, ELanguage.English.getString());
		this.setLanguage(ELanguage.getLanguage(lan));
	}

	public boolean isDarkMode()
	{
		return darkMode;
	}

	public void setDarkMode(boolean darkMode)
	{
		this.darkMode = darkMode;
	}

	public ELanguage getLanguage()
	{
		return language;
	}

	public void setLanguage(ELanguage language)
	{
		this.language = language;
	}

}
