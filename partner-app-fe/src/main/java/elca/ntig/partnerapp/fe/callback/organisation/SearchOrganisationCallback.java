package elca.ntig.partnerapp.fe.callback.organisation;

import elca.ntig.partnerapp.common.proto.entity.organisation.SearchOrganisationPaginationRequestProto;
import elca.ntig.partnerapp.common.proto.entity.organisation.SearchOrganisationPaginationResponseProto;
import elca.ntig.partnerapp.fe.callback.CallBackExceptionHandler;
import elca.ntig.partnerapp.fe.component.ViewPartnerComponent;
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
@Component(id = SearchOrganisationCallback.ID, name = SearchOrganisationCallback.ID)
public class SearchOrganisationCallback extends CallBackExceptionHandler implements CallbackComponent {
    public static final String ID = "SearchOrganisationCallback";
    private static Logger logger = Logger.getLogger(SearchOrganisationCallback.class);

    @Resource
    private Context context;

    @Autowired
    private OrganisationClientService organisationClientService;

    @Override
    public Object handle(Message<Event, Object> message) throws Exception {
        if (message.isMessageBodyTypeOf(SearchOrganisationPaginationRequestProto.class)) {
            try{
            SearchOrganisationPaginationResponseProto response = organisationClientService.searchOrganisationPagination((SearchOrganisationPaginationRequestProto) message.getMessageBody());
            context.send(ViewPartnerPerspective.ID.concat(".").concat(ViewPartnerComponent.ID), response);
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
