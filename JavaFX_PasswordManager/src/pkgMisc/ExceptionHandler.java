package pkgMisc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import pkgData.Database;

public class ExceptionHandler
{

    private static Boolean debug = true;

    public static void hanldeUnexpectedException(Exception ex)
    {
	Alert alert = new Alert(AlertType.ERROR);
	alert.setTitle("Error");
	alert.setHeaderText("And unexpected error occured");
	alert.setContentText(ex.getMessage());
	alert.showAndWait();

	doWriteLogFile(ex);
	if (debug)
	    ex.printStackTrace();
    }

    public static void hanldeExpectedException(String msg, Exception ex)
    {
	Alert alert = new Alert(AlertType.ERROR);
	alert.setTitle("Error");
	alert.setHeaderText("And error occured");
	alert.setContentText(msg);
	alert.showAndWait();

	doWriteLogFile(ex);
	if (debug)
	    ex.printStackTrace();
    }

    private static void doWriteLogFile(Exception e)
    {
	try
	{
	    File f = new File(Database.getLogFilePath());
	    if (!f.exists())
		f.createNewFile();
	    BufferedWriter out = new BufferedWriter(new FileWriter(Database.getLogFilePath(), true));
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

    public static void doClearLogFile()
    {
	try
	{
	    BufferedWriter out = new BufferedWriter(new FileWriter(Database.getLogFilePath()));
	    out.write("");
	    out.flush();
	    out.close();
	} catch (Exception ex)
	{
	    hanldeUnexpectedException(ex);
	}
    }
}
