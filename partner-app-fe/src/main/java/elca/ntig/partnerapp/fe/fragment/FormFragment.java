package elca.ntig.partnerapp.fe.fragment;

import elca.ntig.partnerapp.common.proto.enums.common.PartnerTypeProto;
import elca.ntig.partnerapp.common.proto.enums.partner.LanguageProto;
import elca.ntig.partnerapp.common.proto.enums.person.NationalityProto;
import elca.ntig.partnerapp.common.proto.enums.person.SexEnumProto;
import elca.ntig.partnerapp.fe.common.cell.EnumCell;
import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Fragment(id = FormFragment.ID,
        viewLocation = ResourceConstant.FORM_FRAGMENT_FXML,
        scope = Scope.PROTOTYPE)
public class FormFragment {
    public static final String ID = "FormFragment";

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

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
        bindTextProperties();
        bindComboBoxPromptTexts();
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
//            languageComboBox.setValue(LanguageProto.NULL_LANGUAGE);
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
        });
    }

    private void validateValues() {
        validateName();
        validateAvsNumber();
        validateDate();
    }

    private void validateName() {
        if (lastNameValue.getText().isEmpty()) {
            lastNameErrorLabel.setVisible(true);
        } else {
            lastNameErrorLabel.setVisible(false);
        }
    }

    private void validateAvsNumber() {
        String avsNumber = avsNumberValue.getText().trim();
        String avsNumberRegex = "^756\\.\\d{4}\\.\\d{4}\\.\\d{2}$"; // 756.xxxx.xxxx.xx

        if ((!avsNumberValue.getText().isEmpty()) && (!avsNumber.matches(avsNumberRegex))) {
            avsNumberErrorLabel.setVisible(true);
        } else {
            avsNumberErrorLabel.setVisible(false);
        }
    }

    private void validateDate() {
        if ((birthDateValue.getValue() != null) && (!birthDateValue.getValue().isBefore(LocalDate.now()))) {
            birthDateErrorLabel.setVisible(true);
        } else {
            birthDateErrorLabel.setVisible(false);
        }
    }

    private void bindTextProperties() {
        // Bind labels and buttons
        bindTextProperty(fragmentTitle, "FormFragment.lbl.fragmentTitle");
        bindTextProperty(createPersonButton, "FormFragment.btn.createPerson");
        bindTextProperty(typeLabel, "FormFragment.lbl.type");
        bindTextProperty(lastNameLabel, "FormFragment.lbl.lastName");
        bindTextProperty(firstNameLabel, "FormFragment.lbl.firstName");
        bindTextProperty(avsNumberLabel, "FormFragment.lbl.avsNumber");
        bindTextProperty(statusLabel, "FormFragment.lbl.status");
        bindTextProperty(correspondenceLanguageLabel, "FormFragment.lbl.correspondenceLanguage");
        bindTextProperty(sexLabel, "FormFragment.lbl.sex");
        bindTextProperty(nationalityLabel, "FormFragment.lbl.nationality");
        bindTextProperty(birthDateLabel, "FormFragment.lbl.birthDate");
        bindTextProperty(clearCriteriaButton, "FormFragment.btn.clearCriteria");
        bindTextProperty(searchButton, "FormFragment.btn.search");
        bindTextProperty(lastNameErrorLabel, "Error.requiredField");
        bindTextProperty(avsNumberErrorLabel, "Error.invalidAvsNumber");
        bindTextProperty(birthDateErrorLabel, "Error.invalidDate");
        bindTextProperty(activeCheckBox, "FormFragment.checkBox.active");
        bindTextProperty(inactiveCheckBox, "FormFragment.checkBox.inactive");
    }

    private void bindComboBoxPromptTexts() {
        // Bind prompt texts for ComboBoxes
        bindPromptTextProperty(languageComboBox, "FormFragment.comboBox.placeholder");
        bindPromptTextProperty(sexComboBox, "FormFragment.comboBox.placeholder");
        bindPromptTextProperty(nationalityComboBox, "FormFragment.comboBox.placeholder");
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

    private void bindTextProperty(Labeled control, String key) {
        control.textProperty().bind(observableResourceFactory.getStringBinding(key));
    }

    private void bindPromptTextProperty(ComboBox<?> comboBox, String key) {
        comboBox.promptTextProperty().bind(observableResourceFactory.getStringBinding(key));
    }
}
