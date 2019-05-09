package pkgController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import pkgData.Activity;
import pkgData.Database;
import pkgData.Entry;
import pkgData.User;
import pkgMisc.BooleanCell;
import pkgMisc.ExceptionHandler;

public class UserManagement_Controller
{

	@FXML
	private TableView<User> tableUsers;

	@FXML
	private TableColumn<User, String> colUsername;

	@FXML
	private TableColumn<User, Boolean> colIsAdmin;

	@FXML
	private JFXButton btnSave;

	@FXML
	private JFXButton btnCancel;
	
	@FXML
	private JFXButton btnAddUser;

	public static Stage mainStage;
	private Database db;
	private ObservableList<User> listUsers;
	private List<User> usersToDelete;
	

	@FXML
	void initialize()
	{
		try
		{
			db = Database.newInstance();
			db.setAutoCommit(false);
			
			listUsers = FXCollections.observableArrayList();
			usersToDelete = new ArrayList<User>();
			tableUsers.setItems(listUsers);
			colUsername.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
			colIsAdmin.setCellValueFactory(new PropertyValueFactory<User, Boolean>("isadmin"));
			
			Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>> booleanCellFactory = 
		            new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
		            @Override
		                public TableCell<User, Boolean> call(TableColumn<User, Boolean> p) {
		                    return new BooleanCell();
		            }
		        };
			colIsAdmin.setCellFactory(booleanCellFactory);
			
			tableUsers.setRowFactory(
				    new Callback<TableView<User>, TableRow<User>>() {
				        @Override
				        public TableRow<User> call(TableView<User> tableView) {
				            final TableRow<User> row = new TableRow<>();
				            final ContextMenu rowMenu = new ContextMenu();
				            MenuItem removeItem = new MenuItem("Delete");
				            removeItem.setOnAction(new EventHandler<ActionEvent>() {
								@Override
				                public void handle(ActionEvent event) {
				                   usersToDelete.add(row.getItem());
				                   listUsers.remove(row.getItem());
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
	void onSelectButton(ActionEvent event)
	{
		try
		{
			if (event.getSource().equals(btnCancel))
			{
				mainStage.close();
			} else if (event.getSource().equals(btnSave))
			{
				db.updateListUsers(listUsers);
				db.deleteListUsers(usersToDelete);
				db.commit();
			}
			else if(event.getSource().equals(btnAddUser)) {
				showAddUserUI();
			}
		} catch (Exception ex)
		{
			handleException(ex);
		}
	}

	private void showAddUserUI() throws IOException
	{
		Stage st = new Stage();
		AddUser_Controller.mainStage = st;
		StackPane root = (StackPane) FXMLLoader
				.load(getClass().getResource("/pkgMain/res/fxml/AddUser.fxml"));
		Scene scene = new Scene(root);
		st.initOwner(tableUsers.getScene().getWindow());
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
							db.selectAllUsers();
							listUsers.setAll(db.getAllUsers());
						} catch (Exception e)
						{
							handleException(e);
						}
					}
				});
	}

	@FXML
	void onEditCommitIsAdmin(CellEditEvent<User, Boolean> event)
	{
		(event.getTableView().getItems().get(event.getTablePosition().getRow())).setAdmin(event.getNewValue());
	}

	@FXML
	void onEditCommitUsername(CellEditEvent<User, String> event)
	{
		(event.getTableView().getItems().get(event.getTablePosition().getRow())).setUsername(event.getNewValue());
	}

	private void handleException(Exception e)
	{
		ExceptionHandler.hanldeUnexpectedException(e);
	}

}
