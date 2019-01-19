package pkgData;

import java.awt.image.BufferedImage;
import java.util.Locale;

import javax.imageio.ImageIO;

public enum ELanguage
{
	English, Deutsch;

	public static String getString(ELanguage lang) throws Exception
	{
		switch (lang)
		{
			case English:
				return "English"; //$NON-NLS-1$
			case Deutsch:
				return "Deutsch"; //$NON-NLS-1$
			default:
				throw new Exception(Messages.getString("ELanguage.GetStringStatic.ExceptionText")); //$NON-NLS-1$
		}
	}

	public String getString() throws Exception
	{
		switch (this)
		{
			case English:
				return "English"; //$NON-NLS-1$
			case Deutsch:
				return "Deutsch"; //$NON-NLS-1$
			default:
				throw new Exception(Messages.getString("ELanguage.GetString.ExceptionText")); //$NON-NLS-1$
		}
	}

	public static ELanguage getLanguage(String lan) throws Exception
	{
		switch (lan)
		{
			case "English": //$NON-NLS-1$
				return English;
			case "Deutsch": //$NON-NLS-1$
				return Deutsch;
			default:
				throw new Exception(Messages.getString("ELanguage.GetLanguageStatic.ExcptionText")); //$NON-NLS-1$
		}
	}

	public static BufferedImage getIcon(ELanguage item) throws Exception
	{
		switch (item)
		{
			case English:
				return ImageIO.read(ELanguage.class.getResourceAsStream("/pkgMain/ressources/images/eng.png")); //$NON-NLS-1$
			case Deutsch:
				return ImageIO.read(ELanguage.class.getResourceAsStream("/pkgMain/ressources/images/ger.png")); //$NON-NLS-1$
			default:
				throw new Exception(Messages.getString("ELanguage.GetLanguage.ExceptionText")); //$NON-NLS-1$
		}
	}

	public Locale getLocale()
	{
		switch (this)
		{
			case English:
				return new Locale("en", "US"); //$NON-NLS-1$ //$NON-NLS-2$
			case Deutsch:
				return new Locale("de", "AT"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return Locale.getDefault();
	}
}
