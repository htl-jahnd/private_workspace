package pkgMain_366;

public class Main_366 // NO_UCD (unused code)
{
	// https://www.reddit.com/r/dailyprogrammer/comments/98ufvz/20180820_challenge_366_easy_word_funnel_1/
	// TODO bonus

	public static void main(String[] args)
	{
		System.out.println(funnel("leave", "lave"));
	}

	private static Boolean funnel(String word1, String word2)
	{
		Boolean ret = false;
		for (int i = 0; i < word1.length(); i++)
		{
			if (removeChar(word1, i).equals(word2))
				ret = true;
		}
		return ret;

	}

	private static String removeChar(String str, Integer n)
	{
		String front = str.substring(0, n);
		String back = str.substring(n + 1, str.length());
		return front + back;
	}

}
