package elca.ntig.partnerapp.fe.fragment;

import elca.ntig.partnerapp.common.proto.enums.partner.LanguageProto;
import elca.ntig.partnerapp.common.proto.enums.person.NationalityProto;
import elca.ntig.partnerapp.common.proto.enums.person.SexEnumProto;
import elca.ntig.partnerapp.fe.common.cell.LanguageCell;
import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import elca.ntig.partnerapp.fe.utils.BindingHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import lombok.RequiredArgsConstructor;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ResourceBundle;

@Component
@Fragment(id = FormFragment.ID,
        viewLocation = ResourceConstant.FORM_FRAGMENT_FXML,
        scope = Scope.PROTOTYPE)
public class FormFragment {
    public static final String ID = "FormFragment";

    @Autowired
    private ObservableResourceFactory observableResourceFactory;
    public final static String RESOURCE_BUNDLE_NAME = "bundles/languageBundle";

//    @Autowired
//    private BindingHelper bindingHelper;

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
        // Bind text properties to resource bundle
        fragmentTitle.textProperty()
                .bind(observableResourceFactory.getStringBinding("FormFragment.lbl.fragmentTitle"));
//        fragmentTitle.textProperty()
//                .bind(bindingHelper.textBinding("FormFragment.lbl.fragmentTitle"));
        createPersonButton.textProperty()
                .bind(observableResourceFactory.getStringBinding("FormFragment.btn.createPerson"));
        typeLabel.textProperty()
                .bind(observableResourceFactory.getStringBinding("FormFragment.lbl.type"));
        lastNameLabel.textProperty()
                .bind(observableResourceFactory.getStringBinding("FormFragment.lbl.lastName"));
        firstNameLabel.textProperty()
                .bind(observableResourceFactory.getStringBinding("FormFragment.lbl.firstName"));
        avsNumberLabel.textProperty()
                .bind(observableResourceFactory.getStringBinding("FormFragment.lbl.avsNumber"));
        statusLabel.textProperty()
                .bind(observableResourceFactory.getStringBinding("FormFragment.lbl.status"));
        correspondenceLanguageLabel.textProperty()
                .bind(observableResourceFactory.getStringBinding("FormFragment.lbl.correspondenceLanguage"));
        sexLabel.textProperty()
                .bind(observableResourceFactory.getStringBinding("FormFragment.lbl.sex"));
        nationalityLabel.textProperty()
                .bind(observableResourceFactory.getStringBinding("FormFragment.lbl.nationality"));
        birthDateLabel.textProperty()
                .bind(observableResourceFactory.getStringBinding("FormFragment.lbl.birthDate"));
        removeCriteriasButton.textProperty()
                .bind(observableResourceFactory.getStringBinding("FormFragment.btn.removeCriterias"));
        searchButton.textProperty()
                .bind(observableResourceFactory.getStringBinding("FormFragment.btn.search"));
        languageComboBox.promptTextProperty()
                .bind(observableResourceFactory.getStringBinding("FormFragment.comboBox.placeholder"));
        sexComboBox.promptTextProperty()
                .bind(observableResourceFactory.getStringBinding("FormFragment.comboBox.placeholder"));
        nationalityComboBox.promptTextProperty()
                .bind(observableResourceFactory.getStringBinding("FormFragment.comboBox.placeholder"));

        languageComboBox.getItems().addAll(LanguageProto.values());
        languageComboBox.getItems().remove(LanguageProto.NULL_LANGUAGE);
        languageComboBox.getItems().remove(LanguageProto.UNRECOGNIZED);
        languageComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                languageComboBox.setValue(LanguageProto.NULL_LANGUAGE);
            }
        });
        languageComboBox.setConverter(new StringConverter<LanguageProto>() {
            @Override
            public String toString(LanguageProto language) {
                String value = language.getValueDescriptor().toString().substring(5).toLowerCase();
                return value.substring(0, 1).toUpperCase() + value.substring(1);
            }

            @Override
            public LanguageProto fromString(String string) {
                return LanguageProto.valueOf("LANGUAGE_" + string.toUpperCase());
            }
        });
//        languageComboBox.setCellFactory(param -> new LanguageCell());
//        languageComboBox.setButtonCell(new LanguageCell());
    }
}
