package pkgController;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import pkgData.Database;
import pkgData.GMailer;
import pkgMisc.AddressFormatValidator;
import pkgMisc.ExceptionHandler;

public class Controller_EmailSpammerMain
{
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
	private JFXSlider sliderAmmount;

	private Database db;

	@FXML
	void initialize()
	{
		db = Database.newInstance();
	}

	@FXML
	void onSelectButton(ActionEvent event)
	{
		try
		{
			if (event.getSource().equals(btnSend))
			{
				if (!AddressFormatValidator.isValidEmailAddress(txtToMailAdress.getText()))
					throw new Exception("Please enter a valid mail address");
				doSendEmail();
			} else if (event.getSource().equals(btnExit))
			{
				Platform.exit();
			}
		} catch (Exception e)
		{
			ExceptionHandler.hanldeUnexpectedException(e);
		}
	}

	private void doSendEmail() throws Exception
	{
		MailAccount tmpAcc = db.getAccount();
		String to = txtToMailAdress.getText();
		String[] toArr = new String[1];
		toArr[0] = to;
		String subj = txtMailSubject.getText();
		String cont = htmlMailContent.getHtmlText();
		GMailer gmailer = new GMailer(tmpAcc.getAddress(), tmpAcc.getPassword(), toArr, subj, cont,
				tmpAcc.getProvider());
		for (int i = 0; i < sliderAmmount.getValue(); i++)
		{
			gmailer.sendMail();
		}

	}

	@FXML
	void onSelectMenu(ActionEvent event)
	{
		try
		{
			if (event.getSource().equals(mntmSettings))
			{
				Stage st = new Stage();
				AnchorPane root = (AnchorPane) FXMLLoader
						.load(getClass().getResource("/pkgMain/ressources/Settings.fxml"));
				Scene scene = new Scene(root);
				if (db.isDarkMode())
				{
					scene.getStylesheets().add("pkgMain/ressources/darkmode.css");
				} else
				{
					scene.getStylesheets().add("pkgMain/ressources/lightmode.css");
				}
				st.setScene(scene);
				st.show();
				st.requestFocus();
			} else if (event.getSource().equals(mntmAbout))
			{
				// TODO show about screen
			}
		} catch (Exception e)
		{
			ExceptionHandler.hanldeUnexpectedException(e);
		}
	}

}
