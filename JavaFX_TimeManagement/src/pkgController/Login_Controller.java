package pkgController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import pkgData.Database;
import pkgMisc.ExceptionHandler;
import pkgMisc.SceneSwitcher;

public class Login_Controller
{

	@FXML
	private JFXTextField txtUsername;

	@FXML
	private JFXPasswordField pwdPassword;

	@FXML
	private JFXButton btnLogin;

	@FXML
	private JFXButton btnExit;

	private Database db;
	
	@FXML
	void onSelectButton(ActionEvent event)
	{
		try
		{
			if (event.getSource().equals(btnExit))
			{
				Platform.runLater(new Thread(() ->
				{
					System.exit(0);

				}));
			} else if (event.getSource().equals(btnLogin))
			{
				checkInputs();
				db = Database.newInstance();
				db.login(txtUsername.getText(), pwdPassword.getText().toCharArray());
				SceneSwitcher.startDashboard((Stage) btnLogin.getScene().getWindow()); 
			}
		} catch (Exception e)
		{
			ExceptionHandler.hanldeExpectedException("Invalid Input", e);
		}
	}

	private void checkInputs() throws Exception
	{
		if (txtUsername.getText().trim().isEmpty())
			throw new Exception("Please enter an username.");
		else if (pwdPassword.getText().trim().isEmpty())
			throw new Exception("Please enter a password.");
	}
}
