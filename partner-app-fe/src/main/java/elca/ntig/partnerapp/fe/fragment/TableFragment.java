package elca.ntig.partnerapp.fe.fragment;

import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationResponseProto;
import elca.ntig.partnerapp.fe.common.cell.LocalizedTableCell;
import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
import elca.ntig.partnerapp.fe.common.model.PersonTableModel;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import elca.ntig.partnerapp.fe.utils.BindingHelper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jacpfx.api.fragment.Scope;
import javafx.fxml.FXML;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.log4j.Logger;

@Component
@Fragment(id = TableFragment.ID,
        viewLocation = ResourceConstant.TABLE_FRAGMENT_FXML,
        scope = Scope.PROTOTYPE)
public class TableFragment {
    public static final String ID = "TableFragment";

    private static Logger logger = Logger.getLogger(TableFragment.class);

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    private BindingHelper bindingHelper;

    @FXML
    private Label fragmentTitle;

    @FXML
    private Label exportLabel;

    @FXML
    private TableView<PersonTableModel> partnersTable;

    @FXML
    private Pagination pagination;

    @FXML
    private TableColumn<PersonTableModel, String> baseNumberColumn;

    @FXML
    private TableColumn<PersonTableModel, String> lastNameColumn;

    @FXML
    private TableColumn<PersonTableModel, String> firstNameColumn;

    @FXML
    private TableColumn<PersonTableModel, String> languageColumn;

    @FXML
    private TableColumn<PersonTableModel, String> genderColumn;

    @FXML
    private TableColumn<PersonTableModel, String> nationalityColumn;

    @FXML
    private TableColumn<PersonTableModel, String> avsNumberColumn;

    @FXML
    private TableColumn<PersonTableModel, String> birthDateColumn;

    @FXML
    private TableColumn<PersonTableModel, String> civilStatusColumn;

    @FXML
    private TableColumn<PersonTableModel, String> phoneNumberColumn;

    @FXML
    private TableColumn<PersonTableModel, String> statusColumn;

    @FXML
    private TableColumn<PersonTableModel, Void> deleteIconColumn;

    private ObservableList<PersonTableModel> data;

    public void init() {
        bindingHelper = new BindingHelper(observableResourceFactory);
        bindTextProperties();
        initializeTable();
    }

    private void initializeTable() {
        baseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
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

        languageColumn.setCellFactory(cell -> new LocalizedTableCell<>(observableResourceFactory, "Enum.language."));
        genderColumn.setCellFactory(cell -> new LocalizedTableCell<>(observableResourceFactory, "Enum.sex."));
        nationalityColumn.setCellFactory(cell -> new LocalizedTableCell<>(observableResourceFactory, "Enum.nationality."));
        civilStatusColumn.setCellFactory(cell -> new LocalizedTableCell<>(observableResourceFactory, "Enum.marital."));
        statusColumn.setCellFactory(cell -> new LocalizedTableCell<>(observableResourceFactory, "FormFragment.checkBox."));

        deleteIconColumn.setCellFactory(cell -> new TableCell<PersonTableModel, Void>() {
            private final ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream(ResourceConstant.BIN_ICON)));
            {
                deleteIcon.setFitHeight(20);
                deleteIcon.setFitWidth(20);
            }

            Button deleteButton = new Button();
            {
                deleteButton.setStyle("-fx-background-color: transparent; -fx-padding: 0; -fx-border-width: 0;");
                deleteButton.setGraphic(deleteIcon);
                deleteButton.setOnAction(event -> {
                    PersonTableModel person = getTableView().getItems().get(getIndex());
                    logger.info("Delete person with id: " + person.getId());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    PersonTableModel person = getTableView().getItems().get(getIndex());
                    if (person.getStatus().equals("ACTIVE")) {
                        setGraphic(deleteButton);
                    } else {
                        setGraphic(null);
                    }
                }
            }
        });
    }

    public void updateTable(SearchPeoplePaginationResponseProto response) {
        data = FXCollections.observableArrayList();

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
