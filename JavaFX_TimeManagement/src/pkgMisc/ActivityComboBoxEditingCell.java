package pkgMisc;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.ListCell;
import pkgData.Activity;
import pkgData.Entry;

public class ActivityComboBoxEditingCell extends TableCell<Entry, Activity>
{

	private JFXComboBox<Activity> comboBox;
	private ObservableList<Activity> listData;

	public ActivityComboBoxEditingCell(ObservableList<Activity> lstAct)
	{
		listData = FXCollections.observableArrayList();
		listData.setAll(lstAct);
	}

	@Override
	public void startEdit()
	{
		if (!isEmpty())
		{
			super.startEdit();
			createComboBox();
			setText(null);
			setGraphic(comboBox);
		}
	}

	@Override
	public void cancelEdit()
	{
		super.cancelEdit();

		setText(getTyp().getName());
		setGraphic(null);
	}

	@Override
	public void updateItem(Activity item, boolean empty)
	{
		super.updateItem(item, empty);

		if (empty)
		{
			setText(null);
			setGraphic(null);
		} else
		{
			if (isEditing())
			{
				if (comboBox != null)
				{
					comboBox.setValue(getTyp());
				}
				setText(getTyp().getName());
				setGraphic(comboBox);
			} else
			{
				setText(getTyp().getName());
				setGraphic(null);
			}
		}
	}

	private void createComboBox()
	{
		comboBox = new JFXComboBox<Activity>(listData);
		comboBoxConverter(comboBox);
		comboBox.valueProperty().set(getTyp());
		comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
		comboBox.setOnAction((e) ->
		{
			System.out.println("Committed: " + comboBox.getSelectionModel().getSelectedItem());
			commitEdit(comboBox.getSelectionModel().getSelectedItem());
		});
		comboBox.focusedProperty()
				.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
				{
					if (!newValue)
					{
						commitEdit(comboBox.getSelectionModel().getSelectedItem());
					}
				});
	}

	private void comboBoxConverter(JFXComboBox<Activity> comboBox)
	{
		// Define rendering of the list of values in ComboBox drop down.
		comboBox.setCellFactory((c) ->
		{
			return new ListCell<Activity>() {
				@Override
				protected void updateItem(Activity item, boolean empty)
				{
					super.updateItem(item, empty);

					if (item == null || empty)
					{
						setText(null);
					} else
					{
						setText(item.getName());
					}
				}
			};
		});
	}

	private Activity getTyp()
	{
		return getItem() == null ? listData.get(0) : getItem();
	}
}
