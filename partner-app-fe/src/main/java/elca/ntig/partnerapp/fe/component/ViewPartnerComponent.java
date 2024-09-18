package elca.ntig.partnerapp.fe.component;

import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationResponseProto;
import elca.ntig.partnerapp.fe.common.constant.TargetConstant;
import elca.ntig.partnerapp.fe.common.pagination.PaginationModel;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import elca.ntig.partnerapp.fe.fragment.PartnerFormFragment;
import elca.ntig.partnerapp.fe.fragment.TableFragment;
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

@View(id = ViewPartnerComponent.ID,
        name = ViewPartnerComponent.ID,
        resourceBundleLocation = ObservableResourceFactory.RESOURCE_BUNDLE_NAME,
        initialTargetLayoutId = TargetConstant.TARGET_VIEW_PARTNER_CONTAINER)
public class ViewPartnerComponent implements FXComponent {
    public static final String ID = "ViewPartnerComponent";

    @Resource
    private Context context;

    private Node root;

    private TableFragment tableController;
    private PartnerFormFragment formController;

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
        if (message.isMessageBodyTypeOf(PaginationModel.class)) {
            PaginationModel paginationModel = (PaginationModel) message.getMessageBody();
            formController.handlePagination(paginationModel.getPageNo(), paginationModel.getPageSize());
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

        final ManagedFragmentHandler<PartnerFormFragment> formHandler = context.getManagedFragmentHandler(PartnerFormFragment.class);
        final ManagedFragmentHandler<TableFragment> tableHandler = context.getManagedFragmentHandler(TableFragment.class);
        formController = formHandler.getController();
        tableController = tableHandler.getController();
        formController.init();
        tableController.init();

        container.getChildren().addAll(formHandler.getFragmentNode(), tableHandler.getFragmentNode());
        return container;
    }
}
