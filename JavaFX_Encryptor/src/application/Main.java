package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	@Override
	// TODO
	// Drag and drop for file encryptor
	// https://stackoverflow.com/questions/32534113/javafx-drag-and-drop-a-file-into-a-program
	// Add application icon, right click menu in dock
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../pkgGUI/Encryptor_Main_Scene.fxml"));

			primaryStage.getIcons().addAll(
					new Image("http://icons.iconarchive.com/icons/graphicloads/flat-finance/256/keys-icon.png"));
			// new Image("../Images/icon_64x64.png"),
			// new Image("../Images/icon_128x128.png"));

//			primaryStage.initStyle(StageStyle.UTILITY);
//			primaryStage.setResizable(false);
			primaryStage.setTitle("Encryptor / Decryptor");
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch (Exception e) {
			pkgMisc.ExceptionHandler.hanldeUnexpectedException(e);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
