package pkgMain;

import java.util.ArrayList;
import javax.crypto.Cipher;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pkgData.*;
import javafx.stage.StageStyle;
import pkgController.Controller_Login;

public class Password_Manager_MainAppl extends Application
{

    public static void main(String[] args) throws Exception
    {


    }

    @Override
    public void start(Stage primaryStage)
    {
	try
	{
	    Parent root = FXMLLoader.load(getClass().getResource("../pkgGUI/GUI_Login.fxml"));
	    primaryStage.setTitle("Copy Files");
	    primaryStage.setScene(new Scene(root));
	    
	    primaryStage.show();
	} catch (Exception e)
	{
	    pkgMisc.ExceptionHandler.hanldeUnexpectedException(e);
	}
    }

}
