package elca.ntig.partnerapp.fe.fragment;

import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationResponseProto;
import elca.ntig.partnerapp.fe.common.cell.LocalizedTableCell;
import elca.ntig.partnerapp.fe.common.constant.PaginationConstant;
import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
import elca.ntig.partnerapp.fe.common.model.PersonTableModel;
import elca.ntig.partnerapp.fe.common.pagination.PaginationModel;
import elca.ntig.partnerapp.fe.component.ViewPartnerComponent;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import elca.ntig.partnerapp.fe.perspective.ViewPartnerPerspective;
import elca.ntig.partnerapp.fe.utils.BindingHelper;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.fragment.Scope;
import javafx.fxml.FXML;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.rcp.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Fragment(id = TableFragment.ID,
        viewLocation = ResourceConstant.TABLE_FRAGMENT_FXML,
        scope = Scope.PROTOTYPE)
public class TableFragment {
    public static final String ID = "TableFragment";
    private static Logger logger = Logger.getLogger(TableFragment.class);
    private BindingHelper bindingHelper;
    private ObservableList<PersonTableModel> data;
    private int pageNo = PaginationConstant.DEFAULT_PAGE_NO;
    private int pageSize = PaginationConstant.DEFAULT_PAGE_SIZE;
    private String sortBy = PaginationConstant.DEFAULT_SORT_BY;
    private String sortDir = PaginationConstant.DEFAULT_SORT_DIRECTION;
    private boolean isLastPage = false;

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Resource
    private Context context;

    @FXML
    private Label fragmentTitle;

//    @FXML
//    private Label exportLabel;

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

    @FXML
    private Button previousButton;

    @FXML
    private Button nextButton;

    @FXML
    private Label pageNumber;

    public void init() {
        bindingHelper = new BindingHelper(observableResourceFactory);
        bindTextProperties();
        initializeTable();
        initializePagination();
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

        pageNo = response.getPageNo();
        pageSize = response.getPageSize();
        isLastPage = response.getLast();

        Platform.runLater(() -> {
            partnersTable.setItems(data);
            pageNumber.setText(String.valueOf(pageNo + 1));
            if (isLastPage) {
                nextButton.setDisable(true);
            } else {
                nextButton.setDisable(false);
            }
            if (pageNo == 0) {
                previousButton.setDisable(true);
            } else {
                previousButton.setDisable(false);
            }
        });
    }

    private void bindTextProperties() {
        bindingHelper.bindLabelTextProperty(fragmentTitle, "TableFragment.lbl.fragmentTitle");
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

    private void initializeTable() {
        setTableDefaultMessage();
        setCellValueFactories();
        setCellFactories();
    }

    private void setTableDefaultMessage() {
        Label empty = new Label();
        bindingHelper.bindLabelTextProperty(empty, "TableFragment.defaultMessage");
        partnersTable.setPlaceholder(empty);
    }

    private void setCellValueFactories(){
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
    }

    private void setCellFactories(){
        languageColumn.setCellFactory(cell -> new LocalizedTableCell<>(observableResourceFactory, "Enum.language."));
        genderColumn.setCellFactory(cell -> new LocalizedTableCell<>(observableResourceFactory, "Enum.sex."));
        nationalityColumn.setCellFactory(cell -> new LocalizedTableCell<>(observableResourceFactory, "Enum.nationality."));
        civilStatusColumn.setCellFactory(cell -> new LocalizedTableCell<>(observableResourceFactory, "Enum.marital."));
        statusColumn.setCellFactory(cell -> new LocalizedTableCell<>(observableResourceFactory, "FormFragment.checkBox."));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        birthDateColumn.setCellFactory(new Callback<TableColumn<PersonTableModel, String>, TableCell<PersonTableModel, String>>() {
            @Override
            public TableCell<PersonTableModel, String> call(TableColumn<PersonTableModel, String> param) {
                return new TableCell<PersonTableModel, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            LocalDate date = LocalDate.parse(item);
                            setText(date.format(dateFormatter));
                        }
                    }
                };
            }
        });

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

    private void initializePagination() {
        previousButton.setText("<");
        nextButton.setText(">");
        pageNumber.setText(String.valueOf(pageNo + 1));
        previousButton.setDisable(true);
        nextButton.setDisable(true);

        nextButton.setOnAction(event -> {
            pageNo++;
            pageNumber.setText(String.valueOf(pageNo));
            logger.info("Current page: " + pageNo);
            context.send(ViewPartnerPerspective.ID.concat(".").concat(ViewPartnerComponent.ID), new PaginationModel(pageNo, pageSize, sortBy, sortDir));
        });

        previousButton.setOnAction(event -> {
            if (pageNo > 0) {
                pageNo--;
                pageNumber.setText(String.valueOf(pageNo));
                logger.info("Current page: " + pageNo);
                context.send(ViewPartnerPerspective.ID.concat(".").concat(ViewPartnerComponent.ID), new PaginationModel(pageNo, pageSize, sortBy, sortDir));
            }
        });
    }
}
