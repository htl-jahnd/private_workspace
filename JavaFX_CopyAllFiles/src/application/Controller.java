package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import pkgData.ExceptionHandler;
import pkgData.Extraction;
import pkgExceptions.InvalidDestinationPathException;
import pkgExceptions.InvalidPathException;
import pkgExceptions.InvalidSourcePathException;

public class Controller implements Initializable
{
    @FXML
    private Button btnFileChooserFrom;
    @FXML
    private Button btnFileChooserTo;
    @FXML
    private Button btnExit;
    @FXML
    private Button btnStart;

    @FXML
    private TextField txtFromDirectory;
    @FXML
    private TextField txtToDirectory;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Label lblCopyStatus;

    private Extraction ext;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
	// TODO Auto-generated method stub

    }

    public void fileChooserToClicked(ActionEvent event)
    {
	String path = showFileChooser();
	if (path != null)
	{
	    txtToDirectory.setText(path);
	}

    }

    public void fileChooserFromClicked(ActionEvent event)
    {
	String path = showFileChooser();
	if (path != null)
	{
	    txtFromDirectory.setText(path);
	}

    }

    public void start(ActionEvent event)
    {
	try
	{
	    doCheckInput();
	    doStartCopying();
	    txtFromDirectory.setStyle("-fx-background-color:WHITE");
	    txtToDirectory.setStyle("-fx-background-color: WHITE");
	} catch (InvalidPathException e)
	{
	    txtFromDirectory.setStyle("-fx-background-color: RED");
	    txtToDirectory.setStyle("-fx-background-color:RED");
	    ExceptionHandler.hanldeException(e);
	} catch (InvalidSourcePathException e)
	{
	    txtFromDirectory.setStyle("-fx-background-color:RED");
	    ExceptionHandler.hanldeException(e);
	} catch (InvalidDestinationPathException e)
	{
	    txtToDirectory.setStyle("-fx-background-color:RED");
	    ExceptionHandler.hanldeException(e);
	} catch (Exception ex)
	{
	    ExceptionHandler.hanldeException(ex);
	}
    }

    public void exit(ActionEvent event)
    {
	System.exit(0);

    }

    public String showFileChooser()
    {
	final DirectoryChooser directoryChooser = new DirectoryChooser();
	final File selectedDirectory = directoryChooser.showDialog(null);
	return selectedDirectory != null ? selectedDirectory.getAbsolutePath() : null;

    }

    private void doStartCopying() throws IOException
    {
	lblCopyStatus.setMinWidth(250);
	btnStart.setDisable(true);
	String src = txtFromDirectory.getText();
	String dest = txtToDirectory.getText();
	ext = new Extraction(src, dest);

	progressBar.progressProperty().unbind();
	progressBar.progressProperty().bind(ext.progressProperty());

	progressIndicator.progressProperty().unbind();
	progressIndicator.progressProperty().bind(ext.progressProperty());

	lblCopyStatus.textProperty().unbind();
	lblCopyStatus.textProperty().bind(ext.messageProperty());
	// When completed tasks
	ext.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {
	    @Override
	    public void handle(WorkerStateEvent t)
	    {
		ArrayList<File> copied = ext.getValue();
		lblCopyStatus.textProperty().unbind();
		lblCopyStatus.setText("Copied: " + copied.size() + " files");
		btnStart.setDisable(false);
	    }
	});

	new Thread(ext).start();
    }

    private void doCheckInput() throws InvalidPathException, InvalidSourcePathException, InvalidDestinationPathException
    {
	String src = txtFromDirectory.getText();
	String dest = txtToDirectory.getText();
	Path srcPath = Paths.get(src);
	Path destPath = Paths.get(dest);
	// checking both paths
	if (src.equals(dest))
	    throw new InvalidPathException("ERROR: Source path and destination path must not be the same");
	else if (src.isEmpty() && dest.isEmpty())
	    throw new InvalidPathException("ERROR: Enter a source path and destination path");
	// checking soruce path
	else if (src.isEmpty())
	    throw new InvalidSourcePathException("ERROR: Enter a source path");
	else if (!Files.exists(srcPath))
	    throw new InvalidSourcePathException("ERROR: Enter a valid source path");
	else if (!Files.isDirectory(srcPath))
	    throw new InvalidSourcePathException("ERROR: Source path must be a directory");
	// Checking destination path
	else if (dest.isEmpty())
	    throw new InvalidDestinationPathException("ERROR: Enter a destination path");
	else if (!Files.exists(destPath))
	    throw new InvalidDestinationPathException("ERROR: Enter a valid destination path");
	else if (!Files.isDirectory(destPath))
	    throw new InvalidDestinationPathException("ERROR: Destination path must be a directory");

    }

}
