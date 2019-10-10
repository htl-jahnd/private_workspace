package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import data.FileCounter;
import data.LineCounter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import misc.ExceptionHandler;
import misc.FileExtensions;

public class MainController implements FileExtensions {

	@FXML
	private JFXTextField txtFilePath;

	@FXML
	private JFXButton btnBrowseFile;

	@FXML
	private JFXRadioButton rdbtnCheckFile;

	@FXML
	private ToggleGroup groupCheck;

	@FXML
	private JFXTextField txtFolderPath;

	@FXML
	private JFXButton btnBrowseFolder;

	@FXML
	private JFXRadioButton rdbtnCheckFolder;

	@FXML
	private JFXCheckBox chbxJava;

	@FXML
	private JFXCheckBox chckCs;

	@FXML
	private JFXCheckBox chckJs;

	@FXML
	private JFXCheckBox chckTs;

	@FXML
	private JFXCheckBox chckPhp;

	@FXML
	private JFXCheckBox chckPy;

	@FXML
	private JFXCheckBox chckC;

	@FXML
	private JFXCheckBox chckCpp;

	@FXML
	private JFXCheckBox chckSql;

	@FXML
	private JFXCheckBox chckSwift;

	@FXML
	private JFXCheckBox chckRb;

	@FXML
	private JFXCheckBox chckGo;

	@FXML
	private JFXCheckBox chckHtml;

	@FXML
	private JFXCheckBox chckXml;

	@FXML
	private JFXCheckBox chckFxml;

	@FXML
	private JFXCheckBox chckXaml;

	@FXML
	private JFXCheckBox chckCss;

	@FXML
	private JFXCheckBox chckScss;

	@FXML
	private JFXCheckBox chckMarkdown;

	@FXML
	private JFXButton btnStart;

	@FXML
	private JFXButton btnCancel;

	@FXML
	private JFXProgressBar progressBar;

	@FXML
	private Label lblProgressInformation;

	@FXML
	private VBox paneOthers;

	@FXML
	private VBox paneProgrammingLanguages;
	
    @FXML
    private HBox paneChooseFile;
    
    @FXML
    private HBox paneChooseFolder;
    
    @FXML
    private JFXCheckBox chckKotlin;

	@FXML
	void onSelectButton(ActionEvent event) {
		try {
			if (event.getSource().equals(btnBrowseFile)) {
				File f = showFileChooserDialog();
				if (f != null)
					txtFilePath.setText(f.getAbsolutePath());
			} else if (event.getSource().equals(btnBrowseFolder)) {
				File f = showFolderChooserDialog();
				if (f != null)
					txtFolderPath.setText(f.getAbsolutePath());
			} else if (event.getSource().equals(btnCancel)) {
				Platform.exit();
			} else if (event.getSource().equals(btnStart)) {
				if (rdbtnCheckFile.isSelected()) {
					if (txtFilePath.getText().isEmpty())
						throw new Exception("A file must be chosen.");
					File f = new File(txtFilePath.getText());
					checkFile(f);
					List<File> file = new ArrayList<File>();
					file.add(f);
					LineCounter counter = new LineCounter(file);
					progressBar.progressProperty().bind(counter.getLines());
					lblProgressInformation.textProperty().bind(counter.getLines().asString());
					progressBar.setVisible(true);
					counter.setOnSucceeded(e -> {
						btnStart.setDisable(false);
						lblProgressInformation.textProperty().unbind();
						progressBar.progressProperty().unbind();
					});
					counter.setOnFailed(e -> {
						btnStart.setDisable(false);
						lblProgressInformation.textProperty().unbind();
						progressBar.progressProperty().unbind();
					});
					btnStart.setDisable(true);
					new Thread(counter).start();

				} else {
						if (txtFolderPath.getText().isEmpty())
							throw new Exception("A directory must be chosen.");
						File f = new File(txtFolderPath.getText());
						checkFolder(f);
						
						FileCounter counter = new FileCounter(f, this.getFileExtensions());
						progressBar.progressProperty().bind(counter.getDoubleProp());
						progressBar.setVisible(true);
						lblProgressInformation.setText("Searching Files...");
						counter.setOnFailed(e -> {
							btnStart.setDisable(false);
						});
						counter.setOnSucceeded(e -> {
							LineCounter lCounter = new LineCounter(counter.getValue());
							progressBar.progressProperty().unbind();
							progressBar.progressProperty().bind(lCounter.getLines());
							lblProgressInformation.textProperty().bind(lCounter.getLines().asString());
							lCounter.setOnSucceeded(a -> {
								btnStart.setDisable(false);
								lblProgressInformation.textProperty().unbind();
								progressBar.progressProperty().unbind();
							});
							lCounter.setOnFailed( a -> {
								btnStart.setDisable(false);
								lblProgressInformation.textProperty().unbind();
								progressBar.progressProperty().unbind();
							});
							new Thread(lCounter).start();
						});
						new Thread(counter).start();
				}
			}
		} catch (Exception e) {
			ExceptionHandler.hanldeUnexpectedException(e);
		}
	}

	@FXML
	void rdbtnClicked(ActionEvent event) {
		if (event.getSource().equals(rdbtnCheckFile)) {
			paneOthers.setDisable(true);
			paneProgrammingLanguages.setDisable(true);
			paneChooseFile.setDisable(false);
			paneChooseFolder.setDisable(true);
		} else if (event.getSource().equals(rdbtnCheckFolder)) {
			paneOthers.setDisable(false);
			paneProgrammingLanguages.setDisable(false);
			paneChooseFile.setDisable(true);
			paneChooseFolder.setDisable(false);	
		}
	}

	private void checkFolder(File f) throws Exception {
		if (!f.exists())
			throw new FileNotFoundException("Directory does not exist.");
		if (!f.isDirectory())
			throw new Exception("Directory must not be a file.");
	}

	private void checkFile(File f) throws Exception {
		if (!f.exists())
			throw new FileNotFoundException("File does not exist.");
		if (f.isDirectory())
			throw new Exception("File must not be a directory.");
	}

	private File showFileChooserDialog() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
		File file = fileChooser.showOpenDialog(btnBrowseFile.getScene().getWindow());
		if (file != null) {
			return file;
		}
		return null;
	}

	private File showFolderChooserDialog() {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Choose a folder");
		File defaultDirectory = new File(System.getProperty("user.dir"));
		chooser.setInitialDirectory(defaultDirectory);
		File file = chooser.showDialog(btnBrowseFile.getScene().getWindow());
		if (file != null) {
			return file;
		}
		return null;
	}

	private List<String> getFileExtensions() throws Exception {
		List<String> ret = new ArrayList<String>();
		if (chckC.isSelected())
			ret.add(C);
		if (chbxJava.isSelected())
			ret.add(JAVA);
		if (chckCpp.isSelected())
			ret.add(CPP);
		if (chckCs.isSelected())
			ret.add(C_SHARP);
		if (chckCss.isSelected())
			ret.add(CSS);
		if (chckFxml.isSelected())
			ret.add(FXML);
		if (chckGo.isSelected())
			ret.add(GOLANG);
		if (chckHtml.isSelected())
			ret.add(HTML);
		if (chckJs.isSelected())
			ret.add(JAVASCRIPT);
		if(chckKotlin.isSelected())
			ret.add(KOTLIN);
		if (chckMarkdown.isSelected()) {
			ret.add(MARKDOWN);
			ret.add(MARKDOWN_2);
		}
		if (chckPhp.isSelected())
			ret.add(PHP);
		if (chckPy.isSelected())
			ret.add(PYTHON);
		if (chckRb.isSelected())
			ret.add(RUBY);
		if (chckScss.isSelected())
			ret.add(SCSS);
		if (chckSql.isSelected())
			ret.add(SQL);
		if (chckSwift.isSelected())
			ret.add(SWIFT);
		if (chckTs.isSelected())
			ret.add(TYPESCRIPT);
		if (chckXaml.isSelected())
			ret.add(XAML);
		if (chckXml.isSelected())
			ret.add(XML);
		if (ret.isEmpty())
			throw new Exception("At least 1 file extension has to be selected");
		return ret;
	}

}
