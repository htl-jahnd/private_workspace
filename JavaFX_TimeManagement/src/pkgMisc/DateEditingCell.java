package pkgMisc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.jfoenix.controls.JFXDatePicker;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import pkgData.Entry;
import pkgMisc.IStaticStrings;

public class DateEditingCell extends TableCell<Entry, LocalDate> implements IStaticStrings{

    private JFXDatePicker datePicker;

    public DateEditingCell() {
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createDatePicker();
            setText(null);
            setGraphic(datePicker);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getDate().format(DateTimeFormatter.ofPattern(DATE_FORMATTER_PATTERN)));
        setGraphic(null);
    }

    @Override
    public void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (datePicker != null) {
                    datePicker.setValue(getDate());
                }
                setText(null);
                setGraphic(datePicker);
            } else {
                setText(getDate().format(DateTimeFormatter.ofPattern(DATE_FORMATTER_PATTERN)));
                setGraphic(null);
            }
        }
    }

    private void createDatePicker() {
        datePicker = new JFXDatePicker(getDate());
        datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        datePicker.setOnKeyReleased((e) -> {
        	if(e.getCode() == KeyCode.ESCAPE) {
        		cancelEdit();
				e.consume();
        	}
        	else if(e.getCode() == KeyCode.ENTER) {
                commitEdit(LocalDate.from(datePicker.getValue()));
                e.consume();
        	}
            
        });
        datePicker.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                commitEdit(LocalDate.from(datePicker.getValue()));
            }
        });
    }

    private LocalDate getDate() {
        return getItem() == null ? LocalDate.now() : getItem();
    }
}
