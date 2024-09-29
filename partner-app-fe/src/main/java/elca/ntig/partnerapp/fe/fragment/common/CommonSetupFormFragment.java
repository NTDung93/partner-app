package elca.ntig.partnerapp.fe.fragment.common;

import elca.ntig.partnerapp.common.proto.entity.address.AddressResponseProto;
import elca.ntig.partnerapp.common.proto.entity.address.CreateAddressRequestProto;
import elca.ntig.partnerapp.common.proto.enums.common.StatusProto;
import elca.ntig.partnerapp.fe.common.constant.ClassNameConstant;
import elca.ntig.partnerapp.fe.common.model.AddressTableModel;
import elca.ntig.partnerapp.fe.utils.BindingHelper;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public abstract class CommonSetupFormFragment<T> {

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
                    getStyleClass().add(ClassNameConstant.DISABLED_DATE_PICKER_VALUE);
                }
            }
        });
    }

    public void setupAddressDatePickerImpl(DatePicker datePickerValue) {
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
    }

    public void setupIdeNumberFieldImpl(TextField ideNumberValue) {
        TextFormatter<String> ideFormatter = new TextFormatter<>(change -> {
            if (change.isContentChange()) {
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
            }
            return change;
        });

        ideNumberValue.setTextFormatter(ideFormatter);
    }

    private String formatIdeNumber(String digits) {
        StringBuilder formatted = new StringBuilder();
        int len = digits.length();

        if (len >= 3) {
            formatted.append(digits.substring(0, 3));
            if (len > 3) {
                formatted.append('-');
            }
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
            if (change.isContentChange()) {
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
            }
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

    public void setupPhoneNumberFieldImpl(TextField phoneNumberValue) {
        TextFormatter<String> phoneNumberFormatter = new TextFormatter<>(change -> {
            if (change.isContentChange()) {
                String digits = change.getControlNewText().replaceAll("\\D", "");

                if (digits.length() > 10) {
                    digits = digits.substring(0, 10);
                }

                change.setText(digits);
                change.setRange(0, change.getControlText().length());

                int caretPos = digits.length();
                change.setCaretPosition(caretPos);
                change.setAnchor(caretPos);
            }
            return change;
        });

        phoneNumberValue.setTextFormatter(phoneNumberFormatter);
    }

    public List<StatusProto> getStatusesImpl(CheckBox activeCheckBox, CheckBox inactiveCheckBox) {
        List<StatusProto> statuses = new ArrayList<>();
        if (activeCheckBox.isSelected()) {
            statuses.add(StatusProto.ACTIVE);
        }
        if (inactiveCheckBox.isSelected()) {
            statuses.add(StatusProto.INACTIVE);
        }
        return statuses;
    }

    public void validateRequiredTextField(TextField lastNameValue, Label lastNameErrorLabel) {
        if (StringUtils.isBlank(lastNameValue.getText())) {
            lastNameErrorLabel.setVisible(true);
            if (!lastNameValue.getStyleClass().contains(ClassNameConstant.ERROR_INPUT)) {
                lastNameValue.getStyleClass().add(ClassNameConstant.ERROR_INPUT);
            }
        } else {
            lastNameErrorLabel.setVisible(false);
            lastNameValue.getStyleClass().removeAll(ClassNameConstant.ERROR_INPUT);
        }
    }

    public void validateRequiredComboBox(ComboBox<?> comboBox, Label comboBoxErrorLabel) {
        if (comboBox.getValue() == null) {
            comboBoxErrorLabel.setVisible(true);
            if (!comboBox.getStyleClass().contains(ClassNameConstant.ERROR_INPUT)) {
                comboBox.getStyleClass().add(ClassNameConstant.ERROR_INPUT);
            }
        } else {
            comboBoxErrorLabel.setVisible(false);
            comboBox.getStyleClass().removeAll(ClassNameConstant.ERROR_INPUT);
        }
    }

    public void validateAvsNumber(TextField avsNumberValue, Label avsNumberErrorLabel) {
        String avsNumber = avsNumberValue.getText().trim().replaceAll("\\.", "");
        String avsNumberRegex = "^756\\d{10}$"; // 756.xxxx.xxxx.xx
        if ((StringUtils.isNotBlank(avsNumberValue.getText())) && (!avsNumber.matches(avsNumberRegex))) {
            avsNumberErrorLabel.setVisible(true);
            if (!avsNumberValue.getStyleClass().contains(ClassNameConstant.ERROR_INPUT)) {
                avsNumberValue.getStyleClass().add(ClassNameConstant.ERROR_INPUT);
            }
        } else {
            avsNumberErrorLabel.setVisible(false);
            avsNumberValue.getStyleClass().removeAll(ClassNameConstant.ERROR_INPUT);
        }
    }

    public void validateDate(DatePicker dateValue, Label dateErrorLabel) {
        if ((dateValue.getValue() != null) && (!dateValue.getValue().isBefore(LocalDate.now()))) {
            dateErrorLabel.setVisible(true);
            if (!dateValue.getStyleClass().contains(ClassNameConstant.ERROR_INPUT)) {
                dateValue.getStyleClass().add(ClassNameConstant.ERROR_INPUT);
            }
        } else {
            dateErrorLabel.setVisible(false);
            dateValue.getStyleClass().removeAll(ClassNameConstant.ERROR_INPUT);
        }
    }

    public void validateIdeNumber(TextField ideNumberValue, Label ideNumberErrorLabel) {
        String ideNumber = ideNumberValue.getText().trim().replaceAll("[-.]", "");
        String ideNumberRegex = "^(ADM|CHE)\\d{9}$";
        if ((!ideNumberValue.getText().isEmpty()) && (!ideNumber.matches(ideNumberRegex))) {
            ideNumberErrorLabel.setVisible(true);
            if (!ideNumberValue.getStyleClass().contains(ClassNameConstant.ERROR_INPUT)) {
                ideNumberValue.getStyleClass().add(ClassNameConstant.ERROR_INPUT);
            }
        } else {
            ideNumberErrorLabel.setVisible(false);
            ideNumberValue.getStyleClass().removeAll(ClassNameConstant.ERROR_INPUT);
        }
    }

    public void validatePhoneNumber(TextField phoneNumberValue, Label phoneNumberErrorLabel) {
        String regex = "^\\d{10}$";
        if ((StringUtils.isNotBlank(phoneNumberValue.getText())) && (!phoneNumberValue.getText().matches(regex))) {
            phoneNumberErrorLabel.setVisible(true);
            if (!phoneNumberValue.getStyleClass().contains(ClassNameConstant.ERROR_INPUT)) {
                phoneNumberValue.getStyleClass().add(ClassNameConstant.ERROR_INPUT);
            }
        } else {
            phoneNumberErrorLabel.setVisible(false);
            phoneNumberValue.getStyleClass().removeAll(ClassNameConstant.ERROR_INPUT);
        }
    }

    public void setupDatePickerValue(DatePicker datePickerValue, String dateString) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(dateString, inputFormatter);
        String formattedBirthDate = birthDate.format(outputFormatter);
        datePickerValue.setValue(LocalDate.parse(formattedBirthDate, outputFormatter));
    }

    public void validateRequiredDatePicker(DatePicker datePickerValue, Label datePickerErrorLabel) {
        if (datePickerValue.getValue() == null) {
            datePickerErrorLabel.setVisible(true);
            if (!datePickerValue.getStyleClass().contains(ClassNameConstant.ERROR_INPUT)) {
                datePickerValue.getStyleClass().add(ClassNameConstant.ERROR_INPUT);
            }
        } else {
            datePickerErrorLabel.setVisible(false);
            datePickerValue.getStyleClass().removeAll(ClassNameConstant.ERROR_INPUT);
        }
    }

    public void validateMaxLengthTextField(TextField textField, int maxLength) {
        TextFormatter<String> textFieldFormatter = new TextFormatter<>(change -> {
            if (change.isContentChange()) {
                String value = change.getControlNewText();

                if (value.length() > maxLength) {
                    value = value.substring(0, maxLength);
                }

                change.setText(value);
                change.setRange(0, change.getControlText().length());

                int caretPos = value.length();
                change.setCaretPosition(caretPos);
                change.setAnchor(caretPos);
            }
            return change;
        });

        textField.setTextFormatter(textFieldFormatter);
    }

    public void setCellFactoryDateColumn(TableColumn<T, String> column) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        column.setCellFactory(cell -> new TableCell<T, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    if (StringUtils.isBlank(item)) {
                        setText(null);
                        return;
                    }
                    LocalDate date = LocalDate.parse(item);
                    setText(date.format(dateFormatter));
                }
            }
        });
    }

    public void setTableDefaultMessage(BindingHelper bindingHelper, TableView<T> partnersTable) {
        if (partnersTable == null) {
            return;
        }
        Label empty = new Label();
        bindingHelper.bindLabelTextProperty(empty, "TableFragment.defaultMessage");
        partnersTable.setPlaceholder(empty);
    }

    public void validateRequiredAddress(ObservableList<T> addressList, Button createAddressButoon, Label addressErrorLabel) {
        if (addressList == null || addressList.isEmpty()) {
            addressErrorLabel.setVisible(true);
            if (!createAddressButoon.getStyleClass().contains(ClassNameConstant.ERROR_INPUT)) {
                createAddressButoon.getStyleClass().add(ClassNameConstant.ERROR_INPUT);
            } else {
                addressErrorLabel.setVisible(false);
                createAddressButoon.getStyleClass().removeAll(ClassNameConstant.ERROR_INPUT);
            }
        } else if (addressList.size() >= 1) {

            boolean atLeastOneActiveAddress;

            atLeastOneActiveAddress = addressList.stream()
                    .anyMatch(address -> (StringUtils.isBlank(((AddressTableModel) address).getStatus())) ||
                            ((AddressTableModel) address).getStatus().equals("NULL_STATUS"));

            if (!atLeastOneActiveAddress) {
                atLeastOneActiveAddress = addressList.stream()
                        .anyMatch(address -> ((AddressTableModel) address).getStatus().equals(StatusProto.ACTIVE.name()));
            }

            if (!atLeastOneActiveAddress) {
                addressErrorLabel.setVisible(true);
                if (!createAddressButoon.getStyleClass().contains(ClassNameConstant.ERROR_INPUT)) {
                    createAddressButoon.getStyleClass().add(ClassNameConstant.ERROR_INPUT);
                }
            } else {
                addressErrorLabel.setVisible(false);
                createAddressButoon.getStyleClass().removeAll(ClassNameConstant.ERROR_INPUT);
            }
        }
    }

    public void validateEndDateAfterStartDate(DatePicker startDateValue, DatePicker endDateValue, Label endDateErrorLabel) {
        if (endDateValue.getValue() != null) {
            if (!endDateValue.getValue().isAfter(startDateValue.getValue())) {
                endDateErrorLabel.setVisible(true);
                if (!endDateValue.getStyleClass().contains(ClassNameConstant.ERROR_INPUT)) {
                    endDateValue.getStyleClass().add(ClassNameConstant.ERROR_INPUT);
                }
            } else {
                endDateErrorLabel.setVisible(false);
                endDateValue.getStyleClass().removeAll(ClassNameConstant.ERROR_INPUT);
            }
        }
    }

    public CreateAddressRequestProto getAddressProtoByAddressTableModel(AddressTableModel address, List<CreateAddressRequestProto> createAddressRequestProtoList) {
        // if npaAndLocality is 2000 ABC ZYX then zipCode is 2000 and the rest is locality
        String zipCode = address.getNpaAndLocality().split(" ")[0];
        String locality = address.getNpaAndLocality().substring(zipCode.length() + 1);

        CreateAddressRequestProto addressProto = createAddressRequestProtoList.stream()
                .filter(proto -> proto.getStreet().equals(address.getStreet())
                        && proto.getZipCode().equals(zipCode)
                        && proto.getLocality().equals(locality)
                        && proto.getCanton().name().equals(address.getCanton())
                        && proto.getCountry().name().equals(address.getCountry())
                        && proto.getCategory().name().equals(address.getAddressType())
                        && proto.getValidityStart().equals(address.getValidityStart())
                        && proto.getValidityEnd().equals(address.getValidityEnd()))
                .findFirst().orElse(null);

        return addressProto;
    }

    public AddressResponseProto getAddressResponseProtoByAddressTableModel(AddressTableModel address, List<AddressResponseProto> createAddressRequestProtoList) {
        // if npaAndLocality is 2000 ABC ZYX then zipCode is 2000 and the rest is locality
        String zipCode = address.getNpaAndLocality().split(" ")[0];
        String locality = address.getNpaAndLocality().substring(zipCode.length() + 1);

        AddressResponseProto addressProto = createAddressRequestProtoList.stream()
                .filter(proto -> proto.getStreet().equals(address.getStreet())
                        && proto.getZipCode().equals(zipCode)
                        && proto.getLocality().equals(locality)
                        && proto.getCanton().name().equals(address.getCanton())
                        && proto.getCountry().name().equals(address.getCountry())
                        && proto.getCategory().name().equals(address.getAddressType())
                        && proto.getValidityStart().equals(address.getValidityStart())
                        && proto.getValidityEnd().equals(address.getValidityEnd()))
                .findFirst().orElse(null);

        return addressProto;
    }

    public AddressTableModel getAddressTableModelFromCreateAddressRequestProto(CreateAddressRequestProto createAddressRequestProto) {
        return AddressTableModel.builder()
                .street(createAddressRequestProto.getStreet())
                .npaAndLocality(createAddressRequestProto.getZipCode().concat(" ").concat(createAddressRequestProto.getLocality()))
                .canton(createAddressRequestProto.getCanton().name())
                .country(createAddressRequestProto.getCountry().name())
                .addressType(createAddressRequestProto.getCategory().name())
                .validityStart(createAddressRequestProto.getValidityStart())
                .validityEnd(createAddressRequestProto.getValidityEnd())
                .build();
    }

    public AddressResponseProto getAddressResponseProtoFromCreateAddressRequestProto(CreateAddressRequestProto createAddressRequestProto) {
        return AddressResponseProto.newBuilder()
                .setStreet(createAddressRequestProto.getStreet())
                .setZipCode(createAddressRequestProto.getZipCode())
                .setLocality(createAddressRequestProto.getLocality())
                .setHouseNumber(createAddressRequestProto.getHouseNumber())
                .setCanton(createAddressRequestProto.getCanton())
                .setCountry(createAddressRequestProto.getCountry())
                .setCategory(createAddressRequestProto.getCategory())
                .setValidityStart(createAddressRequestProto.getValidityStart())
                .setValidityEnd(createAddressRequestProto.getValidityEnd())
                .build();
    }

    public CreateAddressRequestProto convertAddressResponseProtoToCreateAddressRequestProto(AddressResponseProto addressResponseProto) {
        return CreateAddressRequestProto.newBuilder()
                .setStreet(addressResponseProto.getStreet())
                .setZipCode(addressResponseProto.getZipCode())
                .setHouseNumber(addressResponseProto.getHouseNumber())
                .setLocality(addressResponseProto.getLocality())
                .setCanton(addressResponseProto.getCanton())
                .setCountry(addressResponseProto.getCountry())
                .setCategory(addressResponseProto.getCategory())
                .setValidityStart(addressResponseProto.getValidityStart())
                .setValidityEnd(addressResponseProto.getValidityEnd())
                .build();
    }
}
