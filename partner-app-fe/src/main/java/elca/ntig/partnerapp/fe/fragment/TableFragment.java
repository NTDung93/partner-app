package elca.ntig.partnerapp.fe.fragment;

import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationResponseProto;
import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
import elca.ntig.partnerapp.fe.common.model.PersonTableModel;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import elca.ntig.partnerapp.fe.utils.BindingHelper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

    private int rowsPerPage = 10;

    @FXML
    private Label fragmentTitle;

    @FXML
    private Label exportLabel;

    @FXML
    private TableView<PersonTableModel> partnersTable;

    @FXML
    private Pagination pagination;

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
        initializeTable();
    }

    public void updateTable(SearchPeoplePaginationResponseProto response) {
        ObservableList<PersonTableModel> data = FXCollections.observableArrayList();

        response.getContentList().forEach(person -> {
            PersonTableModel model = new PersonTableModel(
                    String.valueOf(person.getId()),
                    person.getLastName(),
                    person.getFirstName(),
                    person.getLanguage().name(),
                    person.getSex().name(),
                    person.getNationality().name(),
                    person.getAvsNumber(),
                    person.getBirthDate(),
                    person.getMaritalStatus().name(),
                    person.getPhoneNumber(),
                    person.getStatus().name()
            );
            data.add(model);
        });

        Platform.runLater(() -> {
            partnersTable.setItems(data);
        });

//        int totalItems = (int) response.getTotalRecords();
//        pagination.setPageCount((totalItems + rowsPerPage - 1) / rowsPerPage);
//
//        pagination.setPageFactory(pageIndex -> {
//            int fromIndex = pageIndex * rowsPerPage;
//            int toIndex = Math.min(fromIndex + rowsPerPage, data.size());
//            partnersTable.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));
//            return partnersTable;
//        });
    }

    private void initializeTable() {
        baseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("baseNumber"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        languageColumn.setCellValueFactory(new PropertyValueFactory<>("language"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        nationalityColumn.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        avsNumberColumn.setCellValueFactory(new PropertyValueFactory<>("avsNumber"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        civilStatusColumn.setCellValueFactory(new PropertyValueFactory<>("civilStatus"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
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
