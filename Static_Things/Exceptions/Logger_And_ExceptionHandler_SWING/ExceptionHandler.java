package Logger_And_ExceptionHandler_SWING;
import javax.swing.JOptionPane;

public class ExceptionHandler {


	public static void hanldeException(Exception ex) {
		JOptionPane.showMessageDialog(null, ex.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
		Logger.newInstance().logException(ex);
	}
}
