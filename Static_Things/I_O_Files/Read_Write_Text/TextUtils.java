package Read_Write_Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextUtils
{

	public static String readText(File fileToRead) throws FileNotFoundException, IOException
	{
		String everything = null;
		try (BufferedReader br = new BufferedReader(new FileReader(fileToRead)))
		{
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null)
			{
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			everything = sb.toString();
		}
		return everything;
	}

	public static String readText(String fileName) throws FileNotFoundException, IOException
	{
		String everything = null;
		try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
		{
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null)
			{
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			everything = sb.toString();
		}
		return everything;
	}

	public static void writeText(File fileToWrite, String textToWrite) throws IOException
	{
		BufferedWriter writer = null;
		writer = new BufferedWriter(new FileWriter(fileToWrite));
		try
		{
			writer.write(textToWrite);
		} finally
		{
			writer.close();
		}
	}

	public static void writeText(String fileToWrite, String textToWrite) throws IOException
	{
		BufferedWriter writer = null;
		writer = new BufferedWriter(new FileWriter(fileToWrite));
		try
		{
			writer.write(textToWrite);
		} finally
		{
			writer.close();
		}
	}

}
