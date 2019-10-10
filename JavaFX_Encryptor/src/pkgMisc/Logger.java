package pkgMisc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import pkgData.Database;

public class Logger
{
	public static void doWriteLogFile(Exception e)
	{
		try
		{
			File logFile = Database.getLogFile();
			if (!logFile.exists())
				logFile.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(logFile, true));
			StackTraceElement[] ste = e.getStackTrace();
			out.write(LocalDateTime.now() + ": Message: " + e.getMessage() + ", Trace: ");
			for (int i = 0; i < 5; i++)
			{
				out.write("    " + ste[i] + "\n");
			}
			out.flush();
			out.close();
		} catch (IOException ex)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("And unexpected error occured");
			alert.setContentText(ex.getMessage());
			alert.showAndWait();
		}
	}

	public static void doClearLogFile() throws FileNotFoundException
	{
		if (Database.getLogFile().exists())
		{
			try
			{
				BufferedWriter out = new BufferedWriter(new FileWriter(Database.getLogFile()));
				out.write("");
				out.flush();
				out.close();
			} catch (Exception ex)
			{
				ExceptionHandler.hanldeUnexpectedException(ex);
			}
		} else
			throw new FileNotFoundException("There is no log file to clear");

	}
}
