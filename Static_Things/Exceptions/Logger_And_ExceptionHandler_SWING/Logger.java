package Logger_And_ExceptionHandler_SWING;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.swing.JOptionPane;

public class Logger {

	private final String logFilePath;
	private static Logger instance;
	
	private Logger(String lfp) {
		logFilePath=lfp;
	}
	//For first use
	public static Logger newInstance(String logFilePath) {
		if(instance == null) {
			instance = new Logger(logFilePath);
		}
		return instance;
	}
	//Just for ExceptionHandler
	public static Logger newInstance() {
		return instance;
	}
	
	public void logException(Exception e) {
		try {
			File f = new File(logFilePath);
			if(!f.exists()) {
				f.createNewFile();
			}
			BufferedWriter out = new BufferedWriter(new FileWriter(logFilePath, true));
			StackTraceElement[] ste = e.getStackTrace();
			out.write(LocalDateTime.now()+ ": Message: "+e.getMessage()+", Trace: ");
			for(int i=0;i<5; i++) {
				out.write("    "+ste[i]+"\n");
			}
			out.flush();
			out.close();
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "Could not write previous errror into log file: ","ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public  void viewLogFile() throws IOException {
		Desktop.getDesktop().open(new File(logFilePath));
	}
	
	public void clearLogFile() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(logFilePath));
			out.write("");
			out.flush();
			out.close();
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "Could not clear log file: ","ERROR", JOptionPane.ERROR_MESSAGE);
			
		}
	}
}
