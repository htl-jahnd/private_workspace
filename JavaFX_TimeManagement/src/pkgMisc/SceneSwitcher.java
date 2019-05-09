package pkgMisc;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneSwitcher
{
	// all paths are relative from pkgMisc
	private static final String GUI_SIGN_IN = "/pkgMain/res/fxml/Login.fxml";
	private static final String GUI_DASHBOARD = "/pkgMain/res/fxml/Dashboard.fxml";

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
		Parent root = FXMLLoader.load(SceneSwitcher.class.getResource(GUI_DASHBOARD));
		Scene scene = new Scene(root);
		window = new Stage();
		window.setScene(scene);
		window.show();
	}

}
