package elca.ntig.partnerapp.fe.callback.person;

import elca.ntig.partnerapp.common.proto.entity.person.GetPersonAlongWithAddressResponseProto;
import elca.ntig.partnerapp.common.proto.entity.person.GetPersonRequestProto;
import elca.ntig.partnerapp.fe.callback.CallBackExceptionHandler;
import elca.ntig.partnerapp.fe.common.constant.MessageConstant;
import elca.ntig.partnerapp.fe.component.UpdatePartnerComponent;
import elca.ntig.partnerapp.fe.perspective.UpdatePartnerPerspective;
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
@Component(id = GetPersonCallBack.ID, name = GetPersonCallBack.ID)
public class GetPersonCallBack  extends CallBackExceptionHandler implements CallbackComponent {
    public static final String ID = "GetPersonCallBack";
    private static Logger logger = Logger.getLogger(GetPersonCallBack.class);

    @Resource
    private Context context;

    @Autowired
    private PersonClientService personClientService;

    @Override
    public Object handle(Message<Event, Object> message) throws Exception {
        if (message.isMessageBodyTypeOf(GetPersonRequestProto.class)) {
            try {
                GetPersonAlongWithAddressResponseProto response = personClientService.getPersonAlongWithAddress((GetPersonRequestProto) message.getMessageBody());
                context.send(UpdatePartnerPerspective.ID, MessageConstant.INIT);
                context.send(UpdatePartnerPerspective.ID.concat(".").concat(UpdatePartnerComponent.ID), response);
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
