package pkgMain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Password_Manager_MainAppl extends Application
{

    public static void main(String[] args) throws Exception
    {
    	launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
	try
	{
	    Parent root = FXMLLoader.load(getClass().getResource("ressources/GUI_Login.fxml"));
	    primaryStage.setTitle("Copy Files");
	    primaryStage.setScene(new Scene(root));
	    
	    primaryStage.show();
	} catch (Exception e)
	{
	    pkgMisc.ExceptionHandler.hanldeUnexpectedException(e);
	}
    }

}
