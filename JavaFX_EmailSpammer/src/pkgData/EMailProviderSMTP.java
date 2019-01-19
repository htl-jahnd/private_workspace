/**
 * 
 */
package pkgData;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author david.jahn
 *
 */
public enum EMailProviderSMTP
{

	Google, Yahoo, Hotmail, GMX, AOL;

	public static String getSmtpHostString(EMailProviderSMTP provider) throws Exception
	{
		switch (provider)
		{
			case Google:
				return "smtp.gmail.com"; //$NON-NLS-1$
			case Yahoo:
				return "smtp.mail.yahoo.com"; //$NON-NLS-1$
			case Hotmail:
				return "smtp-mail.outlook.com"; //$NON-NLS-1$
			case GMX:
				return "mail.gmx.com"; //$NON-NLS-1$
			case AOL:
				return "smtp.aol.com"; //$NON-NLS-1$
			default:
				throw new Exception(Messages.getString("EMailProviderSMTP.GetStringStatic.ExceptionText")); //$NON-NLS-1$
		}
	}

	public String getSmtpHostString() throws Exception
	{
		switch (this)
		{
			case Google:
				return "smtp.gmail.com"; //$NON-NLS-1$
			case Yahoo:
				return "smtp.mail.yahoo.com"; //$NON-NLS-1$
			case Hotmail:
				return "smtp-mail.outlook.com"; //$NON-NLS-1$
			case GMX:
				return "mail.gmx.com"; //$NON-NLS-1$
			case AOL:
				return "smtp.aol.com"; //$NON-NLS-1$
			default:
				throw new Exception(Messages.getString("EMailProviderSMTP.GetString.ExceptionText")); //$NON-NLS-1$
		}
	}

	public static EMailProviderSMTP getProviderOfString(String s) throws Exception
	{
		switch (s)
		{
			case "smtp.gmail.com": //$NON-NLS-1$
				return Google;
			case "smtp.mail.yahoo.com": //$NON-NLS-1$
				return Yahoo;
			case "smtp-mail.outlook.com": //$NON-NLS-1$
				return Hotmail;
			case "mail.gmx.com": //$NON-NLS-1$
				return GMX;
			case "smtp.aol.com": //$NON-NLS-1$
				return AOL;
			default:
				throw new Exception(Messages.getString("EMailProviderSMTP.GetProviderStatic.ExceptionText")); //$NON-NLS-1$
		}
	}

	public static BufferedImage getIcon(EMailProviderSMTP item) throws Exception
	{
		switch (item)
		{
			case Google:
				return ImageIO.read(EMailProviderSMTP.class.getResourceAsStream("/pkgMain/ressources/images/gmail.png")); //$NON-NLS-1$
			case Yahoo:
				return ImageIO.read(EMailProviderSMTP.class.getResourceAsStream("/pkgMain/ressources/images/ymail.png")); //$NON-NLS-1$
			case GMX:
				return ImageIO.read(EMailProviderSMTP.class.getResourceAsStream("/pkgMain/ressources/images/gmx.png")); //$NON-NLS-1$
			case Hotmail:
				return ImageIO
						.read(EMailProviderSMTP.class.getResourceAsStream("/pkgMain/ressources/images/hotmail.png")); //$NON-NLS-1$
			case AOL:
				return ImageIO.read(EMailProviderSMTP.class.getResourceAsStream("/pkgMain/ressources/images/aol.png")); //$NON-NLS-1$
			default:
				throw new Exception(Messages.getString("EMailProviderSMTP.GetIcon.ExceptionText")); //$NON-NLS-1$
		}
	}

}
