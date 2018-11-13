package pkgController;

import java.io.IOException;

import javax.crypto.BadPaddingException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pkgData.Database;
import pkgMisc.ExceptionHandler;
import pkgMisc.SceneSwitcher;

public class Controller_SignIn
{

    @FXML
    private PasswordField pwdPasswordField;
    private Database db;

    @FXML
    public void exit(ActionEvent event)
    {
	System.exit(0);

    }

    @FXML
    public void login(ActionEvent event)
    {
	db = Database.newInstance();
	char[] key = pwdPasswordField.getText().toCharArray();
	try
	{
	    db.doStore(key);
	    //getting the current stage
	    Stage stageTheLabelBelongs = (Stage) pwdPasswordField.getScene().getWindow();
	    //switching stage
	    //TODO switch to real application stage
	    new SceneSwitcher().startPasswordManager(stageTheLabelBelongs);
	    
	} catch (IOException e)
	{
	    if (e.getMessage().contains("BadPaddingException"))
		ExceptionHandler.hanldeExpectedException("ERROR: Wrong Password", e);
	    else
		ExceptionHandler.hanldeUnexpectedException(e);
	} catch (Exception e)
	{
	    ExceptionHandler.hanldeUnexpectedException(e);
	}
    }

}
