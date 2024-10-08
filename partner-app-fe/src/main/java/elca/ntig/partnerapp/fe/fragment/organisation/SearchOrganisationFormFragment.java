package elca.ntig.partnerapp.fe.fragment.organisation;

import elca.ntig.partnerapp.common.proto.entity.organisation.SearchOrganisationCriteriasProto;
import elca.ntig.partnerapp.common.proto.entity.organisation.SearchOrganisationPaginationRequestProto;
import elca.ntig.partnerapp.common.proto.enums.common.PartnerTypeProto;
import elca.ntig.partnerapp.common.proto.enums.organisation.LegalStatusProto;
import elca.ntig.partnerapp.common.proto.enums.partner.LanguageProto;
import elca.ntig.partnerapp.fe.callback.organisation.SearchOrganisationCallback;
import elca.ntig.partnerapp.fe.common.cell.EnumCell;
import elca.ntig.partnerapp.fe.common.constant.ClassNameConstant;
import elca.ntig.partnerapp.fe.common.constant.MessageConstant;
import elca.ntig.partnerapp.fe.common.constant.PaginationConstant;
import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
import elca.ntig.partnerapp.fe.common.model.PaginationModel;
import elca.ntig.partnerapp.fe.component.CreatePartnerComponent;
import elca.ntig.partnerapp.fe.component.ViewPartnerComponent;
import elca.ntig.partnerapp.fe.perspective.CreatePartnerPerspective;
import elca.ntig.partnerapp.fe.utils.ObservableResourceFactory;
import elca.ntig.partnerapp.fe.fragment.BaseFormFragment;
import elca.ntig.partnerapp.fe.perspective.ViewPartnerPerspective;
import elca.ntig.partnerapp.fe.utils.BindingHelper;
import elca.ntig.partnerapp.fe.fragment.common.CommonSetupFormFragment;
import javafx.application.Platform;
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
@Fragment(id = SearchOrganisationFormFragment.ID,
        viewLocation = ResourceConstant.SEARCH_ORGANISATION_FORM_FRAGMENT_FXML,
        scope = Scope.PROTOTYPE)
public class SearchOrganisationFormFragment extends CommonSetupFormFragment implements BaseFormFragment {

    public static final String ID = "OrganisationFormFragment";
    private static Logger logger = Logger.getLogger(SearchOrganisationFormFragment.class);
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
        handleEvents();
        setupUIControls();
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
    public void setupUIControls() {
        setupComboBoxes();
        setupIdeNumberField();
        setupDatePicker();
        setupErrorLabelVisibility();
        setupComboBoxNullOptionListener();
    }

    private void setupComboBoxNullOptionListener() {
        languageComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == LanguageProto.NULL_LANGUAGE) {
                Platform.runLater(() -> {
                    languageComboBox.setValue(null);
                });
            }
        });

        legalStatusComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == LegalStatusProto.NULL_LEGAL_STATUS) {
                Platform.runLater(() -> {
                    legalStatusComboBox.setValue(null);
                });
            }
        });
    }

    @Override
    public void setupComboBoxes() {
        typeComboBox.getItems().addAll(PartnerTypeProto.values());
        typeComboBox.getItems().removeAll(PartnerTypeProto.UNRECOGNIZED);
        typeComboBox.setValue(PartnerTypeProto.TYPE_ORGANISATION);
        typeComboBox.setCellFactory(cell -> new EnumCell<>(observableResourceFactory, "Enum.type."));
        typeComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.type."));

        languageComboBox.getItems().addAll(LanguageProto.values());
        languageComboBox.getItems().removeAll(LanguageProto.UNRECOGNIZED);
        languageComboBox.setCellFactory(cell -> new EnumCell<>(observableResourceFactory, "Enum.language."));
        languageComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.language."));

        legalStatusComboBox.getItems().addAll(LegalStatusProto.values());
        legalStatusComboBox.getItems().removeAll(LegalStatusProto.UNRECOGNIZED);
        legalStatusComboBox.setCellFactory(cell -> new EnumCell<>(observableResourceFactory, "Enum.legalStatus."));
        legalStatusComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.legalStatus."));
    }

    @Override
    public void setupErrorLabelVisibility() {
        nameErrorLabel.setVisible(false);
        ideNumberErrorLabel.setVisible(false);
        creationDateErrorLabel.setVisible(false);
    }

    private void setupIdeNumberField() {
       setupIdeNumberFieldImpl(ideNumberValue);
    }

    @Override
    public void setupDatePicker() {
        setupDatePickerImpl(creationDateValue, true);
    }

    @Override
    public void handleEvents() {
        clearCriteriaButton.setOnAction(event -> handleClearCriteriaButtonOnClick());
        searchButton.setOnAction(event -> handleSearchButtonOnClick());
        typeComboBox.setOnAction(event -> handleTypeChange());
        createOrganisationButton.setOnAction(event -> handleCreateOrganisationButtonOnClick());
    }

    private void handleCreateOrganisationButtonOnClick() {
//        context.send(CreatePartnerPerspective.ID, MessageConstant.SWITCH_TYPE_TO_ORGANISATION);
        context.send(CreatePartnerPerspective.ID, MessageConstant.INIT);
        context.send(CreatePartnerPerspective.ID.concat(".").concat(CreatePartnerComponent.ID), MessageConstant.SWITCH_TYPE_TO_ORGANISATION);
    }

    public void handlePagination(PaginationModel paginationModel) {
        searchOrganisationPaginationRequestProto
                .setPageNo(paginationModel.getPageNo())
                .setPageSize(paginationModel.getPageSize())
                .setSortBy(paginationModel.getSortBy())
                .setSortDir(paginationModel.getSortDir())
                .setCriterias(searchOrganisationCriteriaProto.build());
        context.send(ViewPartnerPerspective.ID.concat(".").concat(SearchOrganisationCallback.ID), searchOrganisationPaginationRequestProto.build());
    }

    public void handleClearCriteriaButtonOnClick() {
        nameValue.clear();
        additionalNameValue.clear();
        ideNumberValue.clear();
        activeCheckBox.setSelected(true);
        inactiveCheckBox.setSelected(true);
        languageComboBox.setValue(null);
        legalStatusComboBox.setValue(null);
        creationDateValue.setValue(null);
        nameValue.getStyleClass().remove(ClassNameConstant.ERROR_INPUT);
        ideNumberValue.getStyleClass().remove(ClassNameConstant.ERROR_INPUT);
        creationDateValue.getStyleClass().remove(ClassNameConstant.ERROR_INPUT);
        setupErrorLabelVisibility();
        context.send(ViewPartnerPerspective.ID.concat(".").concat(ViewPartnerComponent.ID), MessageConstant.RESET_SORT_POLICY_FOR_ORGANISATION);
    }

    public void handleSearchButtonOnClick() {
        validateValues();
        if (!nameErrorLabel.isVisible() && !ideNumberErrorLabel.isVisible() && !creationDateErrorLabel.isVisible()) {
            searchOrganisationCriteriaProto = SearchOrganisationCriteriasProto.newBuilder();
            searchOrganisationCriteriaProto
                    .setName(nameValue.getText())
                    .setAdditionalName(additionalNameValue.getText())
                    .setIdeNumber(ideNumberValue.getText().replaceAll("[.]", ""))
                    .addAllStatus(getStatusesImpl(activeCheckBox, inactiveCheckBox));

            if (languageComboBox.getValue() != null) {
                searchOrganisationCriteriaProto.setLanguage(languageComboBox.getValue());
            }
            if (legalStatusComboBox.getValue() != null) {
                searchOrganisationCriteriaProto.setLegalStatus(legalStatusComboBox.getValue());
            }
            if (creationDateValue.getValue() != null) {
                searchOrganisationCriteriaProto.setCreationDate(creationDateValue.getValue().toString());
            } else {
                searchOrganisationCriteriaProto.clearCreationDate();
            }
            SearchOrganisationPaginationRequestProto searchOrganisationPaginationRequestProto = SearchOrganisationPaginationRequestProto.newBuilder()
                    .setPageNo(PaginationConstant.DEFAULT_PAGE_NO)
                    .setPageSize(PaginationConstant.DEFAULT_PAGE_SIZE)
                    .setSortBy(PaginationConstant.DEFAULT_SORT_BY)
                    .setSortDir(PaginationConstant.DEFAULT_SORT_DIRECTION)
                    .setCriterias(searchOrganisationCriteriaProto.build())
                    .build();
            context.send(ViewPartnerPerspective.ID.concat(".").concat(SearchOrganisationCallback.ID), searchOrganisationPaginationRequestProto);
        }
    }

    public void handleTypeChange() {
        PartnerTypeProto selectedType = typeComboBox.getValue();
        if (selectedType == PartnerTypeProto.TYPE_ORGANISATION) {
            context.send(ViewPartnerPerspective.ID.concat(".").concat(ViewPartnerComponent.ID), MessageConstant.SWITCH_TYPE_TO_ORGANISATION);
        } else if (selectedType == PartnerTypeProto.TYPE_PERSON) {
            context.send(ViewPartnerPerspective.ID.concat(".").concat(ViewPartnerComponent.ID), MessageConstant.SWITCH_TYPE_TO_PERSON);
        }
    }

    @Override
    public void validateValues() {
        validateRequiredTextField(nameValue, nameErrorLabel);
        validateIdeNumber(ideNumberValue, ideNumberErrorLabel);
        validateDate(creationDateValue, creationDateErrorLabel);
    }
}
