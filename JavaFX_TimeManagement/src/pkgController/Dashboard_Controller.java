package pkgController;

import org.omg.CORBA.INITIALIZE;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import pkgData.Activity;
import pkgData.Entry;

public class Dashboard_Controller {

    @FXML
    private MenuItem mntmShowStatistics;

    @FXML
    private MenuItem mntmShowAllEntries;

    @FXML
    private JFXButton btnStartStop;

    @FXML
    private JFXComboBox<?> cmbxActivity;

    @FXML
    private Label lblTimer;

    @FXML
    private JFXTextArea txtMessage;
    
    @FXML
    void initialize() {
    	Activity act = new Activity("Testing", 0);
    	System.out.println(act.toString());
    }

    @FXML
    void onSelectButton(ActionEvent event) {
    	
    }

    @FXML
    void onSelectMenu(ActionEvent event) {

    }

}
