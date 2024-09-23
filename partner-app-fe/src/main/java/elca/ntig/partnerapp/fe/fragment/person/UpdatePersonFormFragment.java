package elca.ntig.partnerapp.fe.fragment.person;

import elca.ntig.partnerapp.common.proto.entity.person.SearchPeopleCriteriasProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationRequestProto;
import elca.ntig.partnerapp.common.proto.entity.person.UpdatePersonRequestProto;
import elca.ntig.partnerapp.common.proto.enums.common.PartnerTypeProto;
import elca.ntig.partnerapp.common.proto.enums.partner.LanguageProto;
import elca.ntig.partnerapp.common.proto.enums.person.MaritalStatusProto;
import elca.ntig.partnerapp.common.proto.enums.person.NationalityProto;
import elca.ntig.partnerapp.common.proto.enums.person.SexEnumProto;
import elca.ntig.partnerapp.fe.common.cell.EnumCell;
import elca.ntig.partnerapp.fe.common.constant.MessageConstant;
import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
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
import org.apache.log4j.Logger;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.jacpfx.rcp.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Fragment(id = UpdatePersonFormFragment.ID,
        viewLocation = ResourceConstant.UPDATE_PERSON_FORM_FRAGMENT_FXML,
        scope = Scope.PROTOTYPE)
public class UpdatePersonFormFragment extends CommonSetupFormFragment implements BaseFormFragment {
    public static final String ID = "UpdatePersonFormFragment";
    private static Logger logger = Logger.getLogger(UpdatePersonFormFragment.class);
    UpdatePersonRequestProto.Builder updatePersonRequestProto = UpdatePersonRequestProto.newBuilder();
    private BindingHelper bindingHelper;

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

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @Override
    public void init() {
        bindingHelper = new BindingHelper(observableResourceFactory);
        bindTextProperties();
        setupUIControls();
        handleEvents();
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
        bindingHelper.bindPromptTextProperty(languageComboBox, "FormFragment.comboBox.placeholder");
        bindingHelper.bindPromptTextProperty(sexComboBox, "FormFragment.comboBox.placeholder");
        bindingHelper.bindPromptTextProperty(nationalityComboBox, "FormFragment.comboBox.placeholder");
        bindingHelper.bindPromptTextProperty(maritalStatusComboBox, "FormFragment.comboBox.placeholder");
    }

    @Override
    public void setupUIControls() {
        setupComboBoxes();
        setupAvsNumberField();
        setupDatePicker();
        setupPhoneNumberField();
        setupErrorLabelVisibility();
    }

    @Override
    public void setupComboBoxes() {
        typeComboBox.getItems().addAll(PartnerTypeProto.values());
        typeComboBox.getItems().removeAll(PartnerTypeProto.UNRECOGNIZED);
        typeComboBox.setValue(PartnerTypeProto.TYPE_PERSON);
        typeComboBox.setCellFactory(cell -> new EnumCell<>(observableResourceFactory, "Enum.type."));
        typeComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.type."));

        languageComboBox.getItems().addAll(LanguageProto.values());
        languageComboBox.getItems().removeAll(LanguageProto.NULL_LANGUAGE, LanguageProto.UNRECOGNIZED);
        languageComboBox.setCellFactory(cell -> new EnumCell<>(observableResourceFactory, "Enum.language."));
        languageComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.language."));

        sexComboBox.getItems().addAll(SexEnumProto.values());
        sexComboBox.getItems().removeAll(SexEnumProto.NULL_SEX_ENUM, SexEnumProto.UNRECOGNIZED);
        sexComboBox.setCellFactory(cell -> new EnumCell<>(observableResourceFactory, "Enum.sex."));
        sexComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.sex."));

        nationalityComboBox.getItems().addAll(NationalityProto.values());
        nationalityComboBox.getItems().removeAll(NationalityProto.NULL_NATIONALITY, NationalityProto.UNRECOGNIZED);
        nationalityComboBox.setCellFactory(cell -> new EnumCell<>(observableResourceFactory, "Enum.nationality."));
        nationalityComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.nationality."));

        maritalStatusComboBox.getItems().addAll(MaritalStatusProto.values());
        maritalStatusComboBox.getItems().removeAll(MaritalStatusProto.NULL_MARITAL_STATUS, MaritalStatusProto.UNRECOGNIZED);
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
    }

    private void setupAvsNumberField() {
        setupAvsNumberFieldImpl(avsNumberValue);
    }

    @Override
    public void setupDatePicker() {
        setupDatePickerImpl(birthDateValue);
    }

    private void setupPhoneNumberField() {
        setupPhoneNumberFieldImpl(phoneNumberValue);
    }

    @Override
    public void handleEvents() {
        typeComboBox.setOnAction(event -> handleTypeChange());
//        saveButton.setOnAction(event -> handleSaveButtonOnClick());
        cancelButton.setOnAction(event -> handleCancelButtonOnClick());
    }

    private void handleCancelButtonOnClick() {
        context.send(ViewPartnerPerspective.ID, MessageConstant.INIT);
        context.send(ViewPartnerPerspective.ID.concat(".").concat(ViewPartnerComponent.ID), MessageConstant.SWITCH_TYPE_TO_PERSON);
    }

    @Override
    public void handleTypeChange() {
        PartnerTypeProto selectedType = typeComboBox.getValue();
        if (selectedType == PartnerTypeProto.TYPE_ORGANISATION) {
            context.send(CreatePartnerPerspective.ID.concat(".").concat(CreatePartnerComponent.ID), MessageConstant.SWITCH_TYPE_TO_ORGANISATION);
        } else if (selectedType == PartnerTypeProto.TYPE_PERSON) {
            context.send(CreatePartnerPerspective.ID.concat(".").concat(CreatePartnerComponent.ID), MessageConstant.SWITCH_TYPE_TO_PERSON);
        }
    }

    @Override
    public void validateValues() {

    }
}