package pkgData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.swing.JOptionPane;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;

public class ExceptionHandler {

	private static String logFilePath ="./logs/logs.log";
	
	public static void hanldeException(Exception ex) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("And unexpected error occured");
		alert.setContentText(ex.getMessage());
		alert.showAndWait();
		
		doWriteLogFile(ex);
		
	}
	
	
	public static void doWriteLogFile(Exception e)  {
		try {
			File f=new File(logFilePath);
			f.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(logFilePath, true));
			StackTraceElement[] ste = e.getStackTrace();
			out.write(LocalDateTime.now()+ ": Message: "+e.getMessage()+", Trace: ");
			for(int i=0;i<5; i++) {
				out.write("    "+ste[i]+"\n");
			}
			out.flush();
			out.close();
		}
		catch(IOException ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("And unexpected error occured");
			alert.setContentText(ex.getMessage());
			alert.showAndWait();
		}
	}
	
	public static void doClearLogFile() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(logFilePath));
			out.write("");
			out.flush();
			out.close();
		}
		catch(Exception ex) {
			hanldeException(ex);
		}
	}
}
