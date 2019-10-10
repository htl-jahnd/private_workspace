package pkgMain;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import pkgData.Database;
import pkgMisc.ExceptionHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;

public class Main extends Application
{

	// TODO send emails with task -> ui more responsible,
	// 	progress bar for sending

	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			Database db = Database.newInstance();
			try
			{
				db.readPreferences();
				
			} catch (Exception e)
			{
				ExceptionHandler.hanldeUnexpectedException(e);
			}
			
			Locale locale = db.getLanguage().getLocale();
			ResourceBundle bundle = ResourceBundle.getBundle("pkgMain/ressources/strings_EmailSpammer", locale); //$NON-NLS-1$
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("ressources/EmailSpammer.fxml"), bundle); //$NON-NLS-1$
			Scene scene = new Scene(root);
			if (db.isDarkMode())
			{
				scene.getStylesheets().add("pkgMain/ressources/darkmode.css"); //$NON-NLS-1$
			} else
			{
				scene.getStylesheets().add("pkgMain/ressources/lightmode.css"); //$NON-NLS-1$
			}
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e)
		{
			ExceptionHandler.hanldeExpectedException(Messages.getString("Main.LaunchExceptionText"), e); //$NON-NLS-1$
		}

	}

	public static void main(String[] args)
	{
		launch(args);
	}

}
