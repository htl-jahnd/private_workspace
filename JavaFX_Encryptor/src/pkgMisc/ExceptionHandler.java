package pkgMisc;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ExceptionHandler {

	private static Boolean debug = true;

	public static void hanldeUnexpectedException(Exception ex) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("An unexpected error occured");
		alert.setContentText(ex.getMessage());
		alert.showAndWait();

		Logger.doWriteLogFile(ex);
		if (debug)
			ex.printStackTrace();
	}

	public static void hanldeExpectedException(String msg, Exception ex) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("An error occured");
		alert.setContentText(msg);
		alert.showAndWait();

		Logger.doWriteLogFile(ex);
		if (debug)
			ex.printStackTrace();
	}

	public static void displayErrorMessage(String msg) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("An error occured");
		alert.setContentText(msg);
		alert.showAndWait();
	}

	public static Boolean getDebug() {
		return debug;
	}

	public static void setDebug(Boolean debug) {
		ExceptionHandler.debug = debug;
	}

}
