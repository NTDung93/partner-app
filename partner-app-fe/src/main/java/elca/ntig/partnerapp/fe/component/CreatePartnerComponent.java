package elca.ntig.partnerapp.fe.component;

import elca.ntig.partnerapp.common.proto.enums.common.PartnerTypeProto;
import elca.ntig.partnerapp.fe.common.constant.PaginationConstant;
import elca.ntig.partnerapp.fe.common.constant.TargetConstant;
import elca.ntig.partnerapp.fe.common.model.PaginationModel;
import elca.ntig.partnerapp.fe.fragment.organisation.CreateOrganisationFormFragment;
import elca.ntig.partnerapp.fe.fragment.person.CreatePersonFormFragment;
import elca.ntig.partnerapp.fe.utils.ObservableResourceFactory;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Node;
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

@View(id = CreatePartnerComponent.ID,
        name = CreatePartnerComponent.ID,
        resourceBundleLocation = ObservableResourceFactory.RESOURCE_BUNDLE_NAME,
        initialTargetLayoutId = TargetConstant.TARGET_CREATE_PARTNER_CONTAINER)
public class CreatePartnerComponent implements FXComponent {
    public static final String ID = "CreatePartnerComponent";
    private static Logger logger = Logger.getLogger(CreatePartnerComponent.class);

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
}
