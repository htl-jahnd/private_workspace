package pkgData;

import java.net.URL;

public class WebAccount
{

	private String websiteName;
	private URL websiteURL;
	private String username;
	private String password;
	private String additionalInformation;

	public WebAccount(String websiteName, URL websiteURL, String username, String password,
			String additionalInformation)
	{
		super();
		this.websiteName = websiteName;
		this.websiteURL = websiteURL;
		this.username = username;
		this.password = password;
		this.additionalInformation = additionalInformation;
	}

	@Override
	public String toString()
	{
		return "WebAccount [websiteName=" + websiteName + ", websiteURL=" + websiteURL + ", username=" + username
				+ ", password=" + password + ", additionalInformation=" + additionalInformation + "]";
	}

	public String getWebsiteName()
	{
		return websiteName;
	}

	public void setWebsiteName(String accountName)
	{
		this.websiteName = accountName;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getAdditionalInformation()
	{
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation)
	{
		this.additionalInformation = additionalInformation;
	}

	public URL getWebsiteURL()
	{
		return websiteURL;
	}

	public void setWebsiteURL(URL websiteURL)
	{
		this.websiteURL = websiteURL;
	}

}
