package elca.ntig.partnerapp.fe.callback.organisation;

import elca.ntig.partnerapp.common.proto.entity.organisation.CreateOrganisationRequestProto;
import elca.ntig.partnerapp.common.proto.entity.organisation.OrganisationResponseProto;
import elca.ntig.partnerapp.fe.callback.CallBackExceptionHandler;
import elca.ntig.partnerapp.fe.common.constant.MessageConstant;
import elca.ntig.partnerapp.fe.component.CreatePartnerComponent;
import elca.ntig.partnerapp.fe.perspective.CreatePartnerPerspective;
import elca.ntig.partnerapp.fe.perspective.ViewPartnerPerspective;
import elca.ntig.partnerapp.fe.service.OrganisationClientService;
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
@Component(id = CreateOrganisationCallback.ID, name = CreateOrganisationCallback.ID)
public class CreateOrganisationCallback extends CallBackExceptionHandler implements CallbackComponent {
    public static final String ID = "CreateOrganisationCallback";
    private static Logger logger = Logger.getLogger(CreateOrganisationCallback.class);

    @Resource
    private Context context;

    @Autowired
    private OrganisationClientService organisationClientService;

    @Override
    public Object handle(Message<Event, Object> message) throws Exception {
        if (message.isMessageBodyTypeOf(CreateOrganisationRequestProto.class)) {
            try {
                OrganisationResponseProto response = organisationClientService.createOrganisation((CreateOrganisationRequestProto) message.getMessageBody());
                handleSuccessfulResponse();
                context.send(ViewPartnerPerspective.ID, MessageConstant.SWITCH_TYPE_TO_ORGANISATION);
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
