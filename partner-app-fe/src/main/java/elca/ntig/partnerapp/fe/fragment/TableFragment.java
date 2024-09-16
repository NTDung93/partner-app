package elca.ntig.partnerapp.fe.fragment;

import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import elca.ntig.partnerapp.fe.utils.BindingHelper;
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

    private BindingHelper bindingHelper;

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
        bindingHelper = new BindingHelper(observableResourceFactory);
        bindTextProperties();
    }

    private void bindTextProperties() {
        bindingHelper.bindLabelTextProperty(fragmentTitle, "TableFragment.lbl.fragmentTitle");
        bindingHelper.bindLabelTextProperty(exportLabel, "TableFragment.lbl.exportLabel");
        bindingHelper.bindColumnTextProperty(baseNumberColumn, "TableFragment.col.baseNumber");
        bindingHelper.bindColumnTextProperty(lastNameColumn, "TableFragment.col.lastName");
        bindingHelper.bindColumnTextProperty(firstNameColumn, "TableFragment.col.firstName");
        bindingHelper.bindColumnTextProperty(languageColumn, "TableFragment.col.language");
        bindingHelper.bindColumnTextProperty(genderColumn, "TableFragment.col.gender");
        bindingHelper.bindColumnTextProperty(nationalityColumn, "TableFragment.col.nationality");
        bindingHelper.bindColumnTextProperty(avsNumberColumn, "TableFragment.col.avsNumber");
        bindingHelper.bindColumnTextProperty(birthDateColumn, "TableFragment.col.birthDate");
        bindingHelper.bindColumnTextProperty(civilStatusColumn, "TableFragment.col.civilStatus");
        bindingHelper.bindColumnTextProperty(phoneNumberColumn, "TableFragment.col.phoneNumber");
        bindingHelper.bindColumnTextProperty(statusColumn, "TableFragment.col.status");
    }
}
