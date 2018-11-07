package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import pkgData.ExceptionHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class FileCopyMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			 Parent root = FXMLLoader.load(getClass()
	                    .getResource("./MainWindow.fxml"));
	            primaryStage.setTitle("Copy Files");
	            primaryStage.setScene(new Scene(root));
	            primaryStage.show();
		} catch(Exception e) {
			ExceptionHandler.hanldeException(e);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
