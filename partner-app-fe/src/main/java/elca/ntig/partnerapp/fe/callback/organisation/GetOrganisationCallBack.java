package elca.ntig.partnerapp.fe.callback.organisation;

import elca.ntig.partnerapp.common.proto.entity.organisation.GetOrganisationAlongWithAddressResponseProto;
import elca.ntig.partnerapp.common.proto.entity.organisation.GetOrganisationRequestProto;
import elca.ntig.partnerapp.fe.callback.CallBackExceptionHandler;
import elca.ntig.partnerapp.fe.common.constant.MessageConstant;
import elca.ntig.partnerapp.fe.component.UpdatePartnerComponent;
import elca.ntig.partnerapp.fe.perspective.UpdatePartnerPerspective;
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
@Component(id = GetOrganisationCallBack.ID, name = GetOrganisationCallBack.ID)
public class GetOrganisationCallBack  extends CallBackExceptionHandler implements CallbackComponent {
    public static final String ID = "GetOrganisationCallBack";
    private static Logger logger = Logger.getLogger(GetOrganisationCallBack.class);

    @Resource
    private Context context;

    @Autowired
    private OrganisationClientService organisationClientService;

    @Override
    public Object handle(Message<Event, Object> message) throws Exception {
        if (message.isMessageBodyTypeOf(GetOrganisationRequestProto.class)) {
            try {
                GetOrganisationAlongWithAddressResponseProto response = organisationClientService.getOrganisationAlongWithAddress((GetOrganisationRequestProto) message.getMessageBody());
                context.send(UpdatePartnerPerspective.ID, MessageConstant.INIT);
                context.send(UpdatePartnerPerspective.ID.concat(".").concat(UpdatePartnerComponent.ID), response);
                return response;
            } catch (Exception e) {
                logger.error(e.getMessage());
                if (e instanceof StatusRuntimeException) {
                    handleStatusRuntimeException(e, "getPartner");
                }else{
                    handleUnexpectedException(e);
                }
            }
        }
        return null;
    }
}
