package misc;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Logger implements IStaticStrings
{

	static void doWriteLogFile(Exception e)
	{
		try
		{
			File f = new File(LOG_FILE_PATH);
			if (!f.exists())
				f.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true));
			StackTraceElement[] ste = e.getStackTrace();
			out.write(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "   : Message: " + e.getMessage() + "  , Trace: "); //$NON-NLS-1$ //$NON-NLS-2$
			for (int i = 0; i < 5; i++)
			{
				out.write("    " + ste[i] + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			out.flush();
			out.close();
		} catch (IOException ex)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error"); //$NON-NLS-1$
			alert.setHeaderText("An error occured during writing logfile."); //$NON-NLS-1$
			alert.setContentText(ex.getMessage());
			alert.showAndWait();
		}
	}

	public static void doClearLogFile()
	{
		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(LOG_FILE_PATH));
			out.write(""); //$NON-NLS-1$
			out.flush();
			out.close();
		} catch (Exception ex)
		{
			ExceptionHandler.hanldeUnexpectedException(ex);
		}
	}

	public static void viewLogFile() throws IOException
	{
		File f = new File(LOG_FILE_PATH);
		if (!f.exists())
			throw new FileNotFoundException("Could not find file: "+ LOG_FILE_PATH ); //$NON-NLS-1$ //$NON-NLS-2$
		else if (!f.isFile())
			throw new FileNotFoundException(LOG_FILE_PATH +" is not a file."); //$NON-NLS-1$ //$NON-NLS-2$
		Desktop.getDesktop().open(new File(LOG_FILE_PATH));
	}
}
