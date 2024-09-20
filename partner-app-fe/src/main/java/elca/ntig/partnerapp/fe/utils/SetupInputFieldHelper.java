package elca.ntig.partnerapp.fe.utils;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class SetupInputFieldHelper {
    public void setupDatePickerImpl(DatePicker datePickerValue) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

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

    public void setupIdeNumberFieldImpl(TextField ideNumberValue) {
        TextFormatter<String> ideFormatter = new TextFormatter<>(change -> {
            String ideNumber = change.getControlNewText().toUpperCase().replaceAll("[^A-Z0-9]", "");

            if (ideNumber.length() > 12) {
                ideNumber = ideNumber.substring(0, 12);
            }

            String formattedText = formatIdeNumber(ideNumber);

            change.setText(formattedText);
            change.setRange(0, change.getControlText().length());

            int caretPos = formattedText.length();
            change.setCaretPosition(caretPos);
            change.setAnchor(caretPos);

            return change;
        });

        ideNumberValue.setTextFormatter(ideFormatter);
    }

    private String formatIdeNumber(String digits) {
        StringBuilder formatted = new StringBuilder();
        int len = digits.length();

        if (len >= 3) {
            formatted.append(digits.substring(0, 3));
            formatted.append('-');
        } else {
            formatted.append(digits);
            return formatted.toString();
        }

        if (len > 3) {
            String suffix = digits.substring(3);

            for (int i = 0; i < suffix.length(); i++) {
                if (i == 3 || i == 6) {
                    formatted.append('.');
                }
                formatted.append(suffix.charAt(i));
            }
        }

        return formatted.toString();
    }

    public void setupAvsNumberFieldImpl(TextField avsNumberValue) {
        TextFormatter<String> avsFormatter = new TextFormatter<>(change -> {
            String digits = change.getControlNewText().replaceAll("\\D", "");

            if (digits.length() > 13) {
                digits = digits.substring(0, 13);
            }

            String formattedText = formatAvsNumber(digits);

            change.setText(formattedText);
            change.setRange(0, change.getControlText().length());

            int caretPos = formattedText.length();
            change.setCaretPosition(caretPos);
            change.setAnchor(caretPos);

            return change;
        });

        avsNumberValue.setTextFormatter(avsFormatter);
    }

    private String formatAvsNumber(String digits) {
        StringBuilder formatted = new StringBuilder();
        int length = digits.length();

        for (int i = 0; i < length; i++) {
            if (i == 3 || i == 7 || i == 11) {
                formatted.append('.');
            }
            formatted.append(digits.charAt(i));
        }

        return formatted.toString();
    }
}
