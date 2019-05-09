
package pkgController;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import pkgData.Activity;
import pkgData.Database;
import pkgData.Entry;
import pkgData.User;
import pkgMisc.ExceptionHandler;
import pkgMisc.IStaticStrings;
import pkgMisc.TextAreaTableCell;
import pkgMisc.TimePickerTableCell;
import pkgMisc.ActivityComboBoxEditingCell;
import pkgMisc.DateEditingCell;

public class EntriesManagement_Controller implements IStaticStrings
{

	@FXML
	private StackPane pane;

	@FXML
	private HBox boxUserSelection;

	@FXML
	private JFXComboBox<User> cmbxUser;

	@FXML
	private JFXButton btnSave;

	@FXML
	private JFXButton btnCancel;

	@FXML
	private JFXCheckBox chckbxAllUsersEntries;

	@FXML
	private HBox hboxSaveCancel;

	@FXML
	private JFXButton btnCancelEntry;

	@FXML
	private JFXButton btnSaveEntry;

	@FXML
	private TableView<Entry> tableEntries;

	@FXML
	private TableColumn<Entry, User> colUser;

	@FXML
	private TableColumn<Entry, Activity> colActivity;

	@FXML
	private TableColumn<Entry, String> colTitle;

	@FXML
	private TableColumn<Entry, String> colMessage;

	@FXML
	private TableColumn<Entry, LocalDate> colDate;

	@FXML
	private TableColumn<Entry, LocalTime> colStartTime;

	@FXML
	private TableColumn<Entry, LocalTime> colEndTime;

	public static Stage mainStage;
	private Database db;
	private ObservableList<User> listUsers;
	private ObservableList<Activity> listActivities;
	private ObservableList<Entry> listEntries;
	private ArrayList<Entry> entriesToDelete;

	@FXML
	void initialize() throws Exception
	{
		try
		{
			db = Database.newInstance();
			db.setAutoCommit(false);
			listUsers = FXCollections.observableArrayList();
			listActivities = FXCollections.observableArrayList();
			listEntries = FXCollections.observableArrayList();
			entriesToDelete = new ArrayList<Entry>();

			cmbxUser.setItems(listUsers);
			tableEntries.setItems(listEntries);

			db.selectAllActivities();
			listActivities.setAll(db.getAllActivities());
			db.selectAllUsers();
			listUsers.setAll(db.getAllUsers());
			db.selectAllEntries();
			listEntries.setAll(db.getAllEntries());

			colActivity.setCellValueFactory(new PropertyValueFactory<Entry, Activity>("activity"));
			colActivity.setCellFactory(
					(TableColumn<Entry, Activity> param) -> new ActivityComboBoxEditingCell(listActivities));
			colMessage.setCellValueFactory(new PropertyValueFactory<Entry, String>("message"));
			colMessage.setCellFactory(TextAreaTableCell.forTableColumn());

			colTitle.setCellValueFactory(new PropertyValueFactory<Entry, String>("title"));
			colTitle.setCellFactory(TextFieldTableCell.forTableColumn());

			colUser.setCellValueFactory(new PropertyValueFactory<Entry, User>("user"));

			colDate.setCellValueFactory(new PropertyValueFactory<Entry, LocalDate>("date"));
			colDate.setCellFactory((TableColumn<Entry, LocalDate> param) -> new DateEditingCell());

			colEndTime.setCellValueFactory(new PropertyValueFactory<Entry, LocalTime>("timeEnd"));
			colEndTime.setCellFactory(TimePickerTableCell.forTableColumn());
			colStartTime.setCellValueFactory(new PropertyValueFactory<Entry, LocalTime>("timeStart"));
			colStartTime.setCellFactory(TimePickerTableCell.forTableColumn());
			
			
			tableEntries.setRowFactory(
				    new Callback<TableView<Entry>, TableRow<Entry>>() {
				        @Override
				        public TableRow<Entry> call(TableView<Entry> tableView) {
				            final TableRow<Entry> row = new TableRow<>();
				            final ContextMenu rowMenu = new ContextMenu();
				            MenuItem removeItem = new MenuItem("Delete");
				            removeItem.setOnAction(new EventHandler<ActionEvent>() {

				                @Override
				                public void handle(ActionEvent event) {
				                   entriesToDelete.add(row.getItem());
				                   listEntries.remove(row.getItem());
				                }
				            });
				            rowMenu.getItems().addAll(removeItem);

				            // only display context menu for non-null items:
				            row.contextMenuProperty().bind(
				              Bindings.when(Bindings.isNotNull(row.itemProperty()))
				              .then(rowMenu)
				              .otherwise((ContextMenu)null));
				            return row;
				    }
				});
			

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
	void onEditColActivityFinished(CellEditEvent<Entry, Activity> event)
	{
		(event.getTableView().getItems().get(event.getTablePosition().getRow())).setActivity(event.getNewValue());
	}

	@FXML
	void onEditColDateFinished(CellEditEvent<Entry, LocalDate> event)
	{
		(event.getTableView().getItems().get(event.getTablePosition().getRow())).setDate(event.getNewValue());
	}

	@FXML
	void onEditColEndTimeFinished(CellEditEvent<Entry, LocalTime> event)
	{
		(event.getTableView().getItems().get(event.getTablePosition().getRow())).setTimeEnd(event.getNewValue());
	}

	@FXML
	void onEditColMessageFinished(CellEditEvent<Entry, String> event)
	{
		(event.getTableView().getItems().get(event.getTablePosition().getRow())).setMessage(event.getNewValue());
	}

	@FXML
	void onEditColStartTimeFinished(CellEditEvent<Entry, LocalTime> event)
	{
		(event.getTableView().getItems().get(event.getTablePosition().getRow())).setTimeStart(event.getNewValue());
	}

	@FXML
	void onEditColTitleFinished(CellEditEvent<Entry, String> event)
	{
		(event.getTableView().getItems().get(event.getTablePosition().getRow())).setTitle(event.getNewValue());
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
			} else if (event.getSource().equals(btnSave))
			{
				db.updateListEntries(listEntries);
				db.deleteListEntries(entriesToDelete);
				db.commit();
				
				mainStage.close();
			}
		} catch (Exception e)
		{
			handleException(e);
		}

	}

	private void showConfirmCancelDialog()
	{
		// TODO
	}

	private void updateListEntries() throws Exception
	{
		if (chckbxAllUsersEntries.isSelected())
		{
			db.selectAllEntries();
			listEntries.setAll(db.getAllEntries());
		} else
		{
			db.selectAllUsers();
			listEntries.setAll(db.getUserEntries());
		}

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

	private void handleException(Exception e)
	{
		ExceptionHandler.hanldeUnexpectedException(e);
	}

}
