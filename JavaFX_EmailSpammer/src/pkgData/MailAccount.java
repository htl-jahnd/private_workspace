package pkgData;

public class MailAccount
{
	private String address;
	private String password;
	private EMailProviderSMTP provider;
	private String name;

	public MailAccount(String address, String password, EMailProviderSMTP provider, String name)
	{
		super();
		this.address = address;
		this.password = password;
		this.provider = provider;
		this.name = name;
	}

	public MailAccount()
	{
		this(Messages.getString("MailAccount.Constructor.DefaultSenderAddress"), Messages.getString("MailAccount.Constructor.DefaultPassword"), EMailProviderSMTP.Google, Messages.getString("MailAccount.Constructor.DefaultSenderName")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public EMailProviderSMTP getProvider()
	{
		return provider;
	}

	public void setProvider(EMailProviderSMTP provider)
	{
		this.provider = provider;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
