package elca.ntig.partnerapp.fe.callback.person;

import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationRequestProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationResponseProto;
import elca.ntig.partnerapp.fe.callback.CallBackExceptionHandler;
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
@Component(id = SearchPeopleCallback.ID, name = SearchPeopleCallback.ID)
public class SearchPeopleCallback extends CallBackExceptionHandler implements CallbackComponent {
    public static final String ID = "SearchPeopleCallback";
    private static Logger logger = Logger.getLogger(SearchPeopleCallback.class);

    @Resource
    private Context context;

    @Autowired
    private PersonClientService personClientService;

    @Override
    public Object handle(Message<Event, Object> message) throws Exception {
        if (message.isMessageBodyTypeOf(SearchPeoplePaginationRequestProto.class)) {
            try{
            SearchPeoplePaginationResponseProto response = personClientService.searchPeoplePagination((SearchPeoplePaginationRequestProto) message.getMessageBody());
            context.send(ViewPartnerPerspective.ID.concat(".").concat(ViewPartnerComponent.ID), response);
            return response;
            } catch (Exception e) {
                logger.error(e.getMessage());
                if (e instanceof StatusRuntimeException) {
                    handleStatusRuntimeException(e, "searchPartner");
                }else{
                    handleUnexpectedException(e);
                }
            }
        }
        return null;
    }
}
