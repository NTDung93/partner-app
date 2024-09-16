package elca.ntig.partnerapp.fe.fragment;

import elca.ntig.partnerapp.common.proto.entity.person.SearchPeopleCriteriasProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationRequestProto;
import elca.ntig.partnerapp.common.proto.enums.common.PartnerTypeProto;
import elca.ntig.partnerapp.common.proto.enums.common.StatusProto;
import elca.ntig.partnerapp.common.proto.enums.partner.LanguageProto;
import elca.ntig.partnerapp.common.proto.enums.person.NationalityProto;
import elca.ntig.partnerapp.common.proto.enums.person.SexEnumProto;
import elca.ntig.partnerapp.fe.callback.person.SearchPeopleCallback;
import elca.ntig.partnerapp.fe.common.cell.EnumCell;
import elca.ntig.partnerapp.fe.common.constant.PaginationConstant;
import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import elca.ntig.partnerapp.fe.perspective.ViewPartnerPerspective;
import elca.ntig.partnerapp.fe.utils.BindingHelper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
@Fragment(id = FormFragment.ID,
        viewLocation = ResourceConstant.FORM_FRAGMENT_FXML,
        scope = Scope.PROTOTYPE)
public class FormFragment {
    public static final String ID = "FormFragment";

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    private BindingHelper bindingHelper;

    @Resource
    private Context context;

    @FXML
    private Label fragmentTitle;

    @FXML
    private Button createPersonButton;

    @FXML
    private Label typeLabel;

    @FXML
    private Label lastNameLabel;

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
        setupComboBoxes();
        setupVisibility();
        handleClearCriteriaButtonOnClick();
        handleSearchButtonOnClick();
    }

    private void setupVisibility() {
        lastNameErrorLabel.setVisible(false);
        avsNumberErrorLabel.setVisible(false);
        birthDateErrorLabel.setVisible(false);
    }

    private void handleClearCriteriaButtonOnClick() {
        clearCriteriaButton.setOnAction(event -> {
            lastNameValue.clear();
            firstNameValue.clear();
            avsNumberValue.clear();
            activeCheckBox.setSelected(false);
            inactiveCheckBox.setSelected(false);
            languageComboBox.setValue(null);
            sexComboBox.setValue(null);
            nationalityComboBox.setValue(null);
            birthDateValue.setValue(null);
            setupVisibility();
        });
    }

    private void handleSearchButtonOnClick() {
        searchButton.setOnAction(event -> {
            validateValues();
            SearchPeopleCriteriasProto.Builder searchPeopleCriteriasProto = SearchPeopleCriteriasProto.newBuilder();
            searchPeopleCriteriasProto
                    .setLastName(lastNameValue.getText())
                    .setFirstName(firstNameValue.getText())
                    .setAvsNumber(avsNumberValue.getText())
                    .addAllStatus(getStatuses());

            if (languageComboBox.getValue() != null) {
                searchPeopleCriteriasProto.setLanguage(languageComboBox.getValue());
            }
            if (sexComboBox.getValue() != null) {
                searchPeopleCriteriasProto.setSex(sexComboBox.getValue());
            }
            if (nationalityComboBox.getValue() != null) {
                searchPeopleCriteriasProto.setNationality(nationalityComboBox.getValue());
            }
            if (birthDateValue.getValue() != null) {
                searchPeopleCriteriasProto.setBirthDate(birthDateValue.getValue().toString());
            }
            SearchPeoplePaginationRequestProto searchPeoplePaginationRequestProto = SearchPeoplePaginationRequestProto.newBuilder()
                    .setPageNo(0)
                    .setPageSize(5)
                    .setSortBy(PaginationConstant.DEFAULT_SORT_BY)
                    .setSortDir(PaginationConstant.DEFAULT_SORT_DIRECTION)
                    .setCriterias(searchPeopleCriteriasProto.build())
                    .build();
            context.send(ViewPartnerPerspective.ID.concat(".").concat(SearchPeopleCallback.ID), searchPeoplePaginationRequestProto);
        });
    }

    private List<StatusProto> getStatuses() {
        List<StatusProto> statuses = new ArrayList<>();
        if (activeCheckBox.isSelected()) {
            statuses.add(StatusProto.ACTIVE);
        }
        if (inactiveCheckBox.isSelected()) {
            statuses.add(StatusProto.INACTIVE);
        }
        return statuses;
    }

    private void validateValues() {
        validateName();
        validateAvsNumber();
        validateDate();
    }

    private void validateName() {
        lastNameErrorLabel.setVisible(lastNameValue.getText().isEmpty());
    }

    private void validateAvsNumber() {
        String avsNumber = avsNumberValue.getText().trim();
        String avsNumberRegex = "^756\\d{10}$"; // 756.xxxx.xxxx.xx
        avsNumberErrorLabel.setVisible((!avsNumberValue.getText().isEmpty()) && (!avsNumber.matches(avsNumberRegex)));
    }

    private void validateDate() {
        birthDateErrorLabel.setVisible((birthDateValue.getValue() != null) && (!birthDateValue.getValue().isBefore(LocalDate.now())));
    }

    private void bindTextProperties() {
        // Bind labels and buttons
        bindingHelper.bindLabelTextProperty(fragmentTitle, "FormFragment.lbl.fragmentTitle");
        bindingHelper.bindLabelTextProperty(createPersonButton, "FormFragment.btn.createPerson");
        bindingHelper.bindLabelTextProperty(typeLabel, "FormFragment.lbl.type");
        bindingHelper.bindLabelTextProperty(lastNameLabel, "FormFragment.lbl.lastName");
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

    private void setupComboBoxes() {
        typeComboBox.getItems().addAll(PartnerTypeProto.values());
        typeComboBox.getItems().removeAll(PartnerTypeProto.UNRECOGNIZED);
        typeComboBox.setValue(PartnerTypeProto.TYPE_PERSON);
        typeComboBox.setCellFactory(cb -> new EnumCell<>(observableResourceFactory, "Enum.type.", 5));
        typeComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.type.", 5));

        languageComboBox.getItems().addAll(LanguageProto.values());
        languageComboBox.getItems().removeAll(LanguageProto.NULL_LANGUAGE, LanguageProto.UNRECOGNIZED);
        languageComboBox.setCellFactory(cb -> new EnumCell<>(observableResourceFactory, "Enum.language.", 5));
        languageComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.language.", 5));
//        languageComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
//            if (newVal == null) {
//                languageComboBox.setValue(LanguageProto.NULL_LANGUAGE);
//            }
//        });

        sexComboBox.getItems().addAll(SexEnumProto.values());
        sexComboBox.getItems().removeAll(SexEnumProto.NULL_SEX_ENUM, SexEnumProto.UNRECOGNIZED);
        sexComboBox.setCellFactory(cb -> new EnumCell<>(observableResourceFactory, "Enum.sex.", 0));
        sexComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.sex.", 0));
//        sexComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
//            if (newVal == null) {
//                sexComboBox.setValue(SexEnumProto.NULL_SEX_ENUM);
//            }
//        });

        nationalityComboBox.getItems().addAll(NationalityProto.values());
        nationalityComboBox.getItems().removeAll(NationalityProto.NULL_NATIONALITY, NationalityProto.UNRECOGNIZED);
        nationalityComboBox.setCellFactory(cb -> new EnumCell<>(observableResourceFactory, "Enum.nationality.", 12));
        nationalityComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.nationality.", 12));
//        nationalityComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
//            if (newVal == null) {
//                nationalityComboBox.setValue(NationalityProto.NULL_NATIONALITY);
//            }
//        });
    }
}
