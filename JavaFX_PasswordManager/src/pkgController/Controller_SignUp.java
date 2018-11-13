package pkgController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Controller_SignUp {

    @FXML
    private JFXTextField txtEmailAddress;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXPasswordField txtConfirmPassword;

    @FXML
    private JFXButton btnSignUp;

    @FXML
    private JFXButton btnCancel;

    @FXML
    void onSelectButton(ActionEvent event) {

    }

}
