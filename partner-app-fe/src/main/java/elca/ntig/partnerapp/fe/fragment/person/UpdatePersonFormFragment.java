package elca.ntig.partnerapp.fe.fragment.person;

import elca.ntig.partnerapp.common.proto.entity.address.AddressResponseProto;
import elca.ntig.partnerapp.common.proto.entity.address.CreateAddressRequestProto;
import elca.ntig.partnerapp.common.proto.entity.person.GetPersonAlongWithAddressResponseProto;
import elca.ntig.partnerapp.common.proto.entity.person.PersonResponseProto;
import elca.ntig.partnerapp.common.proto.entity.person.UpdatePersonRequestProto;
import elca.ntig.partnerapp.common.proto.enums.common.PartnerTypeProto;
import elca.ntig.partnerapp.common.proto.enums.common.StatusProto;
import elca.ntig.partnerapp.common.proto.enums.partner.LanguageProto;
import elca.ntig.partnerapp.common.proto.enums.person.MaritalStatusProto;
import elca.ntig.partnerapp.common.proto.enums.person.NationalityProto;
import elca.ntig.partnerapp.common.proto.enums.person.SexEnumProto;
import elca.ntig.partnerapp.fe.callback.person.UpdatePersonCallback;
import elca.ntig.partnerapp.fe.common.cell.EnumCell;
import elca.ntig.partnerapp.fe.common.cell.LocalizedTableCell;
import elca.ntig.partnerapp.fe.common.constant.ClassNameConstant;
import elca.ntig.partnerapp.fe.common.constant.MessageConstant;
import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
import elca.ntig.partnerapp.fe.common.dialog.DialogBuilder;
import elca.ntig.partnerapp.fe.common.message.UpdateAddressMessage;
import elca.ntig.partnerapp.fe.common.model.AddressTableModel;
import elca.ntig.partnerapp.fe.component.UpdatePartnerComponent;
import elca.ntig.partnerapp.fe.component.ViewPartnerComponent;
import elca.ntig.partnerapp.fe.fragment.BaseFormFragment;
import elca.ntig.partnerapp.fe.fragment.common.CommonSetupFormFragment;
import elca.ntig.partnerapp.fe.perspective.UpdatePartnerPerspective;
import elca.ntig.partnerapp.fe.perspective.ViewPartnerPerspective;
import elca.ntig.partnerapp.fe.utils.BindingHelper;
import elca.ntig.partnerapp.fe.utils.ObservableResourceFactory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.jacpfx.rcp.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Fragment(id = UpdatePersonFormFragment.ID,
        viewLocation = ResourceConstant.UPDATE_PERSON_FORM_FRAGMENT_FXML,
        scope = Scope.PROTOTYPE)
public class UpdatePersonFormFragment extends CommonSetupFormFragment<AddressTableModel> implements BaseFormFragment {
    public static final String ID = "UpdatePersonFormFragment";
    private static Logger logger = Logger.getLogger(UpdatePersonFormFragment.class);
    UpdatePersonRequestProto.Builder updatePersonRequestProto = UpdatePersonRequestProto.newBuilder();
    private BindingHelper bindingHelper;
    GetPersonAlongWithAddressResponseProto orginalPersonResponseProto;

    //address
    private ObservableList<AddressTableModel> addressData = FXCollections.observableArrayList();
    private List<AddressResponseProto> addressResponseProtoList = new ArrayList<>();
    int indexUpdatingRow = -1;
    AddressTableModel updatingRowData = null;
    AddressResponseProto updatingAddressResponseProto = null;

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Resource
    private Context context;

    @FXML
    private Label fragmentTitle;

    @FXML
    private Text typeLabel;

    @FXML
    private ComboBox<PartnerTypeProto> typeComboBox;

    @FXML
    private Text lastNameLabel;

    @FXML
    private TextField lastNameValue;

    @FXML
    private Label lastNameErrorLabel;

    @FXML
    private Text firstNameLabel;

    @FXML
    private TextField firstNameValue;

    @FXML
    private Label firstNameErrorLabel;

    @FXML
    private Label avsNumberLabel;

    @FXML
    private TextField avsNumberValue;

    @FXML
    private Label avsNumberErrorLabel;

    @FXML
    private Label maritalStatusLabel;

    @FXML
    private ComboBox<MaritalStatusProto> maritalStatusComboBox;

    @FXML
    private Button createAddressButton;

    @FXML
    private Label createAddressButtonErrorLabel;

    // right column
    @FXML
    private Text correspondenceLanguageLabel;

    @FXML
    private ComboBox<LanguageProto> languageComboBox;

    @FXML
    private Label languageErrorLabel;

    @FXML
    private Text sexLabel;

    @FXML
    private ComboBox<SexEnumProto> sexComboBox;

    @FXML
    private Label sexErrorLabel;

    @FXML
    private Label nationalityLabel;

    @FXML
    private ComboBox<NationalityProto> nationalityComboBox;

    @FXML
    private Label birthDateLabel;

    @FXML
    private DatePicker birthDateValue;

    @FXML
    private Label birthDateErrorLabel;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private TextField phoneNumberValue;

    @FXML
    private Label phoneNumberErrorLabel;

    // address table
    @FXML
    private TableView<AddressTableModel> addressesTable;

    @FXML
    private TableColumn<AddressTableModel, String> streetColumn;

    @FXML
    private TableColumn<AddressTableModel, String> npaAndLocalityColumn;

    @FXML
    private TableColumn<AddressTableModel, String> cantonColumn;

    @FXML
    private TableColumn<AddressTableModel, String> countryColumn;

    @FXML
    private TableColumn<AddressTableModel, String> addressTypeColumn;

    @FXML
    private TableColumn<AddressTableModel, String> validityStartColumn;

    @FXML
    private TableColumn<AddressTableModel, String> validityEndColumn;

    @FXML
    private TableColumn<AddressTableModel, String> statusColumn;

    @FXML
    private TableColumn<AddressTableModel, Void> deleteIconColumn;

    // buttons
    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    public void init(GetPersonAlongWithAddressResponseProto responseProto) {
        orginalPersonResponseProto = responseProto;
        addressResponseProtoList = new ArrayList<>(responseProto.getAddressesList());
        bindingHelper = new BindingHelper(observableResourceFactory);
        bindTextProperties();
        setupUIControls();
        fillData(responseProto);
        setupUneditableForm(responseProto.getPerson().getStatus());
        initializeTable();
        setupDoubleClickEventHandler();
        handleEvents();
    }

    private void setupUneditableForm(StatusProto status) {
        uneditableComboBox(typeComboBox, bindingHelper);

        if (status == StatusProto.INACTIVE) {
            uneditableTextField(lastNameValue);
            uneditableTextField(firstNameValue);
            uneditableTextField(avsNumberValue);
            uneditableTextField(phoneNumberValue);

            uneditableDatePicker(birthDateValue);

            uneditableComboBox(maritalStatusComboBox, bindingHelper);
            uneditableComboBox(languageComboBox, bindingHelper);
            uneditableComboBox(sexComboBox, bindingHelper);
            uneditableComboBox(nationalityComboBox, bindingHelper);

            createAddressButton.setVisible(false);
            saveButton.setVisible(false);
        }
    }

    private void fillData(GetPersonAlongWithAddressResponseProto responseProto) {
        fillDataIntoForm(responseProto.getPerson());
        fillDataIntoTableAddress(responseProto.getAddressesList());
    }

    private void fillDataIntoTableAddress(List<AddressResponseProto> addressesList) {
        for (AddressResponseProto address : addressesList) {
            AddressTableModel model = AddressTableModel.builder()
                    .street(address.getStreet())
                    .npaAndLocality(address.getZipCode().concat(" ").concat(address.getLocality()))
                    .canton(address.getCanton().name())
                    .country(address.getCountry().name())
                    .addressType(address.getCategory().name())
                    .validityStart(address.getValidityStart())
                    .validityEnd(address.getValidityEnd())
                    .status(address.getStatus().name())
                    .build();
            addressData.add(model);
        }
        addressesTable.setItems(addressData);
    }

    private void fillDataIntoForm(PersonResponseProto responseProto) {
        lastNameValue.setText(responseProto.getLastName());
        firstNameValue.setText(responseProto.getFirstName());
        avsNumberValue.setText(responseProto.getAvsNumber());
        if (responseProto.getMaritalStatus() != MaritalStatusProto.NULL_MARITAL_STATUS) {
            maritalStatusComboBox.setValue(responseProto.getMaritalStatus());
        }
        languageComboBox.setValue(responseProto.getLanguage());
        sexComboBox.setValue(responseProto.getSex());
        if (responseProto.getNationality() != NationalityProto.NULL_NATIONALITY) {
            nationalityComboBox.setValue(responseProto.getNationality());
        }
        if (StringUtils.isNotBlank(responseProto.getBirthDate())) {
            setupDatePickerValue(birthDateValue, responseProto.getBirthDate());
        }
        phoneNumberValue.setText(responseProto.getPhoneNumber());
    }

    @Override
    public void bindTextProperties() {
        bindingHelper.bindLabelTextProperty(fragmentTitle, "FormFragment.lbl.updateFragmentTitle");
        bindingHelper.bindLabelTextProperty(createAddressButton, "FormFragment.btn.createAddress");
        bindingHelper.bindTextProperty(typeLabel, "FormFragment.lbl.type");
        bindingHelper.bindTextProperty(lastNameLabel, "FormFragment.lbl.lastName");
        bindingHelper.bindTextProperty(firstNameLabel, "FormFragment.lbl.firstNameRequired");
        bindingHelper.bindLabelTextProperty(avsNumberLabel, "FormFragment.lbl.avsNumber");
        bindingHelper.bindLabelTextProperty(maritalStatusLabel, "FormFragment.lbl.maritalStatus");
        bindingHelper.bindTextProperty(correspondenceLanguageLabel, "FormFragment.lbl.correspondenceLanguageRequired");
        bindingHelper.bindTextProperty(sexLabel, "FormFragment.lbl.sexRequired");
        bindingHelper.bindLabelTextProperty(nationalityLabel, "FormFragment.lbl.nationality");
        bindingHelper.bindLabelTextProperty(birthDateLabel, "FormFragment.lbl.birthDate");
        bindingHelper.bindLabelTextProperty(phoneNumberLabel, "FormFragment.lbl.phoneNumber");
        bindingHelper.bindLabelTextProperty(cancelButton, "FormFragment.btn.cancel");
        bindingHelper.bindLabelTextProperty(saveButton, "FormFragment.btn.save");

        bindingHelper.bindLabelTextProperty(lastNameErrorLabel, "Error.requiredField");
        bindingHelper.bindLabelTextProperty(firstNameErrorLabel, "Error.requiredField");
        bindingHelper.bindLabelTextProperty(languageErrorLabel, "Error.requiredField");
        bindingHelper.bindLabelTextProperty(sexErrorLabel, "Error.requiredField");
        bindingHelper.bindLabelTextProperty(avsNumberErrorLabel, "Error.invalidAvsNumber");
        bindingHelper.bindLabelTextProperty(birthDateErrorLabel, "Error.invalidDate");
        bindingHelper.bindLabelTextProperty(phoneNumberErrorLabel, "Error.invalidPhoneNumber");
        bindingHelper.bindLabelTextProperty(createAddressButtonErrorLabel, "Error.requiredAddress");

        bindingHelper.bindPromptTextProperty(languageComboBox, "FormFragment.comboBox.placeholder");
        bindingHelper.bindPromptTextProperty(sexComboBox, "FormFragment.comboBox.placeholder");
        bindingHelper.bindPromptTextProperty(nationalityComboBox, "FormFragment.comboBox.placeholder");
        bindingHelper.bindPromptTextProperty(maritalStatusComboBox, "FormFragment.comboBox.placeholder");

        // address table
        bindingHelper.bindColumnTextProperty(streetColumn, "TableFragment.col.street");
        bindingHelper.bindColumnTextProperty(npaAndLocalityColumn, "TableFragment.col.npaAndLocality");
        bindingHelper.bindColumnTextProperty(cantonColumn, "TableFragment.col.canton");
        bindingHelper.bindColumnTextProperty(countryColumn, "TableFragment.col.country");
        bindingHelper.bindColumnTextProperty(addressTypeColumn, "TableFragment.col.addressType");
        bindingHelper.bindColumnTextProperty(validityStartColumn, "TableFragment.col.validityStart");
        bindingHelper.bindColumnTextProperty(validityEndColumn, "TableFragment.col.validityEnd");
        bindingHelper.bindColumnTextProperty(statusColumn, "TableFragment.col.status");
    }

    @Override
    public void setupUIControls() {
        setupErrorLabelVisibility();
        setupComboBoxes();
        setupAvsNumberField();
        setupDatePicker();
        setupPhoneNumberField();
        setupComboBoxNullOptionListener();
    }

    private void setupComboBoxNullOptionListener() {
        nationalityComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == NationalityProto.NULL_NATIONALITY) {
                Platform.runLater(() -> {
                    nationalityComboBox.setValue(null);
                });
            }
        });

        maritalStatusComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == MaritalStatusProto.NULL_MARITAL_STATUS) {
                Platform.runLater(() -> {
                    maritalStatusComboBox.setValue(null);
                });
            }
        });
    }

    @Override
    public void setupComboBoxes() {
        typeComboBox.getItems().addAll(PartnerTypeProto.values());
        typeComboBox.getItems().removeAll(PartnerTypeProto.UNRECOGNIZED);
        typeComboBox.setValue(PartnerTypeProto.TYPE_PERSON);
//        typeComboBox.setDisable(true);
        typeComboBox.setCellFactory(cell -> new EnumCell<>(observableResourceFactory, "Enum.type."));
        typeComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.type."));
        typeComboBox.getStyleClass().add(ClassNameConstant.DISABLED_COMBO_BOX);

        languageComboBox.getItems().addAll(LanguageProto.values());
        languageComboBox.getItems().removeAll(LanguageProto.NULL_LANGUAGE, LanguageProto.UNRECOGNIZED);
        languageComboBox.setCellFactory(cell -> new EnumCell<>(observableResourceFactory, "Enum.language."));
        languageComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.language."));

        sexComboBox.getItems().addAll(SexEnumProto.values());
        sexComboBox.getItems().removeAll(SexEnumProto.NULL_SEX_ENUM, SexEnumProto.UNRECOGNIZED);
        sexComboBox.setCellFactory(cell -> new EnumCell<>(observableResourceFactory, "Enum.sex."));
        sexComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.sex."));

        nationalityComboBox.getItems().addAll(NationalityProto.values());
        nationalityComboBox.getItems().removeAll(NationalityProto.UNRECOGNIZED);
        nationalityComboBox.setCellFactory(cell -> new EnumCell<>(observableResourceFactory, "Enum.nationality."));
        nationalityComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.nationality."));

        maritalStatusComboBox.getItems().addAll(MaritalStatusProto.values());
        maritalStatusComboBox.getItems().removeAll(MaritalStatusProto.UNRECOGNIZED);
        maritalStatusComboBox.setCellFactory(cell -> new EnumCell<>(observableResourceFactory, "Enum.marital."));
        maritalStatusComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.marital."));
    }

    @Override
    public void setupErrorLabelVisibility() {
        lastNameErrorLabel.setVisible(false);
        firstNameErrorLabel.setVisible(false);
        avsNumberErrorLabel.setVisible(false);
        languageErrorLabel.setVisible(false);
        sexErrorLabel.setVisible(false);
        birthDateErrorLabel.setVisible(false);
        phoneNumberErrorLabel.setVisible(false);
        createAddressButtonErrorLabel.setVisible(false);
    }

    private void setupAvsNumberField() {
        setupAvsNumberFieldImpl(avsNumberValue);
    }

    @Override
    public void setupDatePicker() {
        setupDatePickerImpl(birthDateValue, true);
    }

    private void setupPhoneNumberField() {
        setupPhoneNumberFieldImpl(phoneNumberValue);
    }

    @Override
    public void handleEvents() {
        saveButton.setOnAction(event -> handleSaveButtonOnClick());
        cancelButton.setOnAction(event -> handleCancelButtonOnClick());
        createAddressButton.setOnAction(event -> handleCreateAddressButtonOnClick());
    }

    private void handleCreateAddressButtonOnClick() {
        context.send(UpdatePartnerPerspective.ID.concat(".").concat(UpdatePartnerComponent.ID), MessageConstant.SHOW_CREATE_ADDRESS_FORM_FOR_PERSON);
    }

    private void handleSaveButtonOnClick() {
        validateValues();
        if (isFormValid()) {
            updatePersonRequestProto = UpdatePersonRequestProto.newBuilder();
            updatePersonRequestProto
                    .setId(orginalPersonResponseProto.getPerson().getId())
                    .setLastName(lastNameValue.getText())
                    .setFirstName(firstNameValue.getText())
                    .setAvsNumber(avsNumberValue.getText().replaceAll("\\.", ""))
                    .setLanguage(languageComboBox.getValue())
                    .setSex(sexComboBox.getValue())
                    .setPhoneNumber(phoneNumberValue.getText());

            if (maritalStatusComboBox.getValue() != null) {
                updatePersonRequestProto.setMaritalStatus(maritalStatusComboBox.getValue());
            }
            if (nationalityComboBox.getValue() != null) {
                updatePersonRequestProto.setNationality(nationalityComboBox.getValue());
            }
            if (birthDateValue.getValue() != null) {
                updatePersonRequestProto.setBirthDate(birthDateValue.getValue().toString());
            } else {
                updatePersonRequestProto.clearBirthDate();
            }

            updatePersonRequestProto.addAllAddresses(addressResponseProtoList);

            context.send(UpdatePartnerPerspective.ID.concat(".").concat(UpdatePersonCallback.ID), updatePersonRequestProto.build());
        }
    }

    private boolean isFormValid() {
        return !lastNameErrorLabel.isVisible()
                && !firstNameErrorLabel.isVisible()
                && !avsNumberErrorLabel.isVisible()
                && !languageErrorLabel.isVisible()
                && !sexErrorLabel.isVisible()
                && !birthDateErrorLabel.isVisible()
                && !phoneNumberErrorLabel.isVisible()
                && !createAddressButtonErrorLabel.isVisible();
    }

    @Override
    public void validateValues() {
        validateRequiredTextField(lastNameValue, lastNameErrorLabel);
        validateRequiredTextField(firstNameValue, firstNameErrorLabel);
        validateAvsNumber(avsNumberValue, avsNumberErrorLabel);
        validateRequiredComboBox(languageComboBox, languageErrorLabel);
        validateRequiredComboBox(sexComboBox, sexErrorLabel);
        validateDate(birthDateValue, birthDateErrorLabel);
        validatePhoneNumber(phoneNumberValue, phoneNumberErrorLabel);
        validateRequiredAddress(addressData, createAddressButton, createAddressButtonErrorLabel);
    }

    private void handleCancelButtonOnClick() {
        context.send(ViewPartnerPerspective.ID, MessageConstant.INIT);
        context.send(ViewPartnerPerspective.ID.concat(".").concat(ViewPartnerComponent.ID), MessageConstant.SWITCH_TYPE_TO_PERSON);
    }

    // address table
    public void initializeTable() {
        setTableDefaultMessage();
        setCellValueFactories();
        setCellFactories();
    }

    private void setTableDefaultMessage() {
        setTableDefaultMessage(bindingHelper, addressesTable);
    }

    private void setCellValueFactories() {
        streetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
        npaAndLocalityColumn.setCellValueFactory(new PropertyValueFactory<>("npaAndLocality"));
        cantonColumn.setCellValueFactory(new PropertyValueFactory<>("canton"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        addressTypeColumn.setCellValueFactory(new PropertyValueFactory<>("addressType"));
        validityStartColumn.setCellValueFactory(new PropertyValueFactory<>("validityStart"));
        validityEndColumn.setCellValueFactory(new PropertyValueFactory<>("validityEnd"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void setCellFactories() {
        cantonColumn.setCellFactory(cell -> new LocalizedTableCell<>(observableResourceFactory, "Enum.cantonAbbr."));
        countryColumn.setCellFactory(cell -> new LocalizedTableCell<>(observableResourceFactory, "Enum.country."));
        addressTypeColumn.setCellFactory(cell -> new LocalizedTableCell<>(observableResourceFactory, "Enum.addressType."));
        statusColumn.setCellFactory(cell -> new LocalizedTableCell<>(observableResourceFactory, "FormFragment.checkBox."));
        setCellFactoryDateColumn(validityStartColumn);
        setCellFactoryDateColumn(validityEndColumn);
        deleteIconColumn.setCellFactory(cell -> new TableCell<AddressTableModel, Void>() {
            private final ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream(ResourceConstant.BIN_ICON)));

            {
                deleteIcon.setFitHeight(20);
                deleteIcon.setFitWidth(20);
            }

            Button deleteButton = new Button();

            {
                deleteButton.getStyleClass().add(ClassNameConstant.DELETE_BUTTON);
                deleteButton.setGraphic(deleteIcon);
                deleteButton.setOnAction(event -> {
                    AddressTableModel address = getTableView().getItems().get(getIndex());
                    handleDeleteButtonOnClick(address, addressResponseProtoList, addressData);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    AddressTableModel person = getTableView().getItems().get(getIndex());
                    if (StringUtils.isBlank(person.getStatus()) || person.getStatus().equals("ACTIVE") || person.getStatus().equals("NULL_STATUS")) {
                        setGraphic(deleteButton);
                    } else {
                        setGraphic(null);
                    }
                }
            }
        });
    }

    public void handleDeleteButtonOnClick(AddressTableModel address, List<AddressResponseProto> addressResponseProtoList, ObservableList<AddressTableModel> addressData) {
        DialogBuilder dialogBuilder = new DialogBuilder(observableResourceFactory);
        Alert alert = dialogBuilder.buildAlert(Alert.AlertType.CONFIRMATION, "Dialog.confirmation.title",
                "Dialog.confirmation.message.deleteAddress");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            AddressResponseProto addressProto = getAddressResponseProtoByAddressTableModel(address, addressResponseProtoList);
            if (addressProto != null) {
                if (addressProto.getStatus() == StatusProto.NULL_STATUS) {
                    addressResponseProtoList.remove(addressProto);
                    addressData.remove(address);
                } else {
                    AddressResponseProto updatedAddressProto = addressProto.toBuilder()
                            .setStatus(StatusProto.INACTIVE)
                            .build();

                    int index = addressResponseProtoList.indexOf(addressProto);
                    if (index != -1) {
                        addressResponseProtoList.set(index, updatedAddressProto);
                    }

                    address.setStatus("INACTIVE");
                    addressData.set(addressData.indexOf(address), address);
                }
            }
            addressesTable.setItems(addressData);
        }
    }

    public void updateAddressTable(CreateAddressRequestProto createAddressRequestProto) {
        AddressTableModel model = getAddressTableModelFromCreateAddressRequestProto(createAddressRequestProto);
        AddressResponseProto addressResponseProto = getAddressResponseProtoFromCreateAddressRequestProto(createAddressRequestProto);

        addressData.add(model);
        addressResponseProtoList.add(addressResponseProto);

        Platform.runLater(() -> {
            addressesTable.setItems(addressData);
        });
    }

    public void updateRowData(CreateAddressRequestProto createAddressRequestProto) {
        AddressTableModel model = getAddressTableModelFromCreateAddressRequestProto(createAddressRequestProto);
        AddressResponseProto addressResponseProto = getAddressResponseProtoFromCreateAddressRequestProto(createAddressRequestProto);

        // update status and id to the original address
        model.setStatus(updatingAddressResponseProto.getStatus().name());
        AddressResponseProto updatedAddressProto = addressResponseProto.toBuilder()
                .setId(updatingAddressResponseProto.getId())
                .setStatus(updatingAddressResponseProto.getStatus())
                .build();

        addressData.set(addressData.indexOf(updatingRowData), model);
        addressResponseProtoList.set(indexUpdatingRow, updatedAddressProto);

        Platform.runLater(() -> {
            addressesTable.setItems(addressData);
        });
    }

    public void setupDoubleClickEventHandler() {
        addressesTable.setRowFactory(tr -> {
            TableRow<AddressTableModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    AddressTableModel rowData = row.getItem();
                    AddressResponseProto addressProto = getAddressResponseProtoByAddressTableModel(rowData, addressResponseProtoList);

                    indexUpdatingRow = addressResponseProtoList.indexOf(addressProto);
                    updatingRowData = rowData;
                    updatingAddressResponseProto = addressProto;

                    UpdateAddressMessage request = UpdateAddressMessage.builder()
                            .partnerType(PartnerTypeProto.TYPE_PERSON)
                            .updateAddressRequestProto(convertAddressResponseProtoToCreateAddressRequestProto(addressProto))
                            .status(addressProto.getStatus())
                            .build();
                    context.send(UpdatePartnerPerspective.ID.concat(".").concat(UpdatePartnerComponent.ID), request);
                }
            });
            return row;
        });
    }
}