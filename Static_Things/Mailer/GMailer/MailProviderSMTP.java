/**
 * 
 */
package GMailer;

/**
 * @author david.jahn
 *
 */
public enum MailProviderSMTP
{

	Google, Yahoo, Hotmail, GMX, AOL;

	public static String getSmtpHostString(MailProviderSMTP provider) throws Exception
	{
		switch (provider)
		{
			case Google:
				return "smtp.gmail.com";
			case Yahoo:
				return "smtp.mail.yahoo.com";
			case Hotmail:
				return "smtp-mail.outlook.com";
			case GMX:
				return "mail.gmx.com";
			case AOL:
				return "smtp.aol.com";
			default:
				throw new Exception("Mail provider not supported.");
		}
	}

	public String getSmtpHostString() throws Exception
	{
		switch (this)
		{
			case Google:
				return "smtp.gmail.com";
			case Yahoo:
				return "smtp.mail.yahoo.com";
			case Hotmail:
				return "smtp-mail.outlook.com";
			case GMX:
				return "mail.gmx.com";
			case AOL:
				return "smtp.aol.com";
			default:
				throw new Exception("Mail provider not supported.");
		}
	}

	public static MailProviderSMTP getProviderOfString(String s) throws Exception
	{
		switch(s) {
			case "smtp.gmail.com":
				return Google;
			case "smtp.mail.yahoo.com":
				return Yahoo;
			case "smtp-mail.outlook.com":
				return Hotmail;
			case "mail.gmx.com":
				return GMX;
			case "smtp.aol.com":
				return AOL;
			default:
				throw new Exception("SMTP string not supported.");
		}
	}

}
