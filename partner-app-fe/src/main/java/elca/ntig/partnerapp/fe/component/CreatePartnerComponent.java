package elca.ntig.partnerapp.fe.component;

import elca.ntig.partnerapp.common.proto.entity.organisation.OrganisationResponseProto;
import elca.ntig.partnerapp.common.proto.entity.person.GetPersonRequestProto;
import elca.ntig.partnerapp.common.proto.entity.person.PersonResponseProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationResponseProto;
import elca.ntig.partnerapp.common.proto.enums.common.PartnerTypeProto;
import elca.ntig.partnerapp.fe.callback.person.DeletePersonCallback;
import elca.ntig.partnerapp.fe.common.constant.MessageConstant;
import elca.ntig.partnerapp.fe.common.constant.PaginationConstant;
import elca.ntig.partnerapp.fe.common.constant.TargetConstant;
import elca.ntig.partnerapp.fe.common.dialog.DialogBuilder;
import elca.ntig.partnerapp.fe.common.model.PaginationModel;
import elca.ntig.partnerapp.fe.fragment.organisation.CreateOrganisationFormFragment;
import elca.ntig.partnerapp.fe.fragment.person.CreatePersonFormFragment;
import elca.ntig.partnerapp.fe.perspective.ViewPartnerPerspective;
import elca.ntig.partnerapp.fe.utils.ObservableResourceFactory;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.apache.log4j.Logger;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.View;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.FXComponent;
import org.jacpfx.rcp.components.managedFragment.ManagedFragmentHandler;
import org.jacpfx.rcp.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@View(id = CreatePartnerComponent.ID,
        name = CreatePartnerComponent.ID,
        resourceBundleLocation = ObservableResourceFactory.RESOURCE_BUNDLE_NAME,
        initialTargetLayoutId = TargetConstant.TARGET_CREATE_PARTNER_CONTAINER)
public class CreatePartnerComponent implements FXComponent {
    public static final String ID = "CreatePartnerComponent";
    private static Logger logger = Logger.getLogger(CreatePartnerComponent.class);

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Resource
    private Context context;
    private Node root;

    private VBox container = new VBox();

    PaginationModel paginationModel;

    private ManagedFragmentHandler<CreatePersonFormFragment> createPersonFormHandler;
    private ManagedFragmentHandler<CreateOrganisationFormFragment> createOrganisationFormHandler;

    private CreatePersonFormFragment createPersonFormController;
    private CreateOrganisationFormFragment createOrganisationFormController;

    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        return this.root;
    }

    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        if (message.getMessageBody().equals(MessageConstant.SWITCH_TYPE_TO_ORGANISATION)) {
            paginationModel.setPartnerType(PartnerTypeProto.TYPE_ORGANISATION);
            switchTypeToOrganisation();
        }
        if (message.getMessageBody().equals(MessageConstant.SWITCH_TYPE_TO_PERSON)) {
            paginationModel.setPartnerType(PartnerTypeProto.TYPE_PERSON);
            switchTypeToPerson();
        }
        if (message.isMessageBodyTypeOf(PersonResponseProto.class)) {
            PersonResponseProto response = (PersonResponseProto) message.getMessageBody();
            if (response != null) {
                Platform.runLater(() -> {
                    DialogBuilder dialogBuilder = new DialogBuilder(observableResourceFactory);
                    Alert alert = dialogBuilder.buildAlert(Alert.AlertType.INFORMATION, "Dialog.information.title",
                            "Dialog.information.header.createPartner.success", "");
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.OK) {
                        context.send(ViewPartnerPerspective.ID, MessageConstant.REFRESH_PERSON_TABLE);
                    }
                });
            }
        }
        if (message.isMessageBodyTypeOf(OrganisationResponseProto.class)) {
            OrganisationResponseProto response = (OrganisationResponseProto) message.getMessageBody();
            if (response != null) {
                DialogBuilder dialogBuilder = new DialogBuilder(observableResourceFactory);
                Alert alert = dialogBuilder.buildAlert(Alert.AlertType.INFORMATION, "Dialog.information.title",
                        "Dialog.information.header.createPartner.success", "");
                Platform.runLater(() -> {
                    alert.showAndWait();
                });
                if (alert.getResult() == ButtonType.OK) {
                    context.send(ViewPartnerPerspective.ID, MessageConstant.REFRESH_ORGANISATION_TABLE);
                }
            }
        }
        return null;
    }

    @PostConstruct
    public void onPostConstructComponent() {
        container.setVgrow(container, Priority.ALWAYS);
        container.setStyle("-fx-background-color: white;");
        paginationModel = PaginationModel.builder()
                .pageNo(PaginationConstant.DEFAULT_PAGE_NO)
                .pageSize(PaginationConstant.DEFAULT_PAGE_SIZE)
                .sortBy(PaginationConstant.DEFAULT_SORT_BY)
                .sortDir(PaginationConstant.DEFAULT_SORT_DIRECTION)
                .partnerType(PartnerTypeProto.TYPE_PERSON)
                .build();
        this.root = initFragment();
    }

    private Node initFragment() {
        switchTypeToPerson();
        return container;
    }

    private void switchTypeToPerson() {
        createPersonFormHandler = context.getManagedFragmentHandler(CreatePersonFormFragment.class);
        createPersonFormController = createPersonFormHandler.getController();
        createPersonFormController.init();
        Platform.runLater(() -> {
            container.getChildren().setAll(createPersonFormHandler.getFragmentNode());
        });
    }

    private void switchTypeToOrganisation() {
        createOrganisationFormHandler = context.getManagedFragmentHandler(CreateOrganisationFormFragment.class);
        createOrganisationFormController = createOrganisationFormHandler.getController();
        createOrganisationFormController.init();
        Platform.runLater(() -> {
            container.getChildren().setAll(createOrganisationFormHandler.getFragmentNode());
        });
    }
}
