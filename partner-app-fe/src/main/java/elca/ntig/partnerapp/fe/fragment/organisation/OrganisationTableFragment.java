package elca.ntig.partnerapp.fe.fragment.organisation;

import elca.ntig.partnerapp.common.proto.entity.organisation.GetOrganisationRequestProto;
import elca.ntig.partnerapp.common.proto.entity.organisation.SearchOrganisationPaginationResponseProto;
import elca.ntig.partnerapp.common.proto.enums.common.PartnerTypeProto;
import elca.ntig.partnerapp.fe.callback.organisation.DeleteOrganisationCallback;
import elca.ntig.partnerapp.fe.callback.organisation.GetOrganisationCallBack;
import elca.ntig.partnerapp.fe.common.cell.LocalizedTableCell;
import elca.ntig.partnerapp.fe.common.constant.ClassNameConstant;
import elca.ntig.partnerapp.fe.common.constant.PaginationConstant;
import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
import elca.ntig.partnerapp.fe.common.dialog.DialogBuilder;
import elca.ntig.partnerapp.fe.common.model.OrganisationTableModel;
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
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.apache.log4j.Logger;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.jacpfx.rcp.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Fragment(id = OrganisationTableFragment.ID,
        viewLocation = ResourceConstant.ORGANISATION_TABLE_FRAGMENT_FXML,
        scope = Scope.PROTOTYPE)
public class OrganisationTableFragment extends CommonSetupTableFragment<OrganisationTableModel> implements BaseTableFragment {
    public static final String ID = "OrganisationTableFragment";
    private static Logger logger = Logger.getLogger(OrganisationTableFragment.class);
    private BindingHelper bindingHelper;
    private ObservableList<OrganisationTableModel> data;
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

//    @FXML
//    private Label exportLabel;

    @FXML
    private TableView<OrganisationTableModel> partnersTable;

    @FXML
    private TableColumn<OrganisationTableModel, Integer> baseNumberColumn;

    @FXML
    private TableColumn<OrganisationTableModel, String> nameColumn;

    @FXML
    private TableColumn<OrganisationTableModel, String> additionalNameColumn;

    @FXML
    private TableColumn<OrganisationTableModel, String> languageColumn;

    @FXML
    private TableColumn<OrganisationTableModel, String> legalStatusColumn;

    @FXML
    private TableColumn<OrganisationTableModel, String> ideNumberColumn;

    @FXML
    private TableColumn<OrganisationTableModel, String> creationDateColumn;

    @FXML
    private TableColumn<OrganisationTableModel, String> codeNOGAColumn;

    @FXML
    private TableColumn<OrganisationTableModel, String> phoneNumberColumn;

    @FXML
    private TableColumn<OrganisationTableModel, String> statusColumn;

    @FXML
    private TableColumn<OrganisationTableModel, Void> deleteIconColumn;

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
        initializeTable();
        setupDoubleClickEventHandler();
        initializePagination();
        setupSortListener();
    }

    @Override
    public void setupDoubleClickEventHandler() {
        partnersTable.setRowFactory(tr -> {
            TableRow<OrganisationTableModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    OrganisationTableModel rowData = row.getItem();
                    logger.info("Double click on organisation with id: " + rowData.getId());
                    GetOrganisationRequestProto request = GetOrganisationRequestProto.newBuilder().setId(rowData.getId()).build();
                    context.send(ViewPartnerPerspective.ID.concat(".").concat(GetOrganisationCallBack.ID), request);
                }
            });
            return row;
        });
    }

    @Override
    public void bindTextProperties() {
        currentNumberOfRows.textProperty().bind(currentNumberOfRowsProperty);
        totalRows.textProperty().bind(totalRowsProperty);

        bindingHelper.bindLabelTextProperty(fragmentTitle, "TableFragment.lbl.fragmentTitle");
        bindingHelper.bindColumnTextProperty(baseNumberColumn, "TableFragment.col.baseNumber");
        bindingHelper.bindColumnTextProperty(nameColumn, "TableFragment.col.name");
        bindingHelper.bindColumnTextProperty(additionalNameColumn, "TableFragment.col.additionalName");
        bindingHelper.bindColumnTextProperty(languageColumn, "TableFragment.col.language");
        bindingHelper.bindColumnTextProperty(legalStatusColumn, "TableFragment.col.legalStatus");
        bindingHelper.bindColumnTextProperty(ideNumberColumn, "TableFragment.col.ideNumber");
        bindingHelper.bindColumnTextProperty(creationDateColumn, "TableFragment.col.creationDate");
        bindingHelper.bindColumnTextProperty(codeNOGAColumn, "TableFragment.col.codeNOGA");
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
            context.send(ViewPartnerPerspective.ID.concat(".").concat(ViewPartnerComponent.ID), new PaginationModel(pageNo, pageSize, sortBy, sortDir, PartnerTypeProto.TYPE_ORGANISATION));
        });

        previousButton.setOnAction(event -> {
            if (pageNo > 0) {
                pageNo--;
//                logger.info("Current page: " + pageNo);
                context.send(ViewPartnerPerspective.ID.concat(".").concat(ViewPartnerComponent.ID), new PaginationModel(pageNo, pageSize, sortBy, sortDir, PartnerTypeProto.TYPE_ORGANISATION));
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

            TableColumn<OrganisationTableModel, ?> sortedColumn = partnersTable.getSortOrder().get(0);
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
                    new PaginationModel(pageNo, pageSize, sortBy, sortDir, PartnerTypeProto.TYPE_ORGANISATION));
        });
    }

    private String getSortByField(TableColumn<OrganisationTableModel, ?> column) {
        if (column == baseNumberColumn) {
            return "id";
        } else if (column == nameColumn) {
            return "name";
        } else if (column == additionalNameColumn) {
            return "additionalName";
        } else if (column == languageColumn) {
            return "language";
        } else if (column == legalStatusColumn) {
            return "legalStatus";
        } else if (column == ideNumberColumn) {
            return "ideNumber";
        } else if (column == creationDateColumn) {
            return "creationDate";
        } else if (column == codeNOGAColumn) {
            return "codeNoga";
        } else if (column == phoneNumberColumn) {
            return "phoneNumber";
        } else if (column == statusColumn) {
            return "status";
        }
        return "";
    }

    private TableColumn<OrganisationTableModel, ?> getSortByColumn(String sortByField) {
        switch (sortByField) {
            case "id":
                return baseNumberColumn;
            case "name":
                return nameColumn;
            case "additionalName":
                return additionalNameColumn;
            case "language":
                return languageColumn;
            case "legalStatus":
                return legalStatusColumn;
            case "ideNumber":
                return ideNumberColumn;
            case "creationDate":
                return creationDateColumn;
            case "codeNoga":
                return codeNOGAColumn;
            case "phoneNumber":
                return phoneNumberColumn;
            case "status":
                return statusColumn;
            default:
                return null;
        }
    }

    public void updateTable(SearchOrganisationPaginationResponseProto response) {
        data = FXCollections.observableArrayList();

        response.getContentList().forEach(person -> {
            OrganisationTableModel model = new OrganisationTableModel(
                    person.getId(),
                    person.getName(),
                    person.getAdditionalName(),
                    person.getLanguage().name(),
                    person.getLegalStatus().name(),
                    person.getIdeNumber(),
                    person.getCreationDate(),
                    person.getCodeNoga().name(),
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
                TableColumn<OrganisationTableModel, ?> sortColumn = getSortByColumn(sortBy);
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
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        additionalNameColumn.setCellValueFactory(new PropertyValueFactory<>("additionalName"));
        languageColumn.setCellValueFactory(new PropertyValueFactory<>("language"));
        legalStatusColumn.setCellValueFactory(new PropertyValueFactory<>("legalStatus"));
        ideNumberColumn.setCellValueFactory(new PropertyValueFactory<>("ideNumber"));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        codeNOGAColumn.setCellValueFactory(new PropertyValueFactory<>("codeNoga"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void setCellFactories() {
        languageColumn.setCellFactory(cell -> new LocalizedTableCell<>(observableResourceFactory, "Enum.language."));
        legalStatusColumn.setCellFactory(cell -> new LocalizedTableCell<>(observableResourceFactory, "Enum.legalStatus."));
        statusColumn.setCellFactory(cell -> new LocalizedTableCell<>(observableResourceFactory, "FormFragment.checkBox."));
        setCellFactoryDateColumn(creationDateColumn);
        setCellFactoryCodeNOGANumberColumn(codeNOGAColumn);
        deleteIconColumn.setCellFactory(cell -> new TableCell<OrganisationTableModel, Void>() {
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
                    OrganisationTableModel organisation = getTableView().getItems().get(getIndex());
                    handleDeleteButtonOnClick(organisation.getId());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    OrganisationTableModel person = getTableView().getItems().get(getIndex());
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
            logger.info("Delete organisation with id: " + id);
            GetOrganisationRequestProto request = GetOrganisationRequestProto.newBuilder().setId(id).build();
            context.send(ViewPartnerPerspective.ID.concat(".").concat(DeleteOrganisationCallback.ID), request);
        }
    }

    public void resetSortPolicy() {
        sortBy = PaginationConstant.DEFAULT_SORT_BY;
        sortDir = PaginationConstant.DEFAULT_SORT_DIRECTION;
    }
}
