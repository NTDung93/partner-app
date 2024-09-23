package elca.ntig.partnerapp.fe.component;

import elca.ntig.partnerapp.fe.common.constant.MessageConstant;
import elca.ntig.partnerapp.fe.common.constant.TargetConstant;
import elca.ntig.partnerapp.fe.fragment.organisation.CreateOrganisationFormFragment;
import elca.ntig.partnerapp.fe.fragment.person.CreatePersonFormFragment;
import elca.ntig.partnerapp.fe.utils.ObservableResourceFactory;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.View;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.FXComponent;
import org.jacpfx.rcp.components.managedFragment.ManagedFragmentHandler;
import org.jacpfx.rcp.context.Context;
import org.springframework.stereotype.Component;

@Component
@View(id = CreatePartnerComponent.ID,
        name = CreatePartnerComponent.ID,
        resourceBundleLocation = ObservableResourceFactory.RESOURCE_BUNDLE_NAME,
        initialTargetLayoutId = TargetConstant.TARGET_CREATE_PARTNER_CONTAINER)
public class CreatePartnerComponent implements FXComponent {
    public static final String ID = "CreatePartnerComponent";

    @Resource
    private Context context;
    private Node root;
    private VBox container = new VBox();

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
            switchTypeToOrganisation();
        } else if (message.getMessageBody().equals(MessageConstant.SWITCH_TYPE_TO_PERSON)) {
            switchTypeToPerson();
        }
        return null;
    }

    @PostConstruct
    public void onPostConstructComponent() {
        container.setVgrow(container, Priority.ALWAYS);
        container.setStyle("-fx-background-color: white;");
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
