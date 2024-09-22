package elca.ntig.partnerapp.fe.callback.organisation;

import elca.ntig.partnerapp.common.proto.entity.organisation.CreateOrganisationRequestProto;
import elca.ntig.partnerapp.common.proto.entity.organisation.OrganisationResponseProto;
import elca.ntig.partnerapp.fe.component.CreatePartnerComponent;
import elca.ntig.partnerapp.fe.perspective.CreatePartnerPerspective;
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
@Component(id = CreateOrganisationCallback.ID, name = CreateOrganisationCallback.ID)
public class CreateOrganisationCallback implements CallbackComponent {
    public static final String ID = "CreateOrganisationCallback";

    @Resource
    private Context context;

    @Autowired
    private OrganisationClientService organisationClientService;

    @Override
    public Object handle(Message<Event, Object> message) throws Exception {
        if (message.isMessageBodyTypeOf(CreateOrganisationRequestProto.class)) {
            OrganisationResponseProto response = organisationClientService.createOrganisation((CreateOrganisationRequestProto) message.getMessageBody());
            context.send(CreatePartnerPerspective.ID.concat(".").concat(CreatePartnerComponent.ID), response);
            return response;
        }
        return null;
    }
}
