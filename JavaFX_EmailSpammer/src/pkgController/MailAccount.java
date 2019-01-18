package pkgController;

import pkgData.MailProviderSMTP;

public class MailAccount
{
	private String address;
	private String password;
	private MailProviderSMTP provider;

	public MailAccount(String address, String password, MailProviderSMTP provider)
	{
		super();
		this.address = address;
		this.password = password;
		this.provider = provider;
	}

	public MailAccount()
	{
		this("email.address@example.com", "password", MailProviderSMTP.Google);
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

}
