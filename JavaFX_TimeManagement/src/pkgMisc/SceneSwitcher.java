package pkgMisc;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SceneSwitcher
{
	// all paths are relative from pkgMisc
	private static final String GUI_SIGN_IN = "/pkgMain/res/Login.fxml";
	private static final String GUI_PASSWORD_MANAGER = "/pkgMain/res/Dashboard.fxml";

	public static void startLogin(Stage window) throws Exception
	{
		window.close();
		Parent root = FXMLLoader.load(SceneSwitcher.class.getResource(GUI_SIGN_IN));
		Scene scene = new Scene(root);
		window = new Stage();
		window.setScene(scene);
		window.show();
	}

	public static void startDashboard(Stage window) throws Exception
	{
		window.close();
		Parent root = FXMLLoader.load(SceneSwitcher.class.getResource(GUI_PASSWORD_MANAGER));
		Scene scene = new Scene(root);
		window = new Stage();
		window.setScene(scene);
		window.show();
	}

}
