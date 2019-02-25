package pkgMain;
	
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.stage.Stage;
import pkgData.Activity;
import pkgData.Database;
import pkgData.User;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("res/Login.fxml"));
			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
//		Activity act = new Activity("Testing", 0);
//    	System.out.println(act.toString());
		
		
//		try
//		{
//			Database.newInstance().createNewUser(new User("admin", "admin", "", true));
//		} catch (NoSuchAlgorithmException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
