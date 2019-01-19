package pkgData;

public enum ELanguages
{
	English;
	
	public static String getString(ELanguages lang) throws Exception {
		switch(lang) {
			case English:
				return "English";
			default:
				throw new Exception("Language not supported");
		}
	}
	
	public String getString() throws Exception {
		switch(this) {
			case English:
				return "English";
			default:
				throw new Exception("Language not supported");
		}
	}

	public static ELanguages getLanguage(String lan) throws Exception
	{
		switch(lan) {
			case "English":
				return English;
			default:
				throw new Exception("Language not supported");
		}
	}
}
