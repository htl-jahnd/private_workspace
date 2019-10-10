package pkgMain;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application
{
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("res/fxml/Login.fxml"));
			Scene scene = new Scene(root);
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("res/icon.png")));
			primaryStage.setTitle("Time Management");
//			System.setProperty("apple.laf.useScreenMenuBar", "false");
//			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "ImageRotator");
//			com.apple.eawt.Application.getApplication().setDockIconImage(Toolkit.getDefaultToolkit().getImage("/res/icon.png"));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, SQLException, Exception
	{
		// Database.newInstance().createNewUser(new User("admin", "admin", "", true));
		launch(args);
	}
}
