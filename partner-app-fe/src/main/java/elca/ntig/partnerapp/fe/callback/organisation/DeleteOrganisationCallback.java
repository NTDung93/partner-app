package elca.ntig.partnerapp.fe.callback.organisation;

import elca.ntig.partnerapp.common.proto.entity.organisation.DeleteOrganisationResponseProto;
import elca.ntig.partnerapp.common.proto.entity.organisation.GetOrganisationRequestProto;
import elca.ntig.partnerapp.fe.component.ViewPartnerComponent;
import elca.ntig.partnerapp.fe.perspective.ViewPartnerPerspective;
import elca.ntig.partnerapp.fe.service.OrganisationClientService;
import javafx.event.Event;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.Component;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.CallbackComponent;
import org.jacpfx.rcp.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Component(id = DeleteOrganisationCallback.ID, name = DeleteOrganisationCallback.ID)
public class DeleteOrganisationCallback implements CallbackComponent {
    public static final String ID = "DeleteOrganisationCallback";

    @Resource
    private Context context;

    @Autowired
    private OrganisationClientService organisationClientService;

    @Override
    public Object handle(Message<Event, Object> message) throws Exception {
        if (message.isMessageBodyTypeOf(GetOrganisationRequestProto.class)) {
            DeleteOrganisationResponseProto response = organisationClientService.deleteOrganisationById((GetOrganisationRequestProto) message.getMessageBody());
            context.send(ViewPartnerPerspective.ID.concat(".").concat(ViewPartnerComponent.ID), response);
            return response;
        }
        return null;
    }
}
