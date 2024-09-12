package elca.ntig.partnerapp.fe.fragment;

import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import javafx.scene.control.TableColumn;
import org.jacpfx.api.fragment.Scope;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Fragment(id = TableFragment.ID,
        viewLocation = ResourceConstant.TABLE_FRAGMENT_FXML,
        scope = Scope.PROTOTYPE)
public class TableFragment {
    public static final String ID = "TableFragment";

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @FXML
    private Label fragmentTitle;

    @FXML
    private Label exportLabel;

    @FXML
    private TableColumn<?, String> baseNumberColumn;

    @FXML
    private TableColumn<?, String> lastNameColumn;

    @FXML
    private TableColumn<?, String> firstNameColumn;

    @FXML
    private TableColumn<?, String> languageColumn;

    @FXML
    private TableColumn<?, String> genderColumn;

    @FXML
    private TableColumn<?, String> nationalityColumn;

    @FXML
    private TableColumn<?, String> avsNumberColumn;

    @FXML
    private TableColumn<?, String> birthDateColumn;

    @FXML
    private TableColumn<?, String> civilStatusColumn;

    @FXML
    private TableColumn<?, ?> phoneNumberColumn;

    @FXML
    private TableColumn<?, ?> statusColumn;

    @FXML
    private TableColumn<?, ?> deleteIconColumn;

    public void init() {
        fragmentTitle.textProperty()
                .bind(observableResourceFactory.getStringBinding("TableFragment.lbl.fragmentTitle"));
        exportLabel.textProperty()
                .bind(observableResourceFactory.getStringBinding("TableFragment.lbl.exportLabel"));
        baseNumberColumn.textProperty()
                .bind(observableResourceFactory.getStringBinding("TableFragment.col.baseNumber"));
        lastNameColumn.textProperty()
                .bind(observableResourceFactory.getStringBinding("TableFragment.col.lastName"));
        firstNameColumn.textProperty()
                .bind(observableResourceFactory.getStringBinding("TableFragment.col.firstName"));
        languageColumn.textProperty()
                .bind(observableResourceFactory.getStringBinding("TableFragment.col.language"));
        genderColumn.textProperty()
                .bind(observableResourceFactory.getStringBinding("TableFragment.col.gender"));
        nationalityColumn.textProperty()
                .bind(observableResourceFactory.getStringBinding("TableFragment.col.nationality"));
        avsNumberColumn.textProperty()
                .bind(observableResourceFactory.getStringBinding("TableFragment.col.avsNumber"));
        birthDateColumn.textProperty()
                .bind(observableResourceFactory.getStringBinding("TableFragment.col.birthDate"));
        civilStatusColumn.textProperty()
                .bind(observableResourceFactory.getStringBinding("TableFragment.col.civilStatus"));
        phoneNumberColumn.textProperty()
                .bind(observableResourceFactory.getStringBinding("TableFragment.col.phoneNumber"));
        statusColumn.textProperty()
                .bind(observableResourceFactory.getStringBinding("TableFragment.col.status"));
    }
}
