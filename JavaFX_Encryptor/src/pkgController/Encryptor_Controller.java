package pkgController;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.crypto.BadPaddingException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import pkgData.Database;
import pkgMisc.ExceptionHandler;
import pkgMisc.TextUtils;

public class Encryptor_Controller implements Initializable
{
	// Declaring FXML objects
	// All variables bellow belong to the "Text Encrypt" section
	@FXML
	private TextArea txtInput;
	@FXML
	private PasswordField txtPassword_Text;
	@FXML
	private RadioButton rdbtnEncrypt_Text;
	@FXML
	private RadioButton rdbtnDecrypt_Text;
	@FXML
	private Button btnStartText;
	@FXML
	private TextArea txtOutput;
	@FXML
	private Button btnCopyToClipboard;
	@FXML
	private CheckBox chckbxDefaultPassword_Text;
	@FXML
	private Label lblMessage_Text;
	@FXML 
	private ImageView imgMessageLabel_Text;

	// All variables bellow belong to the "File Encrypt" section
	@FXML
	private Button btnFileChooser_Input;
	@FXML
	private TextField txtFilePath_Input;
	@FXML
	private Button btnFileChooser_Output;
	@FXML
	private TextField txtFilePath_Output;
	@FXML
	private CheckBox chckbxDefaultPassword_File;
	@FXML
	private Button btnStartFile;
	@FXML
	private PasswordField txtPassword_File;
	@FXML
	private RadioButton rdbtnEncrypt_File;
	@FXML
	private RadioButton rdbtnDecrypt_File;
	@FXML
	private Label lblMessage_File;
	@FXML 
	private ImageView imgMessageLabel_File;
	

	// Other variables
	private Database db;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		db = Database.newInstance();
	}

	// For btnExit
	@FXML
	public void exit(ActionEvent event)
	{
		System.exit(0);
	}

	// For text en-/decryption
	public void startText(ActionEvent event)
	{
		try
		{
			String text = txtInput.getText();
			if (text.trim().isEmpty())
			{
				ExceptionHandler.displayErrorMessage("Please insert a Text");
			} else
			{
				String outputText = "";
				if (rdbtnEncrypt_Text.isSelected())
				{
					outputText = doEncrypt(text);
				} else
				{
					outputText = doDecrypt(text);
				}
				txtOutput.setText(outputText);
				lblMessage_Text.setText("Text succesfully "+ (rdbtnEncrypt_Text.isSelected() ? "encrypted" : "decrypted") + "!");
				imgMessageLabel_Text.setImage(new Image("/Images/checkmark.png"));
			}

		} catch (BadPaddingException bex)
		{
			txtOutput.setText("");
			lblMessage_Text.setText("Incorrect Password");
			imgMessageLabel_Text.setImage(new Image("/Images/cross.png"));
			ExceptionHandler.hanldeExpectedException("Incorrect Password", bex);
		} catch (Exception ex)
		{
			txtOutput.setText("");
			lblMessage_Text.setText("An error occured during "+ (rdbtnEncrypt_File.isSelected()? "encrypting" : "decypting") +  " the text!");
			imgMessageLabel_Text.setImage(new Image("/Images/cross.png"));
			ExceptionHandler.hanldeUnexpectedException(ex);
		}

	}

	// For file en-/decryption
	public void startFile(ActionEvent event)
	{
		try
		{
			if (checkInput_Files())
			{
				if (rdbtnEncrypt_File.isSelected())
				{
					String enc = null;
					String everything = TextUtils.readText(new File(txtFilePath_Input.getText()));
					if (chckbxDefaultPassword_File.isSelected())
						enc = db.doEncrypt(everything, db.getDefaultPasssword());
					else
						enc = db.doEncrypt(everything, txtPassword_File.getText().toCharArray());
					File f1 = new File(txtFilePath_Output.getText());
					f1.createNewFile();
					TextUtils.writeText(f1, enc);
					lblMessage_File.setText("File succesfully encrypted!");
					imgMessageLabel_File.setImage(new Image("/Images/checkmark.png"));
				} else 
				{
					String enc = null;
					String everything = TextUtils.readText(new File(txtFilePath_Input.getText()));
					if (chckbxDefaultPassword_File.isSelected())
						enc = db.doDecrypt(everything, db.getDefaultPasssword());
					else
						enc = db.doDecrypt(everything, txtPassword_File.getText().toCharArray());
					File f1 = new File(txtFilePath_Output.getText());
					f1.createNewFile();
					TextUtils.writeText(f1, enc);
					lblMessage_File.setText("File succesfully decrypted!");
					imgMessageLabel_File.setImage(new Image("/Images/checkmark.png"));
				}
			}
		} catch (BadPaddingException bex)
		{
			lblMessage_File.setText("Incorrect Password");
			imgMessageLabel_File.setImage(new Image("/Images/cross.png"));
			ExceptionHandler.hanldeExpectedException("Incorrect Password", bex);
		} catch (Exception ex)
		{
			lblMessage_File.setText("An error occured during "+ (rdbtnEncrypt_File.isSelected()? "encrypting" : "decypting") +  " the file!");
			imgMessageLabel_File.setImage(new Image("/Images/cross.png"));
			ExceptionHandler.hanldeUnexpectedException(ex);
		}

	}

	private boolean checkInput_Files() throws Exception
	{
		if (txtFilePath_Input.getText().trim().isEmpty())
			throw new Exception("Input file path must not be empty!");
		else if (txtFilePath_Output.getText().trim().isEmpty())
			throw new Exception("Output file path must not be empty!");
		else if (txtPassword_File.getText().trim().isEmpty() && !chckbxDefaultPassword_File.isSelected())
			throw new Exception("Please enter a password");
		else
			return true;
	}

	public void chooseOutputFile(ActionEvent event)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"),
				new FileChooser.ExtensionFilter("TXT", "*.txt"), new FileChooser.ExtensionFilter("DEC", "*.dec"));
		File file = fileChooser.showSaveDialog(btnFileChooser_Input.getScene().getWindow());
		if (file != null)
		{
			txtFilePath_Output.setText(file.getAbsolutePath());
		}

	}

	public void chooseInputFile(ActionEvent event)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"),
				new FileChooser.ExtensionFilter("TXT", "*.txt"), new FileChooser.ExtensionFilter("DEC", "*.dec"));
		File file = fileChooser.showOpenDialog(btnFileChooser_Input.getScene().getWindow());
		if (file != null)
		{
			txtFilePath_Input.setText(file.getAbsolutePath());
		}
	}

	public void checkIfDefaultPassword_Text(ActionEvent event)
	{
		if (chckbxDefaultPassword_Text.isSelected())
		{
			txtPassword_Text.setDisable(Boolean.TRUE);
		} else
		{
			txtPassword_Text.setDisable(Boolean.FALSE);
		}
	}

	private String doDecrypt(String text) throws IOException, Exception
	{
		String output = "";

		if (chckbxDefaultPassword_Text.isSelected())
		{
			output = db.doDecrypt(text, db.getDefaultPasssword());
		} else
		{
			checkPassword();
			output = db.doDecrypt(text, txtPassword_Text.getText().toCharArray());
		}

		return output;
	}

	private void checkPassword() throws Exception
	{
		if (txtPassword_Text.getText().trim().isEmpty())
			throw new Exception("Please enter a password!");
	}

	private String doEncrypt(String text) throws Exception
	{
		String output = "";
		if (chckbxDefaultPassword_Text.isSelected())
		{
			output = db.doEncrypt(text, db.getDefaultPasssword());
		} else
		{
			checkPassword();
			output = db.doEncrypt(text, txtPassword_Text.getText().toCharArray());
		}
		return output;
	}

	public void copyToClipboard(ActionEvent event)
	{
		StringSelection stringSelection = new StringSelection(txtOutput.getText());
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}

}
