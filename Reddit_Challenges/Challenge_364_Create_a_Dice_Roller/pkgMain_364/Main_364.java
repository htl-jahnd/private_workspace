package pkgMain_364;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Main_364 // NO_UCD (unused code)
{
	// https://www.reddit.com/r/dailyprogrammer/comments/8s0cy1/20180618_challenge_364_easy_create_a_dice_roller/

	private static final int MAX_RANGE = 100;
	private static final int MAX_TIMES = 100;
	private static final int MIN_RANGE = 2;
	private static final int MIN_TIMES = 1;

	public static void main(String[] args) throws Exception
	{
		ArrayList<String> tests = createTestCases(5, 5, 5);
		try
		{
			for (String s : tests)
			{
				System.out.println("Rolled Dice: " + rollDice(s));
				System.out.println();
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private static ArrayList<String> createTestCases(int times, int max_times, int max_range) throws Exception
	{
		if (times < 1 || times > 50)
			throw new Exception("Invalid times");
		int t;
		int r;
		String s;
		ArrayList<String> tests = new ArrayList<String>();
		for (int i = 0; i < times; i++)
		{
			t = ThreadLocalRandom.current().nextInt(MIN_TIMES, +max_times + 1);
			r = ThreadLocalRandom.current().nextInt(MIN_RANGE, +max_range + 1);
			s = t + "d" + r;
			tests.add(s);
		}
		return tests;
	}

	private static BigInteger rollDice(String s) throws Exception
	{
		String[] sArr = s.split("d");
		int times = Integer.parseInt(sArr[0]);
		int maxRange = Integer.parseInt(sArr[1]);
		if (times < MIN_TIMES && times > MAX_TIMES || maxRange < MIN_RANGE && maxRange > MAX_RANGE)
			throw new Exception("Invalid times");

		int[] rolled = new int[times];
		BigInteger sum = new BigInteger("0");
		int y;
		for (int i = 0; i < times; i++)
		{
			y = ThreadLocalRandom.current().nextInt(2, maxRange + 1);
			rolled[i] = y;
			sum = sum.add(BigInteger.valueOf(y));
		}
		printRolled(sum, rolled);
		return sum;
	}

	private static void printRolled(BigInteger sum, int[] rolled)
	{
		StringBuilder builder = new StringBuilder("Sum: ").append(sum).append("; Rolled: ");
		for (int i = 0; i < rolled.length; i++)
		{
			if (i != rolled.length - 1)
				builder.append(rolled[i]).append(", ");
			else
				builder.append(rolled[i]);
		}
		System.out.println(builder);

	}

}
