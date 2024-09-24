package elca.ntig.partnerapp.fe.component;

import elca.ntig.partnerapp.common.proto.entity.organisation.OrganisationResponseProto;
import elca.ntig.partnerapp.common.proto.entity.person.PersonResponseProto;
import elca.ntig.partnerapp.fe.common.constant.ClassNameConstant;
import elca.ntig.partnerapp.fe.common.constant.MessageConstant;
import elca.ntig.partnerapp.fe.common.constant.TargetConstant;
import elca.ntig.partnerapp.fe.fragment.organisation.UpdateOrganisationFormFragment;
import elca.ntig.partnerapp.fe.fragment.person.UpdatePersonFormFragment;
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
@View(id = UpdatePartnerComponent.ID,
        name = UpdatePartnerComponent.ID,
        resourceBundleLocation = ObservableResourceFactory.RESOURCE_BUNDLE_NAME,
        initialTargetLayoutId = TargetConstant.TARGET_UPDATE_PARTNER_CONTAINER)
public class UpdatePartnerComponent implements FXComponent {
    public static final String ID = "UpdatePartnerComponent";

    @Resource
    private Context context;
    private Node root;
    private VBox container = new VBox();

    private ManagedFragmentHandler<UpdatePersonFormFragment> updatePersonFormHandler;
    private ManagedFragmentHandler<UpdateOrganisationFormFragment> updateOrganisationFormHandler;

    private UpdatePersonFormFragment updatePersonFormController;
    private UpdateOrganisationFormFragment updateOrganisationFormController;

    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        return this.root;
    }

    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        if(message.isMessageBodyTypeOf(PersonResponseProto.class)){
            switchTypeToPerson((PersonResponseProto) message.getMessageBody());
        } else if (message.isMessageBodyTypeOf(OrganisationResponseProto.class)){
            switchTypeToOrganisation((OrganisationResponseProto) message.getMessageBody());
        }
        return null;
    }

    @PostConstruct
    public void onPostConstructComponent() {
        container.setVgrow(container, Priority.ALWAYS);
        container.getStyleClass().add(ClassNameConstant.WHITE_BACKGROUND);
        this.root = initFragment();
    }

    private Node initFragment() {
        return container;
    }

    private void switchTypeToPerson(PersonResponseProto responseProto) {
        updatePersonFormHandler = context.getManagedFragmentHandler(UpdatePersonFormFragment.class);
        updatePersonFormController = updatePersonFormHandler.getController();
        updatePersonFormController.init(responseProto);
        Platform.runLater(() -> {
            container.getChildren().setAll(updatePersonFormHandler.getFragmentNode());
        });
    }

    private void switchTypeToOrganisation(OrganisationResponseProto responseProto) {
        updateOrganisationFormHandler = context.getManagedFragmentHandler(UpdateOrganisationFormFragment.class);
        updateOrganisationFormController = updateOrganisationFormHandler.getController();
        updateOrganisationFormController.init(responseProto);
        Platform.runLater(() -> {
            container.getChildren().setAll(updateOrganisationFormHandler.getFragmentNode());
        });
    }
}
