package pkgMisc;

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
			alert.setTitle(Messages.getString("Logger.FileWriteException.Title")); //$NON-NLS-1$
			alert.setHeaderText(Messages.getString("Logger.FileWriteException.ExceptionText")); //$NON-NLS-1$
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
			throw new FileNotFoundException(Messages.getString("Logger.FileViewException.NotFound.Text1") + LOG_FILE_PATH + Messages.getString("Logger.FileViewException.NotFound.Text2")); //$NON-NLS-1$ //$NON-NLS-2$
		else if (!f.isFile())
			throw new FileNotFoundException(Messages.getString("Logger.FileViewException.NoFile.Text1") + LOG_FILE_PATH + Messages.getString("Logger.FileViewException.NoFile.Text2")); //$NON-NLS-1$ //$NON-NLS-2$
		Desktop.getDesktop().open(new File(LOG_FILE_PATH));
	}
}
