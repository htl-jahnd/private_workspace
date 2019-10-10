package pkgController;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pkgData.Database;
import pkgData.ELanguage;
import pkgData.EMailProviderSMTP;
import pkgMisc.AddressFormatValidator;
import pkgMisc.ExceptionHandler;

public class Controller_Settings
{

	@FXML
	private JFXToggleButton toggleDarkMode;

	@FXML
	private JFXComboBox<ELanguage> cmbxLanguage;

	@FXML
	private JFXTextField txtMailAdress;

	@FXML
	private JFXPasswordField txtMailPassword;

	@FXML
	private JFXComboBox<EMailProviderSMTP> cmbxMailProvider;

	@FXML
	private JFXButton btnSave;

	@FXML
	private JFXButton btnCancel;

	@FXML
	private JFXTextField txtMailFromName;

	private ObservableList<ELanguage> listLanguages;
	private ObservableList<EMailProviderSMTP> listMailProviders;
	public static Stage mainStage;

	private Database db;

	@FXML
	void initialize()
	{
		listLanguages = FXCollections.observableArrayList();
		listMailProviders = FXCollections.observableArrayList();

		listLanguages.addAll(ELanguage.values());
		cmbxLanguage.setItems(listLanguages);

		listMailProviders.addAll(EMailProviderSMTP.values());
		cmbxMailProvider.setItems(listMailProviders);

		db = Database.newInstance();

		txtMailAdress.setText(db.getAccount().getAddress());
		txtMailPassword.setText(db.getAccount().getPassword());
		cmbxLanguage.setValue(db.getLanguage());
		cmbxMailProvider.setValue(db.getAccount().getProvider());
		toggleDarkMode.setSelected(db.isDarkMode());
		txtMailFromName.setText(db.getAccount().getName());

		cmbxLanguage.setCellFactory(cmbx -> new ListCell<ELanguage>() {
			@Override
			protected void updateItem(ELanguage item, boolean empty)
			{
				super.updateItem(item, empty);

				if (empty)
				{
					setGraphic(null);
				} else
				{
					HBox hBox = new HBox(2);
					hBox.setAlignment(Pos.CENTER_LEFT);
					ImageView iv = new ImageView();
					try
					{
						iv.setImage(SwingFXUtils.toFXImage(ELanguage.getIcon(item), null));
					} catch (Exception e)
					{
						ExceptionHandler.hanldeUnexpectedException(e);
					}
					iv.setFitHeight(25);
					iv.setFitWidth(25);
					// Add the values from our piece to the HBox
					try
					{
						hBox.getChildren().addAll(iv, new Label("   " + item));
					} catch (Exception e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// Set the HBox as the display
					setGraphic(hBox);
				}
			}
		});

		cmbxMailProvider.setCellFactory(cmbx -> new ListCell<EMailProviderSMTP>() {
			@Override
			protected void updateItem(EMailProviderSMTP item, boolean empty)
			{
				super.updateItem(item, empty);

				if (empty)
				{
					setGraphic(null);
				} else
				{
					// Create a HBox to hold our displayed value
					HBox hBox = new HBox(2);
					hBox.setAlignment(Pos.CENTER_LEFT);
					ImageView iv = new ImageView();
					try
					{
						iv.setImage(SwingFXUtils.toFXImage(EMailProviderSMTP.getIcon(item), null));
					} catch (Exception e)
					{
						ExceptionHandler.hanldeUnexpectedException(e);
					}
					iv.setFitHeight(25);
					iv.setFitWidth(25);
					// Add the values from our piece to the HBox
					try
					{
						hBox.getChildren().addAll(iv, new Label("   " + item));
					} catch (Exception e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// Set the HBox as the display
					setGraphic(hBox);
				}
			}
		});

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
				else if (txtMailFromName.getText().isEmpty())
					throw new Exception("Please enter a valid name");
				if (db.getLanguage() != cmbxLanguage.getValue() || db.isDarkMode() != toggleDarkMode.isSelected())
					showInformationDialog();
				updateDatabase();
				closeStage();
			}
		} catch (Exception e)
		{
			ExceptionHandler.hanldeUnexpectedException(e);
		}
	}

	private void showInformationDialog() throws Exception
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Changes Unavailable");
		alert.setHeaderText("Some of yout changes will not be available until the program is restarted");
		alert.setContentText("Do you want to restart now?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
		{
			updateDatabase();
			Stage st = new Stage();
			db.readPreferences();
			Locale locale = db.getLanguage().getLocale();
			ResourceBundle bundle = ResourceBundle.getBundle("pkgMain/ressources/strings_EmailSpammer", locale); //$NON-NLS-1$
			AnchorPane root = (AnchorPane) FXMLLoader
					.load(getClass().getResource("../pkgMain/ressources/EmailSpammer.fxml"), bundle); //$NON-NLS-1$
			Scene scene = new Scene(root);
			if (db.isDarkMode())
			{
				scene.getStylesheets().add("pkgMain/ressources/darkmode.css"); //$NON-NLS-1$
			} else
			{
				scene.getStylesheets().add("pkgMain/ressources/lightmode.css"); //$NON-NLS-1$
			}
			st.setScene(scene);
			mainStage.close();
			this.closeStage();
			st.show();

		} else
		{
			updateDatabase();
			closeStage();
		}
	}

	private void updateDatabase() throws Exception
	{
		db.getAccount().setAddress(txtMailAdress.getText());
		db.getAccount().setPassword(txtMailPassword.getText());
		db.getAccount().setProvider(cmbxMailProvider.getValue());
		db.getAccount().setName(txtMailFromName.getText());
		db.setDarkMode(toggleDarkMode.isSelected());
		db.setLanguage(cmbxLanguage.getValue());
		db.writePreferences();
	}

	private void closeStage()
	{
		Stage stg = (Stage) btnCancel.getScene().getWindow();
		stg.close();
	}

}
