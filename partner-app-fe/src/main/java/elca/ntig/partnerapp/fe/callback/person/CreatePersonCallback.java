package elca.ntig.partnerapp.fe.callback.person;

import elca.ntig.partnerapp.common.proto.entity.person.CreatePersonRequestProto;
import elca.ntig.partnerapp.common.proto.entity.person.PersonResponseProto;
import elca.ntig.partnerapp.fe.component.CreatePartnerComponent;
import elca.ntig.partnerapp.fe.perspective.CreatePartnerPerspective;
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
@Component(id = CreatePersonCallback.ID, name = CreatePersonCallback.ID)
public class CreatePersonCallback implements CallbackComponent {
    public static final String ID = "CreatePersonCallback";

    @Resource
    private Context context;

    @Autowired
    private PersonClientService personClientService;

    @Override
    public Object handle(Message<Event, Object> message) throws Exception {
        if (message.isMessageBodyTypeOf(CreatePersonRequestProto.class)) {
            PersonResponseProto response = personClientService.createPerson((CreatePersonRequestProto) message.getMessageBody());
            context.send(CreatePartnerPerspective.ID.concat(".").concat(CreatePartnerComponent.ID), response);
            return response;
        }
        return null;
    }
}
