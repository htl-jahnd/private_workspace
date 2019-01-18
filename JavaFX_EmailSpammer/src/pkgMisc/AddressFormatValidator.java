package pkgMisc;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.apache.commons.validator.routines.UrlValidator;

public class AddressFormatValidator
{
	private static final String[] URL_SCHEMES = { "http", "https" };

	public static boolean isValidateIPAddress(String ipAddress)
	{
		return InetAddressValidator.getInstance().isValidInet4Address(ipAddress);
	}

	public static boolean isValidEmailAddress(String email)
	{
		return EmailValidator.getInstance().isValid(email);
	}

	public static boolean isValidUrl(String url)
	{
		UrlValidator urlValidator = new UrlValidator(URL_SCHEMES);
		return urlValidator.isValid(url);
	}
}