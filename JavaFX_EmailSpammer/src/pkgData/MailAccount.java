package pkgData;

public class MailAccount
{
	private String address;
	private String password;
	private MailProviderSMTP provider;
	private String name;

	public MailAccount(String address, String password, MailProviderSMTP provider, String name)
	{
		super();
		this.address = address;
		this.password = password;
		this.provider = provider;
		this.name = name;
	}

	public MailAccount()
	{
		this("email.address@example.com", "password", MailProviderSMTP.Google, "Example Name");
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

	public MailProviderSMTP getProvider()
	{
		return provider;
	}

	public void setProvider(MailProviderSMTP provider)
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
