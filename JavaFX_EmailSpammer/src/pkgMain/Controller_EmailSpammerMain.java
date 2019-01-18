package pkgMain;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.web.HTMLEditor;

public class Controller_EmailSpammerMain {
    @FXML
    private MenuItem mntmSettings;

    @FXML
    private MenuItem mntmAbout;

    @FXML
    private JFXTextField txtToMailAdress;

    @FXML
    private JFXTextField txtMailSubject;

    @FXML
    private HTMLEditor htmlMailContent;

    @FXML
    private JFXButton btnSend;

    @FXML
    private JFXButton btnExit;

    @FXML
    void onSelectButton(ActionEvent event) {
    	if(event.getSource().equals(btnSend)) {
    		//TODO
    	}
    	else if(event.getSource().equals(btnExit)) {
    		System.exit(0);
    	}
    }

    @FXML
    void onSelectMenu(ActionEvent event) {
    	if(event.getSource().equals(mntmSettings)) {
    		//TODO show settings screen
    	}
    	else if(event.getSource().equals(mntmAbout)) {
    		//TODO show about screen
    	}
    }

}
