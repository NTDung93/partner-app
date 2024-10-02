package elca.ntig.partnerapp.fe.component;

import elca.ntig.partnerapp.common.proto.entity.address.CreateAddressRequestProto;
import elca.ntig.partnerapp.common.proto.entity.organisation.GetOrganisationAlongWithAddressResponseProto;
import elca.ntig.partnerapp.common.proto.entity.person.GetPersonAlongWithAddressResponseProto;
import elca.ntig.partnerapp.common.proto.enums.common.PartnerTypeProto;
import elca.ntig.partnerapp.common.proto.enums.common.StatusProto;
import elca.ntig.partnerapp.fe.common.constant.ClassNameConstant;
import elca.ntig.partnerapp.fe.common.constant.MessageConstant;
import elca.ntig.partnerapp.fe.common.constant.TargetConstant;
import elca.ntig.partnerapp.fe.common.enums.Resolution;
import elca.ntig.partnerapp.fe.common.message.CreateAddressMessage;
import elca.ntig.partnerapp.fe.common.message.UpdateAddressMessage;
import elca.ntig.partnerapp.fe.common.message.UpdateAddressResponseMessage;
import elca.ntig.partnerapp.fe.fragment.address.CreateAddressFormFragment;
import elca.ntig.partnerapp.fe.fragment.address.UpdateAddressFormFragment;
import elca.ntig.partnerapp.fe.fragment.organisation.UpdateOrganisationFormFragment;
import elca.ntig.partnerapp.fe.fragment.person.UpdatePersonFormFragment;
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
    private ManagedFragmentHandler<CreateAddressFormFragment> createAddressFormHandler;
    private ManagedFragmentHandler<UpdateAddressFormFragment> updateAddressFormHandler;

    private UpdatePersonFormFragment updatePersonFormController;
    private UpdateOrganisationFormFragment updateOrganisationFormController;
    private CreateAddressFormFragment createAddressFormController;
    private UpdateAddressFormFragment updateAddressFormController;

    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        return this.root;
    }

    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        if (message.isMessageBodyTypeOf(GetPersonAlongWithAddressResponseProto.class)) {
            switchTypeToPerson((GetPersonAlongWithAddressResponseProto) message.getMessageBody());
        } else if (message.isMessageBodyTypeOf(GetOrganisationAlongWithAddressResponseProto.class)) {
            switchTypeToOrganisation((GetOrganisationAlongWithAddressResponseProto) message.getMessageBody());
        } else if (message.getMessageBody().equals(MessageConstant.SHOW_CREATE_ADDRESS_FORM_FOR_PERSON)) {
            showCreateAddressForm(PartnerTypeProto.TYPE_PERSON);
        } else if (message.getMessageBody().equals(MessageConstant.SHOW_CREATE_ADDRESS_FORM_FOR_ORGANISATION)) {
            showCreateAddressForm(PartnerTypeProto.TYPE_ORGANISATION);
        } else if (message.isMessageBodyTypeOf(CreateAddressMessage.class)) {
            CreateAddressMessage createAddressMessage = (CreateAddressMessage) message.getMessageBody();
            if (createAddressMessage.getPartnerType().equals(PartnerTypeProto.TYPE_PERSON)) {
                updatePersonFormController.updateAddressTable(createAddressMessage.getCreateAddressRequestProto());
            } else if (createAddressMessage.getPartnerType().equals(PartnerTypeProto.TYPE_ORGANISATION)) {
                updateOrganisationFormController.updateAddressTable(createAddressMessage.getCreateAddressRequestProto());
            }
        } else if (message.isMessageBodyTypeOf(UpdateAddressMessage.class)) {
            UpdateAddressMessage updateAddressMessage = (UpdateAddressMessage) message.getMessageBody();
            if (updateAddressMessage.getPartnerType().equals(PartnerTypeProto.TYPE_PERSON)) {
                showUpdateAddressForm(PartnerTypeProto.TYPE_PERSON, updateAddressMessage.getUpdateAddressRequestProto(), updateAddressMessage.getStatus());
            } else if (updateAddressMessage.getPartnerType().equals(PartnerTypeProto.TYPE_ORGANISATION)) {
                showUpdateAddressForm(PartnerTypeProto.TYPE_ORGANISATION, updateAddressMessage.getUpdateAddressRequestProto(), updateAddressMessage.getStatus());
            }
        }else if (message.isMessageBodyTypeOf(UpdateAddressResponseMessage.class)) {
            UpdateAddressResponseMessage updateAddressResponseMessage = (UpdateAddressResponseMessage) message.getMessageBody();
            if (updateAddressResponseMessage.getPartnerType().equals(PartnerTypeProto.TYPE_PERSON)) {
                updatePersonFormController.updateRowData(updateAddressResponseMessage.getUpdateAddressRequestProto());
            } else if (updateAddressResponseMessage.getPartnerType().equals(PartnerTypeProto.TYPE_ORGANISATION)) {
                updateOrganisationFormController.updateRowData(updateAddressResponseMessage.getUpdateAddressRequestProto());
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
        return container;
    }

    private void switchTypeToPerson(GetPersonAlongWithAddressResponseProto responseProto) {
        updatePersonFormHandler = context.getManagedFragmentHandler(UpdatePersonFormFragment.class);
        updatePersonFormController = updatePersonFormHandler.getController();
        updatePersonFormController.init(responseProto);
        Platform.runLater(() -> {
            container.getChildren().setAll(updatePersonFormHandler.getFragmentNode());
        });
    }

    private void switchTypeToOrganisation(GetOrganisationAlongWithAddressResponseProto responseProto) {
        updateOrganisationFormHandler = context.getManagedFragmentHandler(UpdateOrganisationFormFragment.class);
        updateOrganisationFormController = updateOrganisationFormHandler.getController();
        updateOrganisationFormController.init(responseProto);
        Platform.runLater(() -> {
            container.getChildren().setAll(updateOrganisationFormHandler.getFragmentNode());
        });
    }

    private void showCreateAddressForm(PartnerTypeProto partnerType) {
        createAddressFormHandler = context.getManagedFragmentHandler(CreateAddressFormFragment.class);
        createAddressFormController = createAddressFormHandler.getController();
        createAddressFormController.init(partnerType, true);
        Platform.runLater(() -> {
            Stage popupWindow = new Stage();
            popupWindow.initModality(Modality.APPLICATION_MODAL);
//            popupWindow.setTitle("Create Address Form");

            Resolution resolution = Resolution.resolutionByPrimaryScreenBounds();
            int popupWidth = (int) (resolution.width() * 0.90);
            int popupHeight = (int) (resolution.height() * 0.6);
            popupWindow.setWidth(popupWidth);
            popupWindow.setHeight(popupHeight);

            Scene scene = new Scene((Parent) createAddressFormHandler.getFragmentNode(), popupWidth, popupHeight);
            popupWindow.setScene(scene);
            popupWindow.show();
        });
    }

    private void showUpdateAddressForm(PartnerTypeProto partnerType, CreateAddressRequestProto createAddressRequestProto, StatusProto status) {
        updateAddressFormHandler = context.getManagedFragmentHandler(UpdateAddressFormFragment.class);
        updateAddressFormController = updateAddressFormHandler.getController();
        updateAddressFormController.init(partnerType, createAddressRequestProto, true, status);
        Platform.runLater(() -> {
            Stage popupWindow = new Stage();
            popupWindow.initModality(Modality.APPLICATION_MODAL);
//            popupWindow.setTitle("Update Address Form");

            Resolution resolution = Resolution.resolutionByPrimaryScreenBounds();
            int popupWidth = (int) (resolution.width() * 0.75);
            int popupHeight = (int) (resolution.height() * 0.6);
            popupWindow.setWidth(popupWidth);
            popupWindow.setHeight(popupHeight);

            Scene scene = new Scene((Parent) updateAddressFormHandler.getFragmentNode(), popupWidth, popupHeight);
            popupWindow.setScene(scene);
            popupWindow.show();
        });
    }
}
