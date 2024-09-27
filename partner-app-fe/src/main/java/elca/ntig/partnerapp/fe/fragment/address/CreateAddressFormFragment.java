package elca.ntig.partnerapp.fe.fragment.address;

import elca.ntig.partnerapp.common.proto.entity.address.CreateAddressRequestProto;
import elca.ntig.partnerapp.common.proto.entity.person.CreatePersonRequestProto;
import elca.ntig.partnerapp.common.proto.enums.address.AddressTypeProto;
import elca.ntig.partnerapp.common.proto.enums.address.CantonAbbrProto;
import elca.ntig.partnerapp.common.proto.enums.address.CountryProto;
import elca.ntig.partnerapp.common.proto.enums.common.PartnerType;
import elca.ntig.partnerapp.common.proto.enums.common.PartnerTypeProto;
import elca.ntig.partnerapp.fe.callback.person.CreatePersonCallback;
import elca.ntig.partnerapp.fe.common.cell.EnumCell;
import elca.ntig.partnerapp.fe.common.constant.MessageConstant;
import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
import elca.ntig.partnerapp.fe.common.message.CreateAddressMessage;
import elca.ntig.partnerapp.fe.component.CreatePartnerComponent;
import elca.ntig.partnerapp.fe.component.ViewPartnerComponent;
import elca.ntig.partnerapp.fe.fragment.BaseFormFragment;
import elca.ntig.partnerapp.fe.fragment.common.CommonSetupFormFragment;
import elca.ntig.partnerapp.fe.perspective.CreatePartnerPerspective;
import elca.ntig.partnerapp.fe.perspective.ViewPartnerPerspective;
import elca.ntig.partnerapp.fe.utils.BindingHelper;
import elca.ntig.partnerapp.fe.utils.ObservableResourceFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.jacpfx.rcp.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Fragment(id = CreateAddressFormFragment.ID,
        viewLocation = ResourceConstant.CREATE_ADDRESS_FORM_FRAGMENT_FXML,
        scope = Scope.PROTOTYPE)
public class CreateAddressFormFragment extends CommonSetupFormFragment implements BaseFormFragment {
    public static final String ID = "CreateAddressFormFragment";
    private static Logger logger = Logger.getLogger(CreateAddressFormFragment.class);
    CreateAddressRequestProto.Builder createAddressRequestProto = CreateAddressRequestProto.newBuilder();
    private BindingHelper bindingHelper;
    private PartnerTypeProto currentPartnerType;

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Resource
    private Context context;

    @FXML
    private Label fragmentTitle;

    @FXML
    private Text typeLabel;

    @FXML
    private ComboBox<AddressTypeProto> typeComboBox;

    @FXML
    private Label typeErrorLabel;

    @FXML
    private Text localityLabel;

    @FXML
    private TextField localityValue;

    @FXML
    private Label localityErrorLabel;

    @FXML
    private Label streetLabel;

    @FXML
    private TextField streetValue;

    @FXML
    private Label countryLabel;

    @FXML
    private ComboBox<CountryProto> countryComboBox;

    @FXML
    private Text validityStartLabel;

    @FXML
    private DatePicker validityStartValue;

    @FXML
    private Label validityStartErrorLabel;

    // right column
    @FXML
    private Text zipCodeLabel;

    @FXML
    private TextField zipCodeValue;

    @FXML
    private Label zipCodeErrorLabel;

    @FXML
    private Label houseNumberLabel;

    @FXML
    private TextField houseNumberValue;

    @FXML
    private Label cantonLabel;

    @FXML
    private ComboBox<CantonAbbrProto> cantonComboBox;

    @FXML
    private Label validityEndLabel;

    @FXML
    private DatePicker validityEndValue;

    @FXML
    private Label validityEndErrorLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    public void init(PartnerTypeProto partnerType) {
        currentPartnerType = partnerType;
        bindingHelper = new BindingHelper(observableResourceFactory);
        bindTextProperties();
        setupUIControls();
        handleEvents();
    }

    @Override
    public void bindTextProperties() {
        bindingHelper.bindLabelTextProperty(fragmentTitle, "FormFragment.lbl.createAddressFragmentTitle");
        bindingHelper.bindTextProperty(typeLabel, "FormFragment.lbl.addressType");
        bindingHelper.bindTextProperty(localityLabel, "FormFragment.lbl.locality");
        bindingHelper.bindLabelTextProperty(streetLabel, "FormFragment.lbl.street");
        bindingHelper.bindLabelTextProperty(countryLabel, "FormFragment.lbl.country");
        bindingHelper.bindTextProperty(validityStartLabel, "FormFragment.lbl.validityStart");
        bindingHelper.bindTextProperty(zipCodeLabel, "FormFragment.lbl.zipCode");
        bindingHelper.bindLabelTextProperty(houseNumberLabel, "FormFragment.lbl.houseNumber");
        bindingHelper.bindLabelTextProperty(cantonLabel, "FormFragment.lbl.canton");
        bindingHelper.bindLabelTextProperty(validityEndLabel, "FormFragment.lbl.validityEnd");
        bindingHelper.bindLabelTextProperty(cancelButton, "FormFragment.btn.cancel");
        bindingHelper.bindLabelTextProperty(saveButton, "FormFragment.btn.save");

        bindingHelper.bindLabelTextProperty(typeErrorLabel, "Error.requiredField");
        bindingHelper.bindLabelTextProperty(localityErrorLabel, "Error.requiredField");
        bindingHelper.bindLabelTextProperty(validityStartErrorLabel, "Error.requiredField");
        bindingHelper.bindLabelTextProperty(zipCodeErrorLabel, "Error.requiredField");
        bindingHelper.bindLabelTextProperty(validityEndErrorLabel, "Error.invalidValidityEnd");

        bindingHelper.bindPromptTextProperty(typeComboBox, "FormFragment.comboBox.placeholder");
        bindingHelper.bindPromptTextProperty(countryComboBox, "FormFragment.comboBox.placeholder");
        bindingHelper.bindPromptTextProperty(cantonComboBox, "FormFragment.comboBox.placeholder");
    }

    @Override
    public void setupUIControls() {
        setupErrorLabelVisibility();
        setupComboBoxes();
        setupDatePicker();
    }

    @Override
    public void setupComboBoxes() {
        typeComboBox.getItems().addAll(AddressTypeProto.values());
        typeComboBox.getItems().remove(AddressTypeProto.UNRECOGNIZED);
        typeComboBox.setCellFactory(cell -> new EnumCell<>(observableResourceFactory, "Enum.addressType."));
        typeComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.addressType."));

        countryComboBox.getItems().addAll(CountryProto.values());
        countryComboBox.getItems().removeAll(CountryProto.UNRECOGNIZED);
        countryComboBox.setCellFactory(cell -> new EnumCell<>(observableResourceFactory, "Enum.country."));
        countryComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.country."));

        cantonComboBox.getItems().addAll(CantonAbbrProto.values());
        cantonComboBox.getItems().removeAll(CantonAbbrProto.UNRECOGNIZED);
        cantonComboBox.setCellFactory(cell -> new EnumCell<>(observableResourceFactory, "Enum.cantonAbbr."));
        cantonComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.cantonAbbr."));
    }

    @Override
    public void setupErrorLabelVisibility() {
        typeErrorLabel.setVisible(false);
        localityErrorLabel.setVisible(false);
        validityStartErrorLabel.setVisible(false);
        zipCodeErrorLabel.setVisible(false);
        validityEndErrorLabel.setVisible(false);
    }

    @Override
    public void setupDatePicker() {
        setupDatePickerImpl(validityStartValue);
        setupDatePickerImpl(validityEndValue);
    }

    @Override
    public void handleEvents() {
        cancelButton.setOnAction(event -> closePopupWindow());
        saveButton.setOnAction(event -> handleSaveButtonOnClick());
    }

    private void handleSaveButtonOnClick() {
        validateValues();
        if (isFormValid()) {
            createAddressRequestProto = CreateAddressRequestProto.newBuilder();
            createAddressRequestProto
                    .setCategory(typeComboBox.getValue())
                    .setLocality(localityValue.getText())
                    .setStreet(streetValue.getText())
                    .setValidityStart(validityStartValue.getValue().toString())
                    .setZipCode(zipCodeValue.getText())
                    .setHouseNumber(houseNumberValue.getText());

            if (countryComboBox.getValue() != null) {
                createAddressRequestProto.setCountry(countryComboBox.getValue());
            }

            if (cantonComboBox.getValue() != null) {
                createAddressRequestProto.setCanton(cantonComboBox.getValue());
            }

            if (validityEndValue.getValue() != null) {
                createAddressRequestProto.setValidityEnd(validityEndValue.getValue().toString());
            } else {
                createAddressRequestProto.clearValidityEnd();
            }

            CreateAddressMessage createAddressMessage = CreateAddressMessage.builder()
                    .partnerType(currentPartnerType)
                    .createAddressRequestProto(createAddressRequestProto.build())
                    .build();

            context.send(CreatePartnerPerspective.ID.concat(".").concat(CreatePartnerComponent.ID), createAddressMessage);
            closePopupWindow();
        }
    }

    private boolean isFormValid() {
        return !typeErrorLabel.isVisible()
                && !localityErrorLabel.isVisible()
                && !validityStartErrorLabel.isVisible()
                && !zipCodeErrorLabel.isVisible()
                && !validityEndErrorLabel.isVisible();
    }

    private void closePopupWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void validateValues() {
        validateRequiredTextField(localityValue, localityErrorLabel);
        validateRequiredTextField(zipCodeValue, zipCodeErrorLabel);
        validateRequiredComboBox(typeComboBox, typeErrorLabel);
        validateRequiredDatePicker(validityStartValue, validityStartErrorLabel);
        validateMaxLengthTextField(zipCodeValue, 15);
        validateMaxLengthTextField(localityValue, 50);
        validateMaxLengthTextField(streetValue, 60);
        validateMaxLengthTextField(houseNumberValue, 12);
        validateEndDateAfterStartDate(validityStartValue, validityEndValue, validityEndErrorLabel);
    }
}
