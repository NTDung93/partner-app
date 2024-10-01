package elca.ntig.partnerapp.fe.component;

import elca.ntig.partnerapp.common.proto.entity.address.CreateAddressRequestProto;
import elca.ntig.partnerapp.common.proto.enums.common.PartnerTypeProto;
import elca.ntig.partnerapp.fe.common.constant.ClassNameConstant;
import elca.ntig.partnerapp.fe.common.constant.MessageConstant;
import elca.ntig.partnerapp.fe.common.constant.TargetConstant;
import elca.ntig.partnerapp.fe.common.enums.Resolution;
import elca.ntig.partnerapp.fe.common.message.CreateAddressMessage;
import elca.ntig.partnerapp.fe.common.message.UpdateAddressMessage;
import elca.ntig.partnerapp.fe.common.message.UpdateAddressResponseMessage;
import elca.ntig.partnerapp.fe.fragment.address.CreateAddressFormFragment;
import elca.ntig.partnerapp.fe.fragment.address.UpdateAddressFormFragment;
import elca.ntig.partnerapp.fe.fragment.organisation.CreateOrganisationFormFragment;
import elca.ntig.partnerapp.fe.fragment.person.CreatePersonFormFragment;
import elca.ntig.partnerapp.fe.utils.ObservableResourceFactory;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    private ManagedFragmentHandler<CreateAddressFormFragment> createAddressFormHandler;
    private ManagedFragmentHandler<UpdateAddressFormFragment> updateAddressFormHandler;

    private CreatePersonFormFragment createPersonFormController;
    private CreateOrganisationFormFragment createOrganisationFormController;
    private CreateAddressFormFragment createAddressFormController;
    private UpdateAddressFormFragment updateAddressFormController;

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
        } else if (message.getMessageBody().equals(MessageConstant.SHOW_CREATE_ADDRESS_FORM_FOR_PERSON)) {
            showCreateAddressForm(PartnerTypeProto.TYPE_PERSON);
        } else if (message.getMessageBody().equals(MessageConstant.SHOW_CREATE_ADDRESS_FORM_FOR_ORGANISATION)) {
            showCreateAddressForm(PartnerTypeProto.TYPE_ORGANISATION);
        } else if (message.isMessageBodyTypeOf(CreateAddressMessage.class)) {
            CreateAddressMessage createAddressMessage = (CreateAddressMessage) message.getMessageBody();
            if (createAddressMessage.getPartnerType().equals(PartnerTypeProto.TYPE_PERSON)) {
                createPersonFormController.updateAddressTable(createAddressMessage.getCreateAddressRequestProto());
            } else if (createAddressMessage.getPartnerType().equals(PartnerTypeProto.TYPE_ORGANISATION)) {
                createOrganisationFormController.updateAddressTable(createAddressMessage.getCreateAddressRequestProto());
            }
        } else if(message.isMessageBodyTypeOf(UpdateAddressMessage.class)) {
            UpdateAddressMessage updateAddressMessage = (UpdateAddressMessage) message.getMessageBody();
            if (updateAddressMessage.getPartnerType().equals(PartnerTypeProto.TYPE_PERSON)) {
                showUpdateAddressForm(PartnerTypeProto.TYPE_PERSON, updateAddressMessage.getUpdateAddressRequestProto());
            } else if (updateAddressMessage.getPartnerType().equals(PartnerTypeProto.TYPE_ORGANISATION)) {
                showUpdateAddressForm(PartnerTypeProto.TYPE_ORGANISATION, updateAddressMessage.getUpdateAddressRequestProto());
            }
        } else if (message.isMessageBodyTypeOf(UpdateAddressResponseMessage.class)) {
            UpdateAddressResponseMessage updateAddressResponseMessage = (UpdateAddressResponseMessage) message.getMessageBody();
            if (updateAddressResponseMessage.getPartnerType().equals(PartnerTypeProto.TYPE_PERSON)) {
                createPersonFormController.updateRowData(updateAddressResponseMessage.getUpdateAddressRequestProto());
            } else if (updateAddressResponseMessage.getPartnerType().equals(PartnerTypeProto.TYPE_ORGANISATION)) {
                createOrganisationFormController.updateRowData(updateAddressResponseMessage.getUpdateAddressRequestProto());
            }
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

    private void showCreateAddressForm(PartnerTypeProto partnerType) {
        createAddressFormHandler = context.getManagedFragmentHandler(CreateAddressFormFragment.class);
        createAddressFormController = createAddressFormHandler.getController();
        createAddressFormController.init(partnerType, false);
        Platform.runLater(() -> {
            Stage popupWindow = new Stage();
            popupWindow.initModality(Modality.APPLICATION_MODAL);
            popupWindow.setTitle("Create Address Form");

            Resolution resolution = Resolution.resolutionByPrimaryScreenBounds();
            int popupWidth = (int) (resolution.width() * 0.75);
            int popupHeight = (int) (resolution.height() * 0.6);
            popupWindow.setWidth(popupWidth);
            popupWindow.setHeight(popupHeight);

            Scene scene = new Scene((Parent) createAddressFormHandler.getFragmentNode(), popupWidth, popupHeight);
            popupWindow.setScene(scene);
            popupWindow.show();
        });
    }

    private void showUpdateAddressForm(PartnerTypeProto partnerType, CreateAddressRequestProto createAddressRequestProto) {
        updateAddressFormHandler = context.getManagedFragmentHandler(UpdateAddressFormFragment.class);
        updateAddressFormController = updateAddressFormHandler.getController();
        updateAddressFormController.init(partnerType, createAddressRequestProto, false);
        Platform.runLater(() -> {
            Stage popupWindow = new Stage();
            popupWindow.initModality(Modality.APPLICATION_MODAL);
            popupWindow.setTitle("Update Address Form");

            Resolution resolution = Resolution.resolutionByPrimaryScreenBounds();
            int popupWidth = (int) (resolution.width() * 0.90);
            int popupHeight = (int) (resolution.height() * 0.6);
            popupWindow.setWidth(popupWidth);
            popupWindow.setHeight(popupHeight);

            Scene scene = new Scene((Parent) updateAddressFormHandler.getFragmentNode(), popupWidth, popupHeight);
            popupWindow.setScene(scene);
            popupWindow.show();
        });
    }
}
