package elca.ntig.partnerapp.fe.fragment;

import elca.ntig.partnerapp.common.proto.enums.partner.LanguageProto;
import elca.ntig.partnerapp.common.proto.enums.person.NationalityProto;
import elca.ntig.partnerapp.common.proto.enums.person.SexEnumProto;
import elca.ntig.partnerapp.fe.common.cell.EnumCell;
import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    private Label firstNameLabel;

    @FXML
    private Label avsNumberLabel;

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
    private Button removeCriteriasButton;

    @FXML
    private Button searchButton;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private ComboBox<LanguageProto> languageComboBox;

    @FXML
    private ComboBox<SexEnumProto> sexComboBox;

    @FXML
    private ComboBox<NationalityProto> nationalityComboBox;

    public void init() {
        bindTextProperties();
        bindComboBoxPromptTexts();
        setupComboBoxes();
    }

    private void bindTextProperties() {
        // Bind labels and buttons to resource strings
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
        bindTextProperty(removeCriteriasButton, "FormFragment.btn.removeCriterias");
        bindTextProperty(searchButton, "FormFragment.btn.search");
    }

    private void bindComboBoxPromptTexts() {
        // Bind prompt texts for ComboBoxes
        bindPromptTextProperty(languageComboBox, "FormFragment.comboBox.placeholder");
        bindPromptTextProperty(sexComboBox, "FormFragment.comboBox.placeholder");
        bindPromptTextProperty(nationalityComboBox, "FormFragment.comboBox.placeholder");
    }

    private void setupComboBoxes(){
        languageComboBox.getItems().addAll(LanguageProto.values());
        languageComboBox.getItems().removeAll(LanguageProto.NULL_LANGUAGE, LanguageProto.UNRECOGNIZED);
        languageComboBox.setCellFactory(cb -> new EnumCell<>(observableResourceFactory, "Enum.language.", 5));
        languageComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.language.", 5));
        languageComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                languageComboBox.setValue(LanguageProto.NULL_LANGUAGE);
            }
        });

        sexComboBox.getItems().addAll(SexEnumProto.values());
        sexComboBox.getItems().removeAll(SexEnumProto.NULL_SEX_ENUM, SexEnumProto.UNRECOGNIZED);
        sexComboBox.setCellFactory(cb -> new EnumCell<>(observableResourceFactory, "Enum.sex.", 0));
        sexComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.sex.", 0));
        sexComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                sexComboBox.setValue(SexEnumProto.NULL_SEX_ENUM);
            }
        });

        nationalityComboBox.getItems().addAll(NationalityProto.values());
        nationalityComboBox.getItems().removeAll(NationalityProto.NULL_NATIONALITY, NationalityProto.UNRECOGNIZED);
        nationalityComboBox.setCellFactory(cb -> new EnumCell<>(observableResourceFactory, "Enum.nationality.", 12));
        nationalityComboBox.setButtonCell(new EnumCell<>(observableResourceFactory, "Enum.nationality.", 12));
        nationalityComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                nationalityComboBox.setValue(NationalityProto.NULL_NATIONALITY);
            }
        });
    }

    private void bindTextProperty(Labeled control, String key) {
        control.textProperty().bind(observableResourceFactory.getStringBinding(key));
    }

    private void bindPromptTextProperty(ComboBox<?> comboBox, String key) {
        comboBox.promptTextProperty().bind(observableResourceFactory.getStringBinding(key));
    }
}
