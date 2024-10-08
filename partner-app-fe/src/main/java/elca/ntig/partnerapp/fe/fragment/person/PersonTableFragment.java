package elca.ntig.partnerapp.fe.fragment.person;

import elca.ntig.partnerapp.common.proto.entity.person.GetPersonRequestProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationResponseProto;
import elca.ntig.partnerapp.common.proto.enums.common.PartnerTypeProto;
import elca.ntig.partnerapp.fe.callback.person.DeletePersonCallback;
import elca.ntig.partnerapp.fe.callback.person.GetPersonCallBack;
import elca.ntig.partnerapp.fe.common.cell.LocalizedTableCell;
import elca.ntig.partnerapp.fe.common.constant.ClassNameConstant;
import elca.ntig.partnerapp.fe.common.constant.PaginationConstant;
import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
import elca.ntig.partnerapp.fe.common.dialog.DialogBuilder;
import elca.ntig.partnerapp.fe.common.model.PersonTableModel;
import elca.ntig.partnerapp.fe.common.model.PaginationModel;
import elca.ntig.partnerapp.fe.component.ViewPartnerComponent;
import elca.ntig.partnerapp.fe.utils.ObservableResourceFactory;
import elca.ntig.partnerapp.fe.fragment.BaseTableFragment;
import elca.ntig.partnerapp.fe.fragment.common.CommonSetupTableFragment;
import elca.ntig.partnerapp.fe.perspective.ViewPartnerPerspective;
import elca.ntig.partnerapp.fe.utils.BindingHelper;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.fragment.Scope;
import javafx.fxml.FXML;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.rcp.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.log4j.Logger;

@Component
@Fragment(id = PersonTableFragment.ID,
        viewLocation = ResourceConstant.PERSON_TABLE_FRAGMENT_FXML,
        scope = Scope.PROTOTYPE)
public class PersonTableFragment extends CommonSetupTableFragment<PersonTableModel> implements BaseTableFragment {
    public static final String ID = "PersonTableFragment";
    private static Logger logger = Logger.getLogger(PersonTableFragment.class);
    private BindingHelper bindingHelper;
    private ObservableList<PersonTableModel> data;
    private int pageNo = PaginationConstant.DEFAULT_PAGE_NO;
    private int pageSize = PaginationConstant.DEFAULT_PAGE_SIZE;
    private String sortBy = PaginationConstant.DEFAULT_SORT_BY;
    private String sortDir = PaginationConstant.DEFAULT_SORT_DIRECTION;
    private boolean isLastPage = false;
    private boolean isUpdatingSort = false;

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Resource
    private Context context;

    @FXML
    private Label fragmentTitle;

    @FXML
    private ImageView exportExcelIcon;

    @FXML
    private Label exportExcelLabel;

    @FXML
    private TableView<PersonTableModel> partnersTable;

    @FXML
    private TableColumn<PersonTableModel, Integer> baseNumberColumn;

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

    @FXML
    private Text currentNumberOfRows;

    @FXML
    private Text totalRows;

    private StringProperty currentNumberOfRowsProperty = new SimpleStringProperty("0");
    private StringProperty totalRowsProperty = new SimpleStringProperty("0");

    @Override
    public void init() {
        bindingHelper = new BindingHelper(observableResourceFactory);
        bindTextProperties();
        initializeExportExcelIcon();
        initializeTable();
        setupDoubleClickEventHandler();
        initializePagination();
        setupSortListener();
        handleExportExcelButtonOnClick();
    }

    private void handleExportExcelButtonOnClick() {
        exportExcelIcon.setOnMouseClicked(event -> exportPersonDataToExcel(data, exportExcelIcon.getScene().getWindow(), observableResourceFactory));
        exportExcelLabel.setOnMouseClicked(event -> exportPersonDataToExcel(data, exportExcelLabel.getScene().getWindow(), observableResourceFactory));
    }

    private void initializeExportExcelIcon() {
        Image exportExcelImage = new Image(getClass().getResourceAsStream(ResourceConstant.EXPORT_EXCEL_ICON));
        {
            exportExcelIcon.setFitHeight(20);
            exportExcelIcon.setFitWidth(20);
        }
        exportExcelIcon.setImage(exportExcelImage);
    }

    @Override
    public void setupDoubleClickEventHandler() {
        partnersTable.setRowFactory(tr -> {
            TableRow<PersonTableModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    PersonTableModel rowData = row.getItem();
                    logger.info("Double click on person with id: " + rowData.getId());
                    GetPersonRequestProto request = GetPersonRequestProto.newBuilder().setId(rowData.getId()).build();
                    context.send(ViewPartnerPerspective.ID.concat(".").concat(GetPersonCallBack.ID), request);
                }
            });
            return row;
        });
    }

    @Override
    public void bindTextProperties() {
        currentNumberOfRows.textProperty().bind(currentNumberOfRowsProperty);
        totalRows.textProperty().bind(totalRowsProperty);

        bindingHelper.bindLabelTextProperty(exportExcelLabel, "TableFragment.lbl.exportLabel");
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

    @Override
    public void initializeTable() {
        setTableDefaultMessage();
        setCellValueFactories();
        setCellFactories();
    }

    @Override
    public void initializePagination() {
        previousButton.setText("<");
        nextButton.setText(">");
        pageNumber.setText(String.valueOf(pageNo + 1));
        previousButton.setDisable(true);
        nextButton.setDisable(true);

        nextButton.setOnAction(event -> {
            pageNo++;
//            logger.info("Current page: " + pageNo);
            context.send(ViewPartnerPerspective.ID.concat(".").concat(ViewPartnerComponent.ID), new PaginationModel(pageNo, pageSize, sortBy, sortDir, PartnerTypeProto.TYPE_PERSON));
        });

        previousButton.setOnAction(event -> {
            if (pageNo > 0) {
                pageNo--;
//                logger.info("Current page: " + pageNo);
                context.send(ViewPartnerPerspective.ID.concat(".").concat(ViewPartnerComponent.ID), new PaginationModel(pageNo, pageSize, sortBy, sortDir, PartnerTypeProto.TYPE_PERSON));
            }
        });
    }

    @Override
    public void setupSortListener() {
        partnersTable.setOnSort(event -> {
            if (isUpdatingSort) {
                return;
            }

            if (partnersTable.getSortOrder().isEmpty()) {
                return;
            }

            TableColumn<PersonTableModel, ?> sortedColumn = partnersTable.getSortOrder().get(0);
            TableColumn.SortType sortType = sortedColumn.getSortType();

            String sortDirection;
            if (sortType == TableColumn.SortType.ASCENDING) {
                sortDirection = PaginationConstant.SORT_DIRECTION_ASC;
            } else if (sortType == TableColumn.SortType.DESCENDING) {
                sortDirection = PaginationConstant.SORT_DIRECTION_DESC;
            } else {
                return;
            }

            String sortByField = getSortByField(sortedColumn);
            sortBy = sortByField;
            sortDir = sortDirection;
            logger.info("Sorting by: " + sortBy + ", Direction: " + sortDir);

            context.send(ViewPartnerPerspective.ID.concat(".").concat(ViewPartnerComponent.ID),
                    new PaginationModel(pageNo, pageSize, sortBy, sortDir, PartnerTypeProto.TYPE_PERSON));
        });
    }

    private String getSortByField(TableColumn<PersonTableModel, ?> column) {
        if (column == baseNumberColumn) {
            return "id";
        } else if (column == lastNameColumn) {
            return "lastName";
        } else if (column == firstNameColumn) {
            return "firstName";
        } else if (column == languageColumn) {
            return "language";
        } else if (column == genderColumn) {
            return "gender";
        } else if (column == nationalityColumn) {
            return "nationality";
        } else if (column == avsNumberColumn) {
            return "avsNumber";
        } else if (column == birthDateColumn) {
            return "birthDate";
        } else if (column == civilStatusColumn) {
            return "civilStatus";
        } else if (column == phoneNumberColumn) {
            return "phoneNumber";
        } else if (column == statusColumn) {
            return "status";
        }
        return "";
    }

    private TableColumn<PersonTableModel, ?> getSortByColumn(String sortByField) {
        switch (sortByField) {
            case "id":
                return baseNumberColumn;
            case "lastName":
                return lastNameColumn;
            case "firstName":
                return firstNameColumn;
            case "language":
                return languageColumn;
            case "gender":
                return genderColumn;
            case "nationality":
                return nationalityColumn;
            case "avsNumber":
                return avsNumberColumn;
            case "birthDate":
                return birthDateColumn;
            case "civilStatus":
                return civilStatusColumn;
            case "phoneNumber":
                return phoneNumberColumn;
            case "status":
                return statusColumn;
            default:
                return null;
        }
    }

    public void updateTable(SearchPeoplePaginationResponseProto response) {
        data = FXCollections.observableArrayList();

        response.getContentList().forEach(person -> {
            PersonTableModel model = new PersonTableModel(
                    person.getId(),
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
            currentNumberOfRowsProperty.set(String.valueOf(data.size() + (pageNo * pageSize)));
            totalRowsProperty.set(String.valueOf(response.getTotalRecords()));

            if (!sortBy.isEmpty()) {
                TableColumn<PersonTableModel, ?> sortColumn = getSortByColumn(sortBy);
                if (sortColumn != null) {
                    isUpdatingSort = true; // Prevent the listener from reacting to this change
                    partnersTable.getSortOrder().clear();
                    partnersTable.getSortOrder().add(sortColumn);
                    sortColumn.setSortType(sortDir.equals(PaginationConstant.SORT_DIRECTION_ASC) ?
                            TableColumn.SortType.ASCENDING : TableColumn.SortType.DESCENDING);
                    isUpdatingSort = false;
                }
            }

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

    private void setTableDefaultMessage() {
        setTableDefaultMessage(bindingHelper, partnersTable);
    }

    private void setCellValueFactories() {
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

    private void setCellFactories() {
        languageColumn.setCellFactory(cell -> new LocalizedTableCell<>(observableResourceFactory, "Enum.language."));
        genderColumn.setCellFactory(cell -> new LocalizedTableCell<>(observableResourceFactory, "Enum.sex."));
        nationalityColumn.setCellFactory(cell -> new LocalizedTableCell<>(observableResourceFactory, "Enum.nationality."));
        civilStatusColumn.setCellFactory(cell -> new LocalizedTableCell<>(observableResourceFactory, "Enum.marital."));
        statusColumn.setCellFactory(cell -> new LocalizedTableCell<>(observableResourceFactory, "FormFragment.checkBox."));
        setCellFactoryDateColumn(birthDateColumn);
        setCellFactoryAvsNumberColumn(avsNumberColumn);
        deleteIconColumn.setCellFactory(cell -> new TableCell<PersonTableModel, Void>() {
            private final ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream(ResourceConstant.BIN_ICON)));

            {
                deleteIcon.setFitHeight(20);
                deleteIcon.setFitWidth(20);
            }

            Button deleteButton = new Button();

            {
                deleteButton.getStyleClass().add(ClassNameConstant.DELETE_BUTTON);
                deleteButton.setGraphic(deleteIcon);
                deleteButton.setOnAction(event -> {
                    PersonTableModel person = getTableView().getItems().get(getIndex());
                    handleDeleteButtonOnClick(person.getId());
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

    public void handleDeleteButtonOnClick(Integer id) {
        DialogBuilder dialogBuilder = new DialogBuilder(observableResourceFactory);
        Alert alert = dialogBuilder.buildAlert(Alert.AlertType.CONFIRMATION, "Dialog.confirmation.title",
                "Dialog.confirmation.message.deletePartner");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            logger.info("Delete person with id: " + id);
            GetPersonRequestProto request = GetPersonRequestProto.newBuilder().setId(id).build();
            context.send(ViewPartnerPerspective.ID.concat(".").concat(DeletePersonCallback.ID), request);
        }
    }

    public void resetSortPolicy() {
        sortBy = PaginationConstant.DEFAULT_SORT_BY;
        sortDir = PaginationConstant.DEFAULT_SORT_DIRECTION;
    }
}
