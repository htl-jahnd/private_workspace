package pkgMain;

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

	// TODO multi language support, send emails with task -> ui more responsible,
	// about screen, progress bar for sending, add icons for mail providers

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
			ExceptionHandler.hanldeExpectedException("Something went wrong during lauch, oops!", e);
		}

	}

	public static void main(String[] args)
	{
		launch(args);
	}

}
