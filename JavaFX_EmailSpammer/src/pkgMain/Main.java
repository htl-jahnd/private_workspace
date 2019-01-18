package pkgMain;

import javafx.application.Application;
import javafx.stage.Stage;
import pkgData.Database;
import pkgMisc.ExceptionHandler;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application
{
	
	//TODO multi language support, send emails with task -> ui more responsible
	
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("ressources/EmailSpammer.fxml"));
			Scene scene = new Scene(root);
			Database db = Database.newInstance();
			try
			{
				db.readPreferences();
				if (db.isDarkMode())
				{
					scene.getStylesheets().add("pkgMain/ressources/darkmode.css");
				} else
				{
					scene.getStylesheets().add("pkgMain/ressources/lightmode.css");
				}
			} catch (Exception e)
			{
				ExceptionHandler.hanldeUnexpectedException(e);
			}
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e)
		{
			ExceptionHandler.hanldeExpectedException("Something went wrong during lauch, oops!",e);
		}
	}

	public static void main(String[] args)
	{
		launch(args);
	}

}
