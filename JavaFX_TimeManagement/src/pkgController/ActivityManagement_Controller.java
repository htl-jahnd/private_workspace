package pkgController;

import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pkgData.Activity;
import pkgData.Database;
import pkgMisc.ExceptionHandler;

public class ActivityManagement_Controller
{

	@FXML
	private JFXListView<Activity> lstViewActivities;

	@FXML
	private JFXTextField txtActivityName;

	@FXML
	private HBox hboxEditDelete;

	@FXML
	private JFXButton btnDeleteEntry;

	@FXML
	private JFXButton btnEditActivity;

	@FXML
	private HBox hboxSaveCancel;

	@FXML
	private JFXButton btnCancelActivity;

	@FXML
	private JFXButton btnSaveActivity;

	@FXML
	private JFXButton btnCancel;

	@FXML
	private JFXButton btnSave;

	@FXML
	private JFXButton btnAddActivity;

	private ObservableList<Activity> listActivities;
	private Database db;
	public static Stage mainStage;
	private Activity currentActivity;
	private boolean isAdding;

	@FXML
	void initialize()
	{

		try
		{
			db = Database.newInstance();
			db.setAutoCommit(false);
			listActivities = FXCollections.observableArrayList();
			lstViewActivities.setItems(listActivities);

			db.selectAllActivities();
			listActivities.setAll(db.getAllActivities());

			mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent we)
				{
					try
					{
						db.setAutoCommit(true);
					} catch (SQLException e)
					{
						handleException(e);
						mainStage.close();
					}
				}
			});
		} catch (Exception e)

		{
			handleException(e);
		}

	}

	@FXML
	void onSelectButton(ActionEvent event)
	{

		try
		{
			if (event.getSource().equals(btnCancel))
			{
				showConfirmCancelDialog(); // TODO
				mainStage.close();
			} else if (event.getSource().equals(btnCancelActivity))
			{
				hboxSaveCancel.setVisible(false);
				hboxEditDelete.setVisible(true);
				setTextFieldsEditable(false);
				txtActivityName.setText(currentActivity.getName());
				btnAddActivity.setDisable(false);
			} else if (event.getSource().equals(btnDeleteEntry))
			{
				clearTextFields();
				db.deleteActivity(currentActivity);
				updateListActivities();
			} else if (event.getSource().equals(btnEditActivity))
			{
				hboxSaveCancel.setVisible(true);
				hboxEditDelete.setVisible(false);
				setTextFieldsEditable(true);
				btnAddActivity.setDisable(true);
			} else if (event.getSource().equals(btnSave))
			{
				db.commit();
				mainStage.close();

			} else if (event.getSource().equals(btnSaveActivity))
			{
				hboxSaveCancel.setVisible(false);
				hboxEditDelete.setVisible(true);
				setTextFieldsEditable(false);
				if (isAdding)
				{
					currentActivity = db.addActivity(txtActivityName.getText());
				} else
				{
					currentActivity.setName(txtActivityName.getText());
					db.updateActivity(currentActivity);
				}
				isAdding = false;
				btnAddActivity.setDisable(false);
				updateListActivities();
			} else if (event.getSource().equals(btnAddActivity))
			{
				hboxSaveCancel.setVisible(true);
				hboxEditDelete.setVisible(false);
				btnAddActivity.setDisable(true);
				setTextFieldsEditable(true);
				isAdding = true;
			}
		} catch (SQLException e)
		{
			handleException(e);
		}
	}

	private void showConfirmCancelDialog()
	{
		// TODO Auto-generated method stub

	}

	private void clearTextFields()
	{
		txtActivityName.clear();
	}

	private void updateListActivities() throws SQLException
	{
		db.selectAllActivities();
		listActivities.setAll(db.getAllActivities());
	}

	private void setTextFieldsEditable(boolean b)
	{
		txtActivityName.setEditable(b);
	}

	@FXML
	void onSelectListActivity(MouseEvent event)
	{
		if (lstViewActivities.getSelectionModel().getSelectedItem() != null)
		{
			currentActivity = lstViewActivities.getSelectionModel().getSelectedItem();
			txtActivityName.setText(currentActivity.getName());
		}
	}

	private void handleException(Exception e)
	{
		ExceptionHandler.hanldeUnexpectedException(e);
	}

}
