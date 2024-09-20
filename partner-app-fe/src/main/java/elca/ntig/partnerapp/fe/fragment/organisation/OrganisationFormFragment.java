package elca.ntig.partnerapp.fe.fragment.organisation;

import elca.ntig.partnerapp.common.proto.entity.organisation.SearchOrganisationCriteriasProto;
import elca.ntig.partnerapp.common.proto.entity.organisation.SearchOrganisationPaginationRequestProto;
import elca.ntig.partnerapp.common.proto.enums.common.PartnerTypeProto;
import elca.ntig.partnerapp.common.proto.enums.organisation.LegalStatusProto;
import elca.ntig.partnerapp.common.proto.enums.partner.LanguageProto;
import elca.ntig.partnerapp.common.proto.enums.person.NationalityProto;
import elca.ntig.partnerapp.common.proto.enums.person.SexEnumProto;
import elca.ntig.partnerapp.fe.common.cell.EnumCell;
import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import elca.ntig.partnerapp.fe.fragment.BaseFormFragment;
import elca.ntig.partnerapp.fe.utils.BindingHelper;
import elca.ntig.partnerapp.fe.utils.SetupDatePickerHelper;
import elca.ntig.partnerapp.fe.utils.SetupInputFieldHelper;
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
@Fragment(id = OrganisationFormFragment.ID,
        viewLocation = ResourceConstant.ORGANISATION_FORM_FRAGMENT_FXML,
        scope = Scope.PROTOTYPE)
public class OrganisationFormFragment extends SetupInputFieldHelper implements BaseFormFragment {

    public static final String ID = "OrganisationFormFragment";
    private static Logger logger = Logger.getLogger(OrganisationFormFragment.class);
    SearchOrganisationCriteriasProto.Builder searchOrganisationCriteriaProto = SearchOrganisationCriteriasProto.newBuilder();
    SearchOrganisationPaginationRequestProto.Builder searchOrganisationPaginationRequestProto = SearchOrganisationPaginationRequestProto.newBuilder();
    private BindingHelper bindingHelper;

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Resource
    private Context context;

    @FXML
    private Label fragmentTitle;

    @FXML
    private Button createOrganisationButton;

    @FXML
    private Text typeLabel;

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
    private Label statusLabel;

    @FXML
    private Label correspondenceLanguageLabel;

    @FXML
    private Label legalStatusLabel;

    @FXML
    private Label creationDateLabel;

    @FXML
    private DatePicker creationDateValue;

    @FXML
    private Label creationDateErrorLabel;

    @FXML
    private Button clearCriteriaButton;

    @FXML
    private Button searchButton;

    @FXML
    private ComboBox<PartnerTypeProto> typeComboBox;

    @FXML
    private ComboBox<LanguageProto> languageComboBox;

    @FXML
    private ComboBox<LegalStatusProto> legalStatusComboBox;

    @FXML
    private CheckBox activeCheckBox;

    @FXML
    private CheckBox inactiveCheckBox;

    public void init() {
        bindingHelper = new BindingHelper(observableResourceFactory);
        bindTextProperties();
        setupComboBoxes();
        setupVisibility();
        setupIdeNumberField();
        setupDatePicker();
        handleEvents();
    }

    @Override
    public void bindTextProperties() {
        bindingHelper.bindLabelTextProperty(fragmentTitle, "FormFragment.lbl.fragmentTitle");
        bindingHelper.bindLabelTextProperty(createOrganisationButton, "FormFragment.btn.createOrganisation");
        bindingHelper.bindTextProperty(typeLabel, "FormFragment.lbl.type");
        bindingHelper.bindTextProperty(nameLabel, "FormFragment.lbl.lastName");
        bindingHelper.bindLabelTextProperty(additionalNameLabel, "FormFragment.lbl.additionalName");
        bindingHelper.bindLabelTextProperty(ideNumberLabel, "FormFragment.lbl.ideNumber");
        bindingHelper.bindLabelTextProperty(statusLabel, "FormFragment.lbl.status");
        bindingHelper.bindLabelTextProperty(correspondenceLanguageLabel, "FormFragment.lbl.correspondenceLanguage");
        bindingHelper.bindLabelTextProperty(legalStatusLabel, "FormFragment.lbl.legalStatus");
        bindingHelper.bindLabelTextProperty(creationDateLabel, "FormFragment.lbl.creationDate");
        bindingHelper.bindLabelTextProperty(clearCriteriaButton, "FormFragment.btn.clearCriteria");
        bindingHelper.bindLabelTextProperty(searchButton, "FormFragment.btn.search");
        bindingHelper.bindLabelTextProperty(nameErrorLabel, "Error.requiredField");
        bindingHelper.bindLabelTextProperty(ideNumberErrorLabel, "Error.invalidIdeNumber");
        bindingHelper.bindLabelTextProperty(creationDateErrorLabel, "Error.invalidDate");
        bindingHelper.bindLabelTextProperty(activeCheckBox, "FormFragment.checkBox.active");
        bindingHelper.bindLabelTextProperty(inactiveCheckBox, "FormFragment.checkBox.inactive");
        bindingHelper.bindPromptTextProperty(languageComboBox, "FormFragment.comboBox.placeholder");
        bindingHelper.bindPromptTextProperty(legalStatusComboBox, "FormFragment.comboBox.placeholder");
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

        legalStatusComboBox.getItems().addAll(LegalStatusProto.values());
        legalStatusComboBox.getItems().removeAll(LegalStatusProto.NULL_LEGAL_STATUS, LegalStatusProto.UNRECOGNIZED);
        legalStatusComboBox.setCellFactory(cell -> new EnumCell<>(observableResourceFactory, "Enum.legalStatus."));
        legalStatusComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.legalStatus."));
    }

    @Override
    public void setupVisibility() {
        nameErrorLabel.setVisible(false);
        ideNumberErrorLabel.setVisible(false);
        creationDateErrorLabel.setVisible(false);
    }

    private void setupIdeNumberField() {
       setupIdeNumberFieldImpl(ideNumberValue);
    }

    @Override
    public void setupDatePicker() {
        setupDatePickerImpl(creationDateValue);
    }

    @Override
    public void handleEvents() {

    }
}
