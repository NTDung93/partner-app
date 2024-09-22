package elca.ntig.partnerapp.fe.callback.person;

import elca.ntig.partnerapp.common.proto.entity.person.DeletePersonResponseProto;
import elca.ntig.partnerapp.common.proto.entity.person.GetPersonRequestProto;
import elca.ntig.partnerapp.fe.component.ViewPartnerComponent;
import elca.ntig.partnerapp.fe.perspective.ViewPartnerPerspective;
import elca.ntig.partnerapp.fe.service.PersonClientService;
import javafx.event.Event;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.Component;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.CallbackComponent;
import org.jacpfx.rcp.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Component(id = DeletePersonCallback.ID, name = DeletePersonCallback.ID)
public class DeletePersonCallback implements CallbackComponent {
    public static final String ID = "DeletePersonCallback";

    @Resource
    private Context context;

    @Autowired
    private PersonClientService personClientService;

    @Override
    public Object handle(Message<Event, Object> message) throws Exception {
        if (message.isMessageBodyTypeOf(GetPersonRequestProto.class)) {
            DeletePersonResponseProto response = personClientService.deletePersonById((GetPersonRequestProto) message.getMessageBody());
            context.send(ViewPartnerPerspective.ID.concat(".").concat(ViewPartnerComponent.ID), response);
            return response;
        }
        return null;
    }
}
