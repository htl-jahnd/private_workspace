package pkgData;

public enum ECreditCardsProviders
{
	Visa, MasterCard, AmericanExpress, DinersClub;

	public static ECreditCardsProviders getProvider(String s)
	{
		switch (s)
		{
			case "Visa":
				return Visa;
			case "MasterCard":
				return MasterCard;
			case "AmericanExpress":
				return AmericanExpress;
			case "DinersClub":
				return DinersClub;
		}
		return null;
	}

	public static String getProviderString(ECreditCardsProviders e)
	{
		switch (e)
		{
			case Visa:
				return "Visa";
			case MasterCard:
				return "MasterCard";
			case AmericanExpress:
				return "AmericanExpress";
			case DinersClub:
				return "DinersClub";
		}
		return null;
	}
}
