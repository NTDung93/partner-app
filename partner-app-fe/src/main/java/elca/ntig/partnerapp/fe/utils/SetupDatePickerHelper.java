package elca.ntig.partnerapp.fe.utils;

import javafx.scene.control.Control;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class SetupDatePickerHelper {
    public void setupDatePickerImpl(DatePicker datePickerValue) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        datePickerValue.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? formatter.format(date) : null;
            }

            @Override
            public LocalDate fromString(String string) {
                try {
                    return (string != null && !string.isEmpty()) ? LocalDate.parse(string, formatter) : null;
                } catch (DateTimeParseException e) {
                    return null;
                }
            }
        });

        datePickerValue.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #7abb81;");
                }
            }
        });
    }
}
