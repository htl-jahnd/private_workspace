package pkgMisc;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ExceptionHandler implements IStaticStrings
{

	private static Boolean debug = true;

	public static Boolean getDebug()
	{
		return debug;
	}

	public static void setDebug(Boolean debug)
	{
		ExceptionHandler.debug = debug;
	}

	public static void hanldeUnexpectedException(Exception ex)
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(Messages.getString("ExceptionHandler.UnexpectedException.Title")); //$NON-NLS-1$
		alert.setHeaderText(Messages.getString("ExceptionHandler.UnexpectedException.Header")); //$NON-NLS-1$
		alert.setContentText(ex.getClass() + ": " + ex.getMessage()); //$NON-NLS-1$
		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label(Messages.getString("ExceptionHandler.UnexpectedException.Stacktrace")); //$NON-NLS-1$

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);
		Logger.doWriteLogFile(ex);
		alert.showAndWait();

		if (debug)
			ex.printStackTrace();
	}

	public static void hanldeExpectedException(String title, Exception ex)
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(Messages.getString("ExceptionHandler.ExpectedException.Title")); //$NON-NLS-1$
		alert.setHeaderText(title);
		alert.setContentText(ex.getMessage());
		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label(Messages.getString("ExceptionHandler.ExpectedException.Stacktrace")); //$NON-NLS-1$

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		Logger.doWriteLogFile(ex);
		alert.showAndWait();
		if (debug)
			ex.printStackTrace();

	}

}
