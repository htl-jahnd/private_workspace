package pkgController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import pkgData.Database;
import pkgData.ELanguages;
import pkgData.MailProviderSMTP;
import pkgMisc.AddressFormatValidator;
import pkgMisc.ExceptionHandler;

public class Controller_Settings
{

	@FXML
	private JFXToggleButton toggleDarkMode;

	@FXML
	private JFXComboBox<ELanguages> cmbxLanguage;

	@FXML
	private JFXTextField txtMailAdress;

	@FXML
	private JFXPasswordField txtMailPassword;

	@FXML
	private JFXComboBox<MailProviderSMTP> cmbxMailProvider;

	@FXML
	private JFXButton btnSave;

	@FXML
	private JFXButton btnCancel;
	
    @FXML
    private JFXTextField txtMailFromName;

	private ObservableList<ELanguages> listLanguages;
	private ObservableList<MailProviderSMTP> listMailProviders;

	private Database db;

	@FXML
	void initialize()
	{
		listLanguages = FXCollections.observableArrayList();
		listMailProviders = FXCollections.observableArrayList();

		listLanguages.addAll(ELanguages.values());
		cmbxLanguage.setItems(listLanguages);

		listMailProviders.addAll(MailProviderSMTP.values());
		cmbxMailProvider.setItems(listMailProviders);

		db = Database.newInstance();

		txtMailAdress.setText(db.getAccount().getAddress());
		txtMailPassword.setText(db.getAccount().getPassword());
		cmbxLanguage.setValue(db.getLanguage());
		cmbxMailProvider.setValue(db.getAccount().getProvider());
		toggleDarkMode.setSelected(db.isDarkMode());
		txtMailFromName.setText(db.getAccount().getName());
	}

	@FXML
	void onSelectButton(ActionEvent event)
	{
		try
		{
			if (event.getSource().equals(btnCancel))
			{
				closeStage();
			} else if (event.getSource().equals(btnSave))
			{
				if (!AddressFormatValidator.isValidEmailAddress(txtMailAdress.getText()))
					throw new Exception("Please enter a valid mail address");
				else if (txtMailPassword.getText().isEmpty())
					throw new Exception("Please enter a valid password");
				else if(txtMailFromName.getText().isEmpty())
					throw new Exception("Please enter a valid name");
				db.getAccount().setAddress(txtMailAdress.getText());
				db.getAccount().setPassword(txtMailPassword.getText());
				db.getAccount().setProvider(cmbxMailProvider.getValue());
				db.getAccount().setName(txtMailFromName.getText());
				db.setDarkMode(toggleDarkMode.isSelected());
				db.setLanguage(cmbxLanguage.getValue());
				db.writePreferences();
				closeStage();
			}
		} catch (Exception e)
		{
			ExceptionHandler.hanldeUnexpectedException(e);
		}
	}

	private void closeStage()
	{
		Stage stg = (Stage) btnCancel.getScene().getWindow();
		stg.close();
	}

}
