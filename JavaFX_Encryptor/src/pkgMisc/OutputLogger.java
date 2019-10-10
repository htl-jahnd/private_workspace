package pkgMisc;

import java.time.LocalDateTime;

public class OutputLogger
{
	public static void Log(String msg)
	{
		System.out.println(LocalDateTime.now() + " | Message: " + msg);
	}
}
