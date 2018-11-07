package JavaFX_Auto_Resize;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFX_Auto_Resize_Main extends Application
{
	public void start(Stage primaryStage) throws IOException {
			Parent root = FXMLLoader.load(getClass().getResource("JavaFX_Auto_Resize.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}

}
