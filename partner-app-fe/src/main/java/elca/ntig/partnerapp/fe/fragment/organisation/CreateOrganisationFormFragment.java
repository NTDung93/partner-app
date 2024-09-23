package elca.ntig.partnerapp.fe.fragment.organisation;

import elca.ntig.partnerapp.common.proto.entity.organisation.CreateOrganisationRequestProto;
import elca.ntig.partnerapp.common.proto.enums.common.PartnerTypeProto;
import elca.ntig.partnerapp.common.proto.enums.organisation.CodeNOGAProto;
import elca.ntig.partnerapp.common.proto.enums.organisation.LegalStatusProto;
import elca.ntig.partnerapp.common.proto.enums.partner.LanguageProto;
import elca.ntig.partnerapp.fe.callback.organisation.CreateOrganisationCallback;
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
@Fragment(id = CreateOrganisationFormFragment.ID,
        viewLocation = ResourceConstant.CREATE_ORGANISATION_FORM_FRAGMENT_FXML,
        scope = Scope.PROTOTYPE)
public class CreateOrganisationFormFragment extends CommonSetupFormFragment implements BaseFormFragment {
    public static final String ID = "CreateOrganisationFormFragment";
    private static Logger logger = Logger.getLogger(CreateOrganisationFormFragment.class);
    CreateOrganisationRequestProto.Builder createOrganisationRequestProto = CreateOrganisationRequestProto.newBuilder();
    private BindingHelper bindingHelper;

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Resource
    private Context context;

    @FXML
    private Label fragmentTitle;

    @FXML
    private Button createAddressButton;

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
        bindingHelper.bindLabelTextProperty(fragmentTitle, "FormFragment.lbl.createFragmentTitle");
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
        bindingHelper.bindPromptTextProperty(languageComboBox, "FormFragment.comboBox.placeholder");
        bindingHelper.bindPromptTextProperty(legalStatusComboBox, "FormFragment.comboBox.placeholder");
        bindingHelper.bindPromptTextProperty(codeNOGAComboBox, "FormFragment.comboBox.placeholder");
    }

    @Override
    public void setupUIControls() {
        setupComboBoxes();
        setupIdeNumberField();
        setupDatePicker();
        setupPhoneNumberField();
        setupErrorLabelVisibility();
    }

    @Override
    public void setupComboBoxes() {
        typeComboBox.getItems().addAll(PartnerTypeProto.values());
        typeComboBox.getItems().removeAll(PartnerTypeProto.UNRECOGNIZED);
        typeComboBox.setValue(PartnerTypeProto.TYPE_ORGANISATION);
        typeComboBox.setCellFactory(cell -> new EnumCell<>(observableResourceFactory, "Enum.type."));
        typeComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.type."));

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
        typeComboBox.setOnAction(event -> handleTypeChange());
        saveButton.setOnAction(event -> handleSaveButtonOnClick());
        cancelButton.setOnAction(event -> handleCancelButtonOnClick());
    }

    private void handleCancelButtonOnClick() {
//        context.send(ViewPartnerPerspective.ID, MessageConstant.SWITCH_TYPE_TO_ORGANISATION);
        context.send(ViewPartnerPerspective.ID, "Init");
        context.send(ViewPartnerPerspective.ID.concat(".").concat(ViewPartnerComponent.ID), MessageConstant.SWITCH_TYPE_TO_ORGANISATION);
    }

    private void handleSaveButtonOnClick() {
        validateValues();
        if (isFormValid()) {
            createOrganisationRequestProto = CreateOrganisationRequestProto.newBuilder();
            createOrganisationRequestProto
                    .setName(nameValue.getText())
                    .setAdditionalName(additionalNameValue.getText())
                    .setIdeNumber(ideNumberValue.getText().replaceAll("[.]", ""))
                    .setLanguage(languageComboBox.getValue())
                    .setPhoneNumber(phoneNumberValue.getText());

            if (codeNOGAComboBox.getValue() != null) {
                createOrganisationRequestProto.setCodeNoga(codeNOGAComboBox.getValue());
            }
            if (legalStatusComboBox.getValue() != null) {
                createOrganisationRequestProto.setLegalStatus(legalStatusComboBox.getValue());
            }
            if (creationDateValue.getValue() != null) {
                createOrganisationRequestProto.setCreationDate(creationDateValue.getValue().toString());
            } else {
                createOrganisationRequestProto.clearCreationDate();
            }

            context.send(CreatePartnerPerspective.ID.concat(".").concat(CreateOrganisationCallback.ID), createOrganisationRequestProto.build());
        }
    }

    private boolean isFormValid() {
        return !nameErrorLabel.isVisible()
                && !ideNumberErrorLabel.isVisible()
                && !languageErrorLabel.isVisible()
                && !creationDateErrorLabel.isVisible()
                && !phoneNumberErrorLabel.isVisible();
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
        validateName(nameValue, nameErrorLabel);
        validateIdeNumber(ideNumberValue, ideNumberErrorLabel);
        validateRequiredComboBox(languageComboBox, languageErrorLabel);
        validateDate(creationDateValue, creationDateErrorLabel);
        validatePhoneNumber(phoneNumberValue, phoneNumberErrorLabel);
    }
}