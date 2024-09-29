package elca.ntig.partnerapp.fe.fragment.organisation;

import elca.ntig.partnerapp.common.proto.entity.address.AddressResponseProto;
import elca.ntig.partnerapp.common.proto.entity.address.CreateAddressRequestProto;
import elca.ntig.partnerapp.common.proto.entity.organisation.GetOrganisationAlongWithAddressResponseProto;
import elca.ntig.partnerapp.common.proto.entity.organisation.OrganisationResponseProto;
import elca.ntig.partnerapp.common.proto.entity.organisation.UpdateOrganisationRequestProto;
import elca.ntig.partnerapp.common.proto.entity.person.GetPersonRequestProto;
import elca.ntig.partnerapp.common.proto.enums.common.PartnerTypeProto;
import elca.ntig.partnerapp.common.proto.enums.common.StatusProto;
import elca.ntig.partnerapp.common.proto.enums.organisation.CodeNOGAProto;
import elca.ntig.partnerapp.common.proto.enums.organisation.LegalStatusProto;
import elca.ntig.partnerapp.common.proto.enums.partner.LanguageProto;
import elca.ntig.partnerapp.fe.callback.organisation.UpdateOrganisationCallback;
import elca.ntig.partnerapp.fe.callback.person.DeletePersonCallback;
import elca.ntig.partnerapp.fe.common.cell.EnumCell;
import elca.ntig.partnerapp.fe.common.cell.LocalizedTableCell;
import elca.ntig.partnerapp.fe.common.constant.ClassNameConstant;
import elca.ntig.partnerapp.fe.common.constant.MessageConstant;
import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
import elca.ntig.partnerapp.fe.common.dialog.DialogBuilder;
import elca.ntig.partnerapp.fe.common.message.UpdateAddressMessage;
import elca.ntig.partnerapp.fe.common.model.AddressTableModel;
import elca.ntig.partnerapp.fe.component.CreatePartnerComponent;
import elca.ntig.partnerapp.fe.component.UpdatePartnerComponent;
import elca.ntig.partnerapp.fe.component.ViewPartnerComponent;
import elca.ntig.partnerapp.fe.fragment.BaseFormFragment;
import elca.ntig.partnerapp.fe.fragment.common.CommonSetupFormFragment;
import elca.ntig.partnerapp.fe.perspective.CreatePartnerPerspective;
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
@Fragment(id = UpdateOrganisationFormFragment.ID,
        viewLocation = ResourceConstant.UPDATE_ORGANISATION_FORM_FRAGMENT_FXML,
        scope = Scope.PROTOTYPE)
public class UpdateOrganisationFormFragment extends CommonSetupFormFragment<AddressTableModel> implements BaseFormFragment {
    public static final String ID = "UpdateOrganisationFormFragment";
    private static Logger logger = Logger.getLogger(UpdateOrganisationFormFragment.class);
    UpdateOrganisationRequestProto.Builder updateOrganisationRequestProto = UpdateOrganisationRequestProto.newBuilder();
    private BindingHelper bindingHelper;
    GetOrganisationAlongWithAddressResponseProto orginalOrganisationResponseProto;

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
    private Text nameLabel;

    @FXML
    private TextField nameValue;

    @FXML
    private Label nameErrorLabel;

    @FXML
    private Label additionalNameLabel;

    @FXML
    private TextField additionalNameValue;

    @FXML
    private Label ideNumberLabel;

    @FXML
    private TextField ideNumberValue;

    @FXML
    private Label ideNumberErrorLabel;

    @FXML
    private Label codeNOGALabel;

    @FXML
    private ComboBox<CodeNOGAProto> codeNOGAComboBox;

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
    private Label legalStatusLabel;

    @FXML
    private ComboBox<LegalStatusProto> legalStatusComboBox;

    @FXML
    private Label creationDateLabel;

    @FXML
    private DatePicker creationDateValue;

    @FXML
    private Label creationDateErrorLabel;

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

    public void init(GetOrganisationAlongWithAddressResponseProto responseProto) {
        orginalOrganisationResponseProto = responseProto;
        addressResponseProtoList = new ArrayList<>(responseProto.getAddressesList());
        bindingHelper = new BindingHelper(observableResourceFactory);
        bindTextProperties();
        setupUIControls();
        fillData(responseProto);
        initializeTable();
        setupDoubleClickEventHandler();
        handleEvents();
    }

    private void fillData(GetOrganisationAlongWithAddressResponseProto responseProto) {
        fillDataIntoForm(responseProto.getOrganisation());
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

    private void fillDataIntoForm(OrganisationResponseProto responseProto) {
        nameValue.setText(responseProto.getName());
        additionalNameValue.setText(responseProto.getAdditionalName());
        ideNumberValue.setText(responseProto.getIdeNumber());
        if (responseProto.getCodeNoga() != CodeNOGAProto.NULL_CODE_NOGA) {
            codeNOGAComboBox.setValue(responseProto.getCodeNoga());
        }
        languageComboBox.setValue(responseProto.getLanguage());
        if (responseProto.getLegalStatus() != LegalStatusProto.NULL_LEGAL_STATUS) {
            legalStatusComboBox.setValue(responseProto.getLegalStatus());
        }
        if (StringUtils.isNotBlank(responseProto.getCreationDate())) {
            setupDatePickerValue(creationDateValue, responseProto.getCreationDate());
        }
        phoneNumberValue.setText(responseProto.getPhoneNumber());
    }

    @Override
    public void bindTextProperties() {
        bindingHelper.bindLabelTextProperty(fragmentTitle, "FormFragment.lbl.updateFragmentTitle");
        bindingHelper.bindLabelTextProperty(createAddressButton, "FormFragment.btn.createAddress");
        bindingHelper.bindTextProperty(typeLabel, "FormFragment.lbl.type");
        bindingHelper.bindTextProperty(nameLabel, "FormFragment.lbl.lastName");
        bindingHelper.bindLabelTextProperty(additionalNameLabel, "FormFragment.lbl.additionalName");
        bindingHelper.bindLabelTextProperty(ideNumberLabel, "FormFragment.lbl.ideNumber");
        bindingHelper.bindLabelTextProperty(codeNOGALabel, "FormFragment.lbl.codeNOGA");
        bindingHelper.bindTextProperty(correspondenceLanguageLabel, "FormFragment.lbl.correspondenceLanguageRequired");
        bindingHelper.bindLabelTextProperty(legalStatusLabel, "FormFragment.lbl.legalStatus");
        bindingHelper.bindLabelTextProperty(creationDateLabel, "FormFragment.lbl.creationDate");
        bindingHelper.bindLabelTextProperty(phoneNumberLabel, "FormFragment.lbl.phoneNumber");
        bindingHelper.bindLabelTextProperty(cancelButton, "FormFragment.btn.cancel");
        bindingHelper.bindLabelTextProperty(saveButton, "FormFragment.btn.save");

        bindingHelper.bindLabelTextProperty(nameErrorLabel, "Error.requiredField");
        bindingHelper.bindLabelTextProperty(languageErrorLabel, "Error.requiredField");
        bindingHelper.bindLabelTextProperty(ideNumberErrorLabel, "Error.invalidIdeNumber");
        bindingHelper.bindLabelTextProperty(creationDateErrorLabel, "Error.invalidDate");
        bindingHelper.bindLabelTextProperty(phoneNumberErrorLabel, "Error.invalidPhoneNumber");
        bindingHelper.bindLabelTextProperty(createAddressButtonErrorLabel, "Error.requiredAddress");

        bindingHelper.bindPromptTextProperty(languageComboBox, "FormFragment.comboBox.placeholder");
        bindingHelper.bindPromptTextProperty(legalStatusComboBox, "FormFragment.comboBox.placeholder");
        bindingHelper.bindPromptTextProperty(codeNOGAComboBox, "FormFragment.comboBox.placeholder");

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
        setupIdeNumberField();
        setupDatePicker();
        setupPhoneNumberField();
    }

    @Override
    public void setupComboBoxes() {
        typeComboBox.getItems().addAll(PartnerTypeProto.values());
        typeComboBox.getItems().removeAll(PartnerTypeProto.UNRECOGNIZED);
        typeComboBox.setValue(PartnerTypeProto.TYPE_ORGANISATION);
        typeComboBox.setDisable(true);
        typeComboBox.setCellFactory(cell -> new EnumCell<>(observableResourceFactory, "Enum.type."));
        typeComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.type."));
        typeComboBox.getStyleClass().add(ClassNameConstant.DISABLED_COMBO_BOX);

        languageComboBox.getItems().addAll(LanguageProto.values());
        languageComboBox.getItems().removeAll(LanguageProto.NULL_LANGUAGE, LanguageProto.UNRECOGNIZED);
        languageComboBox.setCellFactory(cell -> new EnumCell<>(observableResourceFactory, "Enum.language."));
        languageComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.language."));

        legalStatusComboBox.getItems().addAll(LegalStatusProto.values());
        legalStatusComboBox.getItems().removeAll(LegalStatusProto.NULL_LEGAL_STATUS, LegalStatusProto.UNRECOGNIZED);
        legalStatusComboBox.setCellFactory(cell -> new EnumCell<>(observableResourceFactory, "Enum.legalStatus."));
        legalStatusComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.legalStatus."));

        codeNOGAComboBox.getItems().addAll(CodeNOGAProto.values());
        codeNOGAComboBox.getItems().removeAll(CodeNOGAProto.NULL_CODE_NOGA, CodeNOGAProto.UNRECOGNIZED);
        codeNOGAComboBox.setCellFactory(cell -> new EnumCell<>(observableResourceFactory, "Enum.codeNOGA."));
        codeNOGAComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.codeNOGA."));
    }

    private void setupIdeNumberField() {
        setupIdeNumberFieldImpl(ideNumberValue);
    }

    @Override
    public void setupErrorLabelVisibility() {
        nameErrorLabel.setVisible(false);
        ideNumberErrorLabel.setVisible(false);
        languageErrorLabel.setVisible(false);
        creationDateErrorLabel.setVisible(false);
        phoneNumberErrorLabel.setVisible(false);
        createAddressButtonErrorLabel.setVisible(false);
    }

    @Override
    public void setupDatePicker() {
        setupDatePickerImpl(creationDateValue);
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
        context.send(UpdatePartnerPerspective.ID.concat(".").concat(UpdatePartnerComponent.ID), MessageConstant.SHOW_CREATE_ADDRESS_FORM_FOR_ORGANISATION);
    }

    private void handleSaveButtonOnClick() {
        validateValues();
        if (isFormValid()) {
            updateOrganisationRequestProto = UpdateOrganisationRequestProto.newBuilder();
            updateOrganisationRequestProto
                    .setId(orginalOrganisationResponseProto.getOrganisation().getId())
                    .setName(nameValue.getText())
                    .setAdditionalName(additionalNameValue.getText())
                    .setIdeNumber(ideNumberValue.getText().replaceAll("[.]", ""))
                    .setLanguage(languageComboBox.getValue())
                    .setPhoneNumber(phoneNumberValue.getText());

            if (codeNOGAComboBox.getValue() != null) {
                updateOrganisationRequestProto.setCodeNoga(codeNOGAComboBox.getValue());
            }
            if (legalStatusComboBox.getValue() != null) {
                updateOrganisationRequestProto.setLegalStatus(legalStatusComboBox.getValue());
            }
            if (creationDateValue.getValue() != null) {
                updateOrganisationRequestProto.setCreationDate(creationDateValue.getValue().toString());
            } else {
                updateOrganisationRequestProto.clearCreationDate();
            }

            updateOrganisationRequestProto.addAllAddresses(addressResponseProtoList);

            context.send(UpdatePartnerPerspective.ID.concat(".").concat(UpdateOrganisationCallback.ID), updateOrganisationRequestProto.build());
        }
    }

    private boolean isFormValid() {
        return !nameErrorLabel.isVisible()
                && !ideNumberErrorLabel.isVisible()
                && !languageErrorLabel.isVisible()
                && !creationDateErrorLabel.isVisible()
                && !phoneNumberErrorLabel.isVisible()
                && !createAddressButtonErrorLabel.isVisible();
    }

    private void handleCancelButtonOnClick() {
        context.send(ViewPartnerPerspective.ID, MessageConstant.INIT);
        context.send(ViewPartnerPerspective.ID.concat(".").concat(ViewPartnerComponent.ID), MessageConstant.SWITCH_TYPE_TO_ORGANISATION);
    }

    @Override
    public void validateValues() {
        validateRequiredTextField(nameValue, nameErrorLabel);
        validateIdeNumber(ideNumberValue, ideNumberErrorLabel);
        validateRequiredComboBox(languageComboBox, languageErrorLabel);
        validateDate(creationDateValue, creationDateErrorLabel);
        validatePhoneNumber(phoneNumberValue, phoneNumberErrorLabel);
        validateRequiredAddress(addressData, createAddressButton, createAddressButtonErrorLabel);
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
                "Dialog.confirmation.header.deletePartner", "Dialog.confirmation.message.deletePartner");
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
                            .partnerType(PartnerTypeProto.TYPE_ORGANISATION)
                            .updateAddressRequestProto(convertAddressResponseProtoToCreateAddressRequestProto(addressProto))
                            .build();
                    context.send(UpdatePartnerPerspective.ID.concat(".").concat(UpdatePartnerComponent.ID), request);
                }
            });
            return row;
        });
    }
}