package pkgController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import pkgData.Activity;
import pkgData.Database;
import pkgData.Entry;
import pkgData.TimerWorker;
import pkgMisc.ExceptionHandler;

public class Dashboard_Controller
{

	@FXML
	private MenuItem mntmShowStatistics;

	@FXML
	private MenuItem mntmShowAllEntries;

	@FXML
	private JFXButton btnStartStop;

	@FXML
	private JFXComboBox<Activity> cmbxActivity;

	@FXML
	private Label lblTimer;

	@FXML
	private JFXTextArea txtMessage;

	@FXML
	private MenuItem mtnmActivityManagement;

	private Database db;
	private ObservableList<Activity> listActivities;
	private boolean started = false;
	TimerWorker timer;
	Thread workerThread;

	@FXML
	void initialize()
	{
		try
		{
			db = Database.newInstance();
			db.selectAllActivities();
			listActivities = FXCollections.observableArrayList();
			listActivities.setAll(db.getAllActivities());
			cmbxActivity.setItems(listActivities);
		} catch (Exception e)
		{
			ExceptionHandler.hanldeUnexpectedException(e);
		}
	}

	@FXML
	void onSelectButton(ActionEvent event)
	{
		try
		{
			if (event.getSource().equals(btnStartStop))
			{
				if (!started)
				{
					checkInput();
					started = true;

					btnStartStop.setText("Stop");
					btnStartStop.setStyle("-fx-background-color:#EC7063");

					startTimer();
				} else
				{
					started = false;
					btnStartStop.setText("Start");
					btnStartStop.setStyle("-fx-background-color:#82E0AA;");
					stopTimer();
					Entry entr = new Entry(0, cmbxActivity.getSelectionModel().getSelectedItem(), db.getCurrentUser(),
							timer.getStartTime().toLocalDateTime().toLocalDate(),
							timer.getStartTime().toLocalDateTime().toLocalTime(),
							timer.getEndTime().toLocalDateTime().toLocalTime(), "Tests", txtMessage.getText());
					db.addEntry(entr);
				}
			}
		} catch (Exception e)
		{
			handleException(e);
		}
	}

	private void checkInput() throws Exception
	{
		if (cmbxActivity.getSelectionModel().getSelectedItem() == null)
			throw new Exception("PLease select an Activity");
		if (txtMessage.getText().trim().isEmpty())
			throw new Exception("Please enter a message");
	}

	public void setTime(String value)
	{
		lblTimer.setText(value);
	}

	private void startTimer()
	{
		timer = new TimerWorker(this);
		workerThread = new Thread(timer);
		lblTimer.textProperty().bind(timer.getSp());
		workerThread.start();
	}

	private void stopTimer() throws InterruptedException
	{
		lblTimer.textProperty().unbind();
		timer.setEnd();
		workerThread.join();
	}

	@FXML
	void onSelectMenu(ActionEvent event)
	{
		try
		{
			if (event.getSource().equals(mntmShowAllEntries))
			{
				Stage st = new Stage();
				EntriesManagement_Controller.mainStage = st;
				StackPane root = (StackPane) FXMLLoader
						.load(getClass().getResource("/pkgMain/res/fxml/EntriesManagementAdmin.fxml"));
				Scene scene = new Scene(root);
				st.initOwner(txtMessage.getScene().getWindow());
				st.initStyle(StageStyle.UTILITY);
				st.initModality(Modality.APPLICATION_MODAL);
				st.setResizable(false);
				st.setScene(scene);
				st.show();
				st.requestFocus();
			} else if (event.getSource().equals(mtnmActivityManagement))
			{
				Stage st = new Stage();
				ActivityManagement_Controller.mainStage = st;
				StackPane root = (StackPane) FXMLLoader
						.load(getClass().getResource("/pkgMain/res/fxml/ActivityManagementAdmin.fxml"));
				Scene scene = new Scene(root);
				st.initOwner(txtMessage.getScene().getWindow());
				st.initStyle(StageStyle.UTILITY);
				st.initModality(Modality.APPLICATION_MODAL);
				st.setResizable(false);
				st.setScene(scene);
				st.show();
				st.requestFocus();
				st.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent we)
					{
						try
						{
							db.selectAllActivities();
							listActivities.setAll(db.getAllActivities());
						} catch (Exception e)
						{
							handleException(e);
						}
					}
				});
			} else if (event.getSource().equals(mntmShowStatistics))
			{
				Stage st = new Stage();
				EntriesManagement_Controller.mainStage = st;
				StackPane root = (StackPane) FXMLLoader
						.load(getClass().getResource("/pkgMain/res/fxml/StatisticsAdmin.fxml"));
				Scene scene = new Scene(root);
				st.initOwner(txtMessage.getScene().getWindow());
				st.initStyle(StageStyle.UTILITY);
				st.initModality(Modality.APPLICATION_MODAL);
				st.setResizable(false);
				st.setScene(scene);
				st.show();
				st.requestFocus();
			}
		} catch (Exception e)
		{
			handleException(e);
		}
	}

	public void handleException(Exception e)
	{
		ExceptionHandler.hanldeUnexpectedException(e);
	}

}
