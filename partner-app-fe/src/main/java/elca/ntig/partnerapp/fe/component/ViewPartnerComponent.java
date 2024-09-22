package elca.ntig.partnerapp.fe.component;

import elca.ntig.partnerapp.common.proto.entity.organisation.DeleteOrganisationResponseProto;
import elca.ntig.partnerapp.common.proto.entity.organisation.SearchOrganisationPaginationResponseProto;
import elca.ntig.partnerapp.common.proto.entity.person.DeletePersonResponseProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationResponseProto;
import elca.ntig.partnerapp.common.proto.enums.common.PartnerTypeProto;
import elca.ntig.partnerapp.fe.common.constant.MessageConstant;
import elca.ntig.partnerapp.fe.common.constant.PaginationConstant;
import elca.ntig.partnerapp.fe.common.constant.TargetConstant;
import elca.ntig.partnerapp.fe.common.model.PaginationModel;
import elca.ntig.partnerapp.fe.utils.ObservableResourceFactory;
import elca.ntig.partnerapp.fe.fragment.organisation.SearchOrganisationFormFragment;
import elca.ntig.partnerapp.fe.fragment.organisation.OrganisationTableFragment;
import elca.ntig.partnerapp.fe.fragment.person.SearchPersonFormFragment;
import elca.ntig.partnerapp.fe.fragment.person.PersonTableFragment;
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

    private VBox container = new VBox();

    PaginationModel paginationModel;

    private ManagedFragmentHandler<SearchPersonFormFragment> personFormHandler;
    private ManagedFragmentHandler<PersonTableFragment> personTableHandler;
    private ManagedFragmentHandler<SearchOrganisationFormFragment> organisationFormHandler;
    private ManagedFragmentHandler<OrganisationTableFragment> organisationTableHandler;

    private SearchPersonFormFragment personFormController;
    private PersonTableFragment personTableController;
    private SearchOrganisationFormFragment organisationFormController;
    private OrganisationTableFragment organisationTableController;

    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        return this.root;
    }

    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        if (message.isMessageBodyTypeOf(SearchPeoplePaginationResponseProto.class)) {
            SearchPeoplePaginationResponseProto response = (SearchPeoplePaginationResponseProto) message.getMessageBody();
            personTableController.updateTable(response);
        }
        if (message.isMessageBodyTypeOf(SearchOrganisationPaginationResponseProto.class)) {
            SearchOrganisationPaginationResponseProto response = (SearchOrganisationPaginationResponseProto) message.getMessageBody();
            organisationTableController.updateTable(response);
        }
        if (message.isMessageBodyTypeOf(PaginationModel.class)) {
            paginationModel = (PaginationModel) message.getMessageBody();
            if (paginationModel.getPartnerType().equals(PartnerTypeProto.TYPE_PERSON)) {
                personFormController.handlePagination(paginationModel);
            } else {
                organisationFormController.handlePagination(paginationModel);
            }
        }
        if (message.getMessageBody().equals(MessageConstant.RESET_SORT_POLICY_FOR_PERSON)) {
            personTableController.resetSortPolicy();
        }
        if (message.getMessageBody().equals(MessageConstant.RESET_SORT_POLICY_FOR_ORGANISATION)) {
            organisationTableController.resetSortPolicy();
        }
        if (message.getMessageBody().equals(MessageConstant.SWITCH_TYPE_TO_ORGANISATION)) {
            paginationModel.setPartnerType(PartnerTypeProto.TYPE_ORGANISATION);
            switchTypeToOrganisation();
        }
        if (message.getMessageBody().equals(MessageConstant.SWITCH_TYPE_TO_PERSON)) {
            paginationModel.setPartnerType(PartnerTypeProto.TYPE_PERSON);
            switchTypeToPerson();
        }
        if (message.isMessageBodyTypeOf(DeletePersonResponseProto.class)) {
            DeletePersonResponseProto response = (DeletePersonResponseProto) message.getMessageBody();
            if (response.getMessage().equals(MessageConstant.DELETE_PARTNER_SUCCESSFULLY)) {
                personFormController.handlePagination(paginationModel);
            }
        }
        if (message.isMessageBodyTypeOf(DeleteOrganisationResponseProto.class)) {
            DeleteOrganisationResponseProto response = (DeleteOrganisationResponseProto) message.getMessageBody();
            if (response.getMessage().equals(MessageConstant.DELETE_PARTNER_SUCCESSFULLY)) {
                organisationFormController.handlePagination(paginationModel);
            }
        }
        if (message.getMessageBody().equals(MessageConstant.REFRESH_PERSON_TABLE)) {
            paginationModel.setPartnerType(PartnerTypeProto.TYPE_PERSON);
            switchTypeToPerson();
        }
        if (message.getMessageBody().equals(MessageConstant.REFRESH_ORGANISATION_TABLE)) {
            paginationModel.setPartnerType(PartnerTypeProto.TYPE_ORGANISATION);
            switchTypeToOrganisation();
        }
        return null;
    }

    @PostConstruct
    public void onPostConstructComponent() {
        container.setVgrow(container, Priority.ALWAYS);
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

    private void switchTypeToOrganisation() {
        organisationFormHandler = context.getManagedFragmentHandler(SearchOrganisationFormFragment.class);
        organisationTableHandler = context.getManagedFragmentHandler(OrganisationTableFragment.class);
        organisationFormController = organisationFormHandler.getController();
        organisationTableController = organisationTableHandler.getController();
        organisationFormController.init();
        organisationTableController.init();
        Platform.runLater(() -> {
            container.getChildren().setAll(organisationFormHandler.getFragmentNode(), organisationTableHandler.getFragmentNode());
        });
    }

    private void switchTypeToPerson() {
        personFormHandler = context.getManagedFragmentHandler(SearchPersonFormFragment.class);
        personTableHandler = context.getManagedFragmentHandler(PersonTableFragment.class);
        personFormController = personFormHandler.getController();
        personTableController = personTableHandler.getController();
        personFormController.init();
        personTableController.init();
        Platform.runLater(() -> {
            container.getChildren().setAll(personFormHandler.getFragmentNode(), personTableHandler.getFragmentNode());
        });
    }
}
