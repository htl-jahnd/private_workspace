package Logger_And_ExceptionHandler_FX;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ExceptionHandler implements IStaticStrings
{

	private static Boolean debug = true;

	public static void hanldeUnexpectedException(Exception ex)
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("And unexpected error occured");
		alert.setContentText(ex.getMessage());
		alert.showAndWait();

		Logger.doWriteLogFile(ex);
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

		Logger.doWriteLogFile(ex);
		if (debug)
			ex.printStackTrace();
	}

}
