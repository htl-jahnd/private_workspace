package pkgController;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import pkgData.Database;
import pkgMisc.ExceptionHandler;
import pkgMisc.PasswordUtils;

public class AddUser_Controller
{

	@FXML
	private JFXButton btnSave;

	@FXML
	private JFXButton btnCancel;

	@FXML
	private JFXTextField txtUsername;

	@FXML
	private JFXTextField txtPassword;

	@FXML
	private JFXCheckBox chckbxIsAdmin;
	
	public static Stage mainStage;
	private Database db;
	
	@FXML
	void initialize() {
		try
		{
			db = Database.newInstance();
		} catch (Exception e)
		{
			handleException(e);
		}
	}

	@FXML
	void onSelectButton(ActionEvent event)
	{
		try
		{
			if (event.getSource().equals(btnCancel))
			{
				mainStage.close();
			} else if (event.getSource().equals(btnSave))
			{
				db.createNewUser(txtUsername.getText(), txtPassword.getText().toCharArray(), chckbxIsAdmin.isSelected());
				mainStage.close();
			}
		}  catch (Exception e)
		{
			handleException(e);
		}
	}
	
	private void handleException(Exception e)
	{
		ExceptionHandler.hanldeUnexpectedException(e);
	}

}
