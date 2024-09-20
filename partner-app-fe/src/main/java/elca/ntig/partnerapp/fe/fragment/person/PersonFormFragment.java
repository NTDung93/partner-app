package elca.ntig.partnerapp.fe.fragment.person;

import elca.ntig.partnerapp.common.proto.entity.person.SearchPeopleCriteriasProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationRequestProto;
import elca.ntig.partnerapp.common.proto.enums.common.PartnerTypeProto;
import elca.ntig.partnerapp.common.proto.enums.common.StatusProto;
import elca.ntig.partnerapp.common.proto.enums.partner.LanguageProto;
import elca.ntig.partnerapp.common.proto.enums.person.NationalityProto;
import elca.ntig.partnerapp.common.proto.enums.person.SexEnumProto;
import elca.ntig.partnerapp.fe.callback.person.SearchPeopleCallback;
import elca.ntig.partnerapp.fe.common.cell.EnumCell;
import elca.ntig.partnerapp.fe.common.constant.ClassNameConstant;
import elca.ntig.partnerapp.fe.common.constant.MessageConstant;
import elca.ntig.partnerapp.fe.common.constant.PaginationConstant;
import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
import elca.ntig.partnerapp.fe.common.pagination.PaginationModel;
import elca.ntig.partnerapp.fe.component.ViewPartnerComponent;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import elca.ntig.partnerapp.fe.fragment.BaseFormFragment;
import elca.ntig.partnerapp.fe.perspective.ViewPartnerPerspective;
import elca.ntig.partnerapp.fe.utils.BindingHelper;
import elca.ntig.partnerapp.fe.utils.BasicSetupFormFragment;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Fragment(id = PersonFormFragment.ID,
        viewLocation = ResourceConstant.PERSON_FORM_FRAGMENT_FXML,
        scope = Scope.PROTOTYPE)
public class PersonFormFragment extends BasicSetupFormFragment implements BaseFormFragment {
    public static final String ID = "FormFragment";
    private static Logger logger = Logger.getLogger(PersonFormFragment.class);
    SearchPeopleCriteriasProto.Builder searchPeopleCriteriaProto = SearchPeopleCriteriasProto.newBuilder();
    SearchPeoplePaginationRequestProto.Builder searchPeoplePaginationRequestProto = SearchPeoplePaginationRequestProto.newBuilder();
    private BindingHelper bindingHelper;

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Resource
    private Context context;

    @FXML
    private Label fragmentTitle;

    @FXML
    private Button createPersonButton;

    @FXML
    private Text typeLabel;

    @FXML
    private Text lastNameLabel;

    @FXML
    private TextField lastNameValue;

    @FXML
    private Label lastNameErrorLabel;

    @FXML
    private Label firstNameLabel;

    @FXML
    private TextField firstNameValue;

    @FXML
    private Label avsNumberLabel;

    @FXML
    private TextField avsNumberValue;

    @FXML
    private Label avsNumberErrorLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label correspondenceLanguageLabel;

    @FXML
    private Label sexLabel;

    @FXML
    private Label nationalityLabel;

    @FXML
    private Label birthDateLabel;

    @FXML
    private DatePicker birthDateValue;

    @FXML
    private Label birthDateErrorLabel;

    @FXML
    private Button clearCriteriaButton;

    @FXML
    private Button searchButton;

    @FXML
    private ComboBox<PartnerTypeProto> typeComboBox;

    @FXML
    private ComboBox<LanguageProto> languageComboBox;

    @FXML
    private ComboBox<SexEnumProto> sexComboBox;

    @FXML
    private ComboBox<NationalityProto> nationalityComboBox;

    @FXML
    private CheckBox activeCheckBox;

    @FXML
    private CheckBox inactiveCheckBox;

    public void init() {
        bindingHelper = new BindingHelper(observableResourceFactory);
        bindTextProperties();
        setupUIControls();
        handleEvents();
    }

    @Override
    public void bindTextProperties() {
        bindingHelper.bindLabelTextProperty(fragmentTitle, "FormFragment.lbl.fragmentTitle");
        bindingHelper.bindLabelTextProperty(createPersonButton, "FormFragment.btn.createPerson");
        bindingHelper.bindTextProperty(typeLabel, "FormFragment.lbl.type");
        bindingHelper.bindTextProperty(lastNameLabel, "FormFragment.lbl.lastName");
        bindingHelper.bindLabelTextProperty(firstNameLabel, "FormFragment.lbl.firstName");
        bindingHelper.bindLabelTextProperty(avsNumberLabel, "FormFragment.lbl.avsNumber");
        bindingHelper.bindLabelTextProperty(statusLabel, "FormFragment.lbl.status");
        bindingHelper.bindLabelTextProperty(correspondenceLanguageLabel, "FormFragment.lbl.correspondenceLanguage");
        bindingHelper.bindLabelTextProperty(sexLabel, "FormFragment.lbl.sex");
        bindingHelper.bindLabelTextProperty(nationalityLabel, "FormFragment.lbl.nationality");
        bindingHelper.bindLabelTextProperty(birthDateLabel, "FormFragment.lbl.birthDate");
        bindingHelper.bindLabelTextProperty(clearCriteriaButton, "FormFragment.btn.clearCriteria");
        bindingHelper.bindLabelTextProperty(searchButton, "FormFragment.btn.search");
        bindingHelper.bindLabelTextProperty(lastNameErrorLabel, "Error.requiredField");
        bindingHelper.bindLabelTextProperty(avsNumberErrorLabel, "Error.invalidAvsNumber");
        bindingHelper.bindLabelTextProperty(birthDateErrorLabel, "Error.invalidDate");
        bindingHelper.bindLabelTextProperty(activeCheckBox, "FormFragment.checkBox.active");
        bindingHelper.bindLabelTextProperty(inactiveCheckBox, "FormFragment.checkBox.inactive");
        bindingHelper.bindPromptTextProperty(languageComboBox, "FormFragment.comboBox.placeholder");
        bindingHelper.bindPromptTextProperty(sexComboBox, "FormFragment.comboBox.placeholder");
        bindingHelper.bindPromptTextProperty(nationalityComboBox, "FormFragment.comboBox.placeholder");
    }

    @Override
    public void setupUIControls() {
        setupComboBoxes();
        setupAvsNumberField();
        setupDatePicker();
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
    }

    @Override
    public void setupErrorLabelVisibility() {
        lastNameErrorLabel.setVisible(false);
        avsNumberErrorLabel.setVisible(false);
        birthDateErrorLabel.setVisible(false);
    }

    private void setupAvsNumberField() {
        setupAvsNumberFieldImpl(avsNumberValue);
    }

    @Override
    public void setupDatePicker() {
        setupDatePickerImpl(birthDateValue);
    }

    @Override
    public void handleEvents() {
        clearCriteriaButton.setOnAction(event -> handleClearCriteriaButtonOnClick());
        searchButton.setOnAction(event -> handleSearchButtonOnClick());
        typeComboBox.setOnAction(event -> handleTypeChange());
    }

    @Override
    public void handlePagination(PaginationModel paginationModel) {
        searchPeoplePaginationRequestProto
                .setPageNo(paginationModel.getPageNo())
                .setPageSize(paginationModel.getPageSize())
                .setSortBy(paginationModel.getSortBy())
                .setSortDir(paginationModel.getSortDir())
                .setCriterias(searchPeopleCriteriaProto.build());
        context.send(ViewPartnerPerspective.ID.concat(".").concat(SearchPeopleCallback.ID), searchPeoplePaginationRequestProto.build());
    }

    @Override
    public void handleClearCriteriaButtonOnClick() {
        lastNameValue.clear();
        firstNameValue.clear();
        avsNumberValue.clear();
        activeCheckBox.setSelected(true);
        inactiveCheckBox.setSelected(true);
        languageComboBox.setValue(null);
        sexComboBox.setValue(null);
        nationalityComboBox.setValue(null);
        birthDateValue.setValue(null);
        lastNameValue.getStyleClass().remove(ClassNameConstant.ERROR_INPUT);
        avsNumberValue.getStyleClass().remove(ClassNameConstant.ERROR_INPUT);
        birthDateValue.getStyleClass().remove(ClassNameConstant.ERROR_INPUT);
        setupErrorLabelVisibility();
        context.send(ViewPartnerPerspective.ID.concat(".").concat(ViewPartnerComponent.ID), MessageConstant.RESET_SORT_POLICY_FOR_PERSON);
    }

    @Override
    public void handleSearchButtonOnClick() {
        validateValues();
        if (!lastNameErrorLabel.isVisible() && !avsNumberErrorLabel.isVisible() && !birthDateErrorLabel.isVisible()) {
            searchPeopleCriteriaProto = SearchPeopleCriteriasProto.newBuilder();
            searchPeopleCriteriaProto
                    .setLastName(lastNameValue.getText())
                    .setFirstName(firstNameValue.getText())
                    .setAvsNumber(avsNumberValue.getText().replaceAll("\\.", ""))
                    .addAllStatus(getStatuses());

            if (languageComboBox.getValue() != null) {
                searchPeopleCriteriaProto.setLanguage(languageComboBox.getValue());
            }
            if (sexComboBox.getValue() != null) {
                searchPeopleCriteriaProto.setSex(sexComboBox.getValue());
            }
            if (nationalityComboBox.getValue() != null) {
                searchPeopleCriteriaProto.setNationality(nationalityComboBox.getValue());
            }
            if (birthDateValue.getValue() != null) {
                searchPeopleCriteriaProto.setBirthDate(birthDateValue.getValue().toString());
            } else {
                searchPeopleCriteriaProto.clearBirthDate();
            }
            SearchPeoplePaginationRequestProto searchPeoplePaginationRequestProto = SearchPeoplePaginationRequestProto.newBuilder()
                    .setPageNo(PaginationConstant.DEFAULT_PAGE_NO)
                    .setPageSize(PaginationConstant.DEFAULT_PAGE_SIZE)
                    .setSortBy(PaginationConstant.DEFAULT_SORT_BY)
                    .setSortDir(PaginationConstant.DEFAULT_SORT_DIRECTION)
                    .setCriterias(searchPeopleCriteriaProto.build())
                    .build();
            context.send(ViewPartnerPerspective.ID.concat(".").concat(SearchPeopleCallback.ID), searchPeoplePaginationRequestProto);
        }
    }

    @Override
    public void handleTypeChange() {
        PartnerTypeProto selectedType = typeComboBox.getValue();
        if (selectedType == PartnerTypeProto.TYPE_ORGANISATION) {
            context.send(ViewPartnerPerspective.ID.concat(".").concat(ViewPartnerComponent.ID), MessageConstant.SWITCH_TYPE_TO_ORGANISATION);
        } else if (selectedType == PartnerTypeProto.TYPE_PERSON) {
            context.send(ViewPartnerPerspective.ID.concat(".").concat(ViewPartnerComponent.ID), MessageConstant.SWITCH_TYPE_TO_PERSON);
        }
    }

    @Override
    public List<StatusProto> getStatuses() {
        return getStatusesImpl(activeCheckBox, inactiveCheckBox);
    }

    @Override
    public void validateValues() {
        validateName();
        validateAvsNumber();
        validateDate();
    }

    private void validateName() {
        if (lastNameValue.getText().isEmpty()) {
            lastNameErrorLabel.setVisible(true);
            if (!lastNameValue.getStyleClass().contains(ClassNameConstant.ERROR_INPUT)) {
                lastNameValue.getStyleClass().add(ClassNameConstant.ERROR_INPUT);
            }
        } else {
            lastNameErrorLabel.setVisible(false);
            lastNameValue.getStyleClass().removeAll(ClassNameConstant.ERROR_INPUT);
        }
    }

    private void validateAvsNumber() {
        String avsNumber = avsNumberValue.getText().trim().replaceAll("\\.", "");
        String avsNumberRegex = "^756\\d{10}$"; // 756.xxxx.xxxx.xx
        if ((!avsNumberValue.getText().isEmpty()) && (!avsNumber.matches(avsNumberRegex))) {
            avsNumberErrorLabel.setVisible(true);
            if (!avsNumberValue.getStyleClass().contains(ClassNameConstant.ERROR_INPUT)) {
                avsNumberValue.getStyleClass().add(ClassNameConstant.ERROR_INPUT);
            }
        } else {
            avsNumberErrorLabel.setVisible(false);
            avsNumberValue.getStyleClass().removeAll(ClassNameConstant.ERROR_INPUT);
        }
    }

    private void validateDate() {
        if ((birthDateValue.getValue() != null) && (!birthDateValue.getValue().isBefore(LocalDate.now()))) {
            birthDateErrorLabel.setVisible(true);
            if (!birthDateValue.getStyleClass().contains(ClassNameConstant.ERROR_INPUT)) {
                birthDateValue.getStyleClass().add(ClassNameConstant.ERROR_INPUT);
            }
        } else {
            birthDateErrorLabel.setVisible(false);
            birthDateValue.getStyleClass().removeAll(ClassNameConstant.ERROR_INPUT);
        }
    }

}
