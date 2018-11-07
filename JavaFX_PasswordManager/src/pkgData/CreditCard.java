package pkgData;

import java.time.YearMonth;

import pkgExceptions.InvalidCVVCodeException;

public class CreditCard extends Card
{

    private String cardNumber;
    private ECreditCardsProviders provider;
    private Integer securityCode;

    public CreditCard(String cardName, String cardNumber, String cardOwner, YearMonth validationDate,
	    ECreditCardsProviders provider, String additionalInformation, String bankName,
	    Integer securityCode)
    {
	super(cardName, cardOwner, bankName, validationDate, additionalInformation);
	this.cardNumber = cardNumber;
	this.provider = provider;
	this.securityCode = securityCode;
    }


    public String getCardNumber()
    {
	return cardNumber;
    }

    public void setCardNumber(String cardNumber)
    {
	this.cardNumber = cardNumber;
    }

    public ECreditCardsProviders getProvider()
    {
	return provider;
    }

    public void setProvider(ECreditCardsProviders provider)
    {
	this.provider = provider;
    }


    public int getSecurityCode()
    {
	return securityCode;
    }

    public void setSecurityCode(Integer securityCode) throws InvalidCVVCodeException
    {
	if(String.valueOf(securityCode).length()!=3)
	    throw new InvalidCVVCodeException("ERROR: The security code has to be three digits long");
	this.securityCode = securityCode;
    }

}
