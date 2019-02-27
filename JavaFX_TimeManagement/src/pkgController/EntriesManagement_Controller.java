
package pkgController;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.sun.xml.internal.ws.handler.HandlerException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import jdk.internal.org.objectweb.asm.Handle;
import pkgData.Activity;
import pkgData.Database;
import pkgData.Entry;
import pkgData.User;
import pkgMisc.ExceptionHandler;

public class EntriesManagement_Controller
{

	@FXML
	private JFXListView<Entry> lstViewEntries;

	@FXML
	private JFXComboBox<User> cmbxUser;

	@FXML
	private JFXTextArea txtEntryMessage;

	@FXML
	private JFXTextField txtEntryTitle;

	@FXML
	private JFXComboBox<Activity> cmbxEntryActivity;

	@FXML
	private JFXTimePicker timeEntryStartTime;

	@FXML
	private JFXTimePicker timeEntryEndTime;

	@FXML
	private JFXDatePicker dateEntryDate;

	@FXML
	private JFXButton btnEntrySave;

	@FXML
	private JFXButton btnEntryCancel;

	@FXML
	private JFXCheckBox chckbxAllUsersEntries;

	@FXML
	private HBox boxUserSelection;

	@FXML
	private HBox hboxEditDelete;

	@FXML
	private JFXButton btnDeleteEntry;

	@FXML
	private JFXButton btnEditEntry;

	@FXML
	private HBox hboxSaveCancel;

	@FXML
	private JFXButton btnCancelEntry;

	@FXML
	private JFXButton btnSaveEntry;

	public static Stage mainStage;
	private Database db;
	private ObservableList<User> listUsers;
	private ObservableList<Activity> listActivities;
	private ObservableList<Entry> listEntries;
	private Entry currentEntry;

	@FXML
	void initialize() throws Exception
	{
		db = Database.newInstance();
		listUsers = FXCollections.observableArrayList();
		listActivities = FXCollections.observableArrayList();
		listEntries = FXCollections.observableArrayList();

		cmbxEntryActivity.setItems(listActivities);
		cmbxUser.setItems(listUsers);
		lstViewEntries.setItems(listEntries);

		db.selectAllActivities();
		listActivities.setAll(db.getAllActivities());
		db.selectAllUsers();
		listUsers.setAll(db.getAllUsers());
		db.selectAllEntries();
		listEntries.setAll(db.getAllEntries());
	}

	@FXML
	void onSelectButton(ActionEvent event)
	{
		try
		{
			if (event.getSource().equals(btnCancelEntry))
			{
				hboxSaveCancel.setVisible(false);
				hboxEditDelete.setVisible(true);
				setTextFieldsDisable(false);
			} else if (event.getSource().equals(btnSaveEntry))
			{
				hboxSaveCancel.setVisible(false);
				hboxEditDelete.setVisible(true);
				setTextFieldsDisable(false);

				LocalDateTime dtEnd = timeEntryEndTime.getValue().atDate(dateEntryDate.getValue());
				Timestamp tmpEnd = Timestamp.valueOf(dtEnd);

				LocalDateTime dtStart = timeEntryStartTime.getValue().atDate(dateEntryDate.getValue());
				Timestamp tmpStart = Timestamp.valueOf(dtStart);

				currentEntry.setActivity(cmbxEntryActivity.getValue());
				currentEntry.setMessage(txtEntryMessage.getText());
				currentEntry.setTimestampEnd(tmpEnd);
				currentEntry.setTimestampStart(tmpStart);
				currentEntry.setTitle(txtEntryTitle.getText());

				db.updateEntry(currentEntry);
				updateListEntries();
			} else if (event.getSource().equals(btnDeleteEntry))
			{
				clearTextFields();
				int pos = listEntries.indexOf(currentEntry);
				
				db.deleteEntry(currentEntry);
				updateListEntries();
			} else if (event.getSource().equals(btnEditEntry))
			{
				hboxSaveCancel.setVisible(true);
				hboxEditDelete.setVisible(false);
				setTextFieldsDisable(true);
			}
		} catch (Exception e)
		{
			handleException(e);
		}

	}

	private void updateListEntries() throws Exception
	{
		if (chckbxAllUsersEntries.isSelected()) {
			db.selectAllEntries();
			listEntries.setAll(db.getAllEntries());
		}
			
		else {
			db.selectAllUsers();
			listEntries.setAll(db.getUserEntries());
		}
			
	}

	private void clearTextFields()
	{
		txtEntryMessage.clear();
		txtEntryTitle.clear();
		cmbxEntryActivity.getSelectionModel().clearSelection();
		timeEntryEndTime.setValue(LocalTime.of(8, 00));
		timeEntryStartTime.setValue(LocalTime.of(13, 00));
		dateEntryDate.setValue(LocalDate.now());
	}

	private void setTextFieldsDisable(boolean b)
	{
		txtEntryMessage.setEditable(b);
		txtEntryTitle.setEditable(b);
		cmbxEntryActivity.setDisable(b);
		timeEntryEndTime.setEditable(b);
		timeEntryStartTime.setEditable(b);
		dateEntryDate.setEditable(b);
	}

	@FXML
	void onSelectComboBox(ActionEvent event)
	{
		try
		{
			if (event.getSource().equals(cmbxUser))
			{
				db.selectUserEntries(cmbxUser.getSelectionModel().getSelectedItem());
				listEntries.setAll(db.getUserEntries());
			}
		} catch (Exception e)
		{
			handleException(e);
		}
	}

	@FXML
	void onSelectCheckbox(ActionEvent event)
	{
		if (event.getSource().equals(chckbxAllUsersEntries))
		{
			if (chckbxAllUsersEntries.isSelected())
			{
				boxUserSelection.setDisable(true);
				listEntries.setAll(db.getAllEntries());
			} else
			{
				boxUserSelection.setDisable(false);
				listEntries.clear();
			}
		}
	}

	@FXML
	void onSelectListUsers(MouseEvent event)
	{
		if (lstViewEntries.getSelectionModel().getSelectedItem() != null)
		{
			doFillTextFields(lstViewEntries.getSelectionModel().getSelectedItem());
			currentEntry = lstViewEntries.getSelectionModel().getSelectedItem();
		}
	}

	private void doFillTextFields(Entry etr)
	{
		txtEntryMessage.setText(etr.getMessage());
		dateEntryDate.setValue(etr.getTimestampStart().toLocalDateTime().toLocalDate());
		timeEntryEndTime.setValue(etr.getTimestampEnd().toLocalDateTime().toLocalTime());
		timeEntryStartTime.setValue(etr.getTimestampStart().toLocalDateTime().toLocalTime());
		cmbxEntryActivity.getSelectionModel().select(etr.getActivity());
		txtEntryTitle.setText(etr.getTitle());
	}

	private void handleException(Exception e)
	{
		ExceptionHandler.hanldeUnexpectedException(e);
	}

}
