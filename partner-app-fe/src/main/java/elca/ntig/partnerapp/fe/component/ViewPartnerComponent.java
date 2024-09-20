package elca.ntig.partnerapp.fe.component;

import elca.ntig.partnerapp.common.proto.entity.organisation.SearchOrganisationPaginationResponseProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationResponseProto;
import elca.ntig.partnerapp.fe.common.constant.TargetConstant;
import elca.ntig.partnerapp.fe.common.pagination.PaginationModel;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import elca.ntig.partnerapp.fe.fragment.organisation.OrganisationFormFragment;
import elca.ntig.partnerapp.fe.fragment.person.PersonFormFragment;
import elca.ntig.partnerapp.fe.fragment.person.PersonTableFragment;
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

@View(id = ViewPartnerComponent.ID,
        name = ViewPartnerComponent.ID,
        resourceBundleLocation = ObservableResourceFactory.RESOURCE_BUNDLE_NAME,
        initialTargetLayoutId = TargetConstant.TARGET_VIEW_PARTNER_CONTAINER)
public class ViewPartnerComponent implements FXComponent {
    public static final String ID = "ViewPartnerComponent";
    private static Logger logger = Logger.getLogger(ViewPartnerComponent.class);

    @Resource
    private Context context;
    private Node root;
    private PersonTableFragment tableController;
    private PersonFormFragment personFormController;
    private OrganisationFormFragment organisationFormController;

    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        return this.root;
    }

    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        if (message.isMessageBodyTypeOf(SearchPeoplePaginationResponseProto.class)) {
            SearchPeoplePaginationResponseProto response = (SearchPeoplePaginationResponseProto) message.getMessageBody();
            tableController.updateTable(response);
        }
        if (message.isMessageBodyTypeOf(SearchOrganisationPaginationResponseProto.class)) {
            SearchOrganisationPaginationResponseProto response = (SearchOrganisationPaginationResponseProto) message.getMessageBody();
//            tableController.updateTable(response);
            logger.info("response: " + response);
        }
        if (message.isMessageBodyTypeOf(PaginationModel.class)) {
            PaginationModel paginationModel = (PaginationModel) message.getMessageBody();
            personFormController.handlePagination(paginationModel);
        }
        if (message.isMessageBodyTypeOf(String.class) && message.getMessageBody().equals("reset sort policy for person")) {
            tableController.resetSortPolicy();
        }
        return null;
    }

    @PostConstruct
    public void onPostConstructComponent() {
        this.root = initFragment();
    }

    private Node initFragment() {
        final VBox container = new VBox();
        VBox.setVgrow(container, Priority.ALWAYS);

        final ManagedFragmentHandler<PersonFormFragment> personFormHandler = context.getManagedFragmentHandler(PersonFormFragment.class);
        final ManagedFragmentHandler<OrganisationFormFragment> organisationFormHandler = context.getManagedFragmentHandler(OrganisationFormFragment.class);
        final ManagedFragmentHandler<PersonTableFragment> tableHandler = context.getManagedFragmentHandler(PersonTableFragment.class);
        personFormController = personFormHandler.getController();
        organisationFormController = organisationFormHandler.getController();
        tableController = tableHandler.getController();
        personFormController.init();
        organisationFormController.init();
        tableController.init();

//        container.getChildren().addAll(personFormHandler.getFragmentNode(), tableHandler.getFragmentNode());
        container.getChildren().addAll(organisationFormHandler.getFragmentNode(), tableHandler.getFragmentNode());
        return container;
    }
}
