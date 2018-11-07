package pkgMisc;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SceneSwitcher
{
    // all paths are relative from pkgMisc
    private static final String GUI_LOGIN = "../pkgGUI/GUI_Login.fxml";
    private static final String GUI_PASSWORD_MANAGER ="../pkgGUI/Password Manager.fxml";

    public void startLogin(Stage window) throws Exception
    {
	window.close();
	Parent root = FXMLLoader.load(getClass().getResource(GUI_LOGIN));
	Scene scene = new Scene(root);
	window = new Stage();
	window.setScene(scene);
	window.show();
    }
    
    public void startPasswordManager(Stage window) throws Exception
    {
	window.close();
	Parent root = FXMLLoader.load(getClass().getResource(GUI_PASSWORD_MANAGER));
	Scene scene = new Scene(root);
	window = new Stage();
	window.setScene(scene);
	window.show();
    }

    public SceneSwitcher()
    {

    }

}
