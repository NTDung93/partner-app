package elca.ntig.partnerapp.fe.callback.person;

import elca.ntig.partnerapp.common.proto.entity.person.CreatePersonRequestProto;
import elca.ntig.partnerapp.common.proto.entity.person.PersonResponseProto;
import elca.ntig.partnerapp.fe.callback.CallBackExceptionHandler;
import elca.ntig.partnerapp.fe.common.constant.MessageConstant;
import elca.ntig.partnerapp.fe.component.ViewPartnerComponent;
import elca.ntig.partnerapp.fe.perspective.ViewPartnerPerspective;
import elca.ntig.partnerapp.fe.service.PersonClientService;
import io.grpc.StatusRuntimeException;
import javafx.event.Event;
import org.apache.log4j.Logger;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.Component;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.CallbackComponent;
import org.jacpfx.rcp.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Component(id = CreatePersonCallback.ID, name = CreatePersonCallback.ID)
public class CreatePersonCallback extends CallBackExceptionHandler implements CallbackComponent {
    public static final String ID = "CreatePersonCallback";
    private static Logger logger = Logger.getLogger(CreatePersonCallback.class);

    @Autowired
    private PersonClientService personClientService;

    @Resource
    private Context context;

    @Override
    public Object handle(Message<Event, Object> message) throws Exception {
        if (message.isMessageBodyTypeOf(CreatePersonRequestProto.class)) {
            try {
                PersonResponseProto response = personClientService.createPerson((CreatePersonRequestProto) message.getMessageBody());
                handleSuccessfulResponse("createPartner");
                context.send(ViewPartnerPerspective.ID, MessageConstant.INIT);
                context.send(ViewPartnerPerspective.ID.concat(".").concat(ViewPartnerComponent.ID), MessageConstant.SWITCH_TYPE_TO_PERSON);
                return response;
            } catch (Exception e) {
                logger.error(e.getMessage());
                if (e instanceof StatusRuntimeException) {
                    handleStatusRuntimeException(e);
                }else{
                    handleUnexpectedException(e);
                }
            }
        }
        return null;
    }
}
