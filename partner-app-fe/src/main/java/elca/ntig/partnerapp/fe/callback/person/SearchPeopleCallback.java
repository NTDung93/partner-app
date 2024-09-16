package elca.ntig.partnerapp.fe.callback.person;

import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationRequestProto;
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
@Component(id = SearchPeopleCallback.ID, name = SearchPeopleCallback.ID)
public class SearchPeopleCallback implements CallbackComponent {
    public static final String ID = "SearchPeopleCallback";

    @Autowired
    private PersonClientService personClientService;

    @Resource
    private Context context;

    @Override
    public Object handle(Message<Event, Object> message) throws Exception {
        if (message.isMessageBodyTypeOf(SearchPeoplePaginationRequestProto.class)) {
            return personClientService.searchPeoplePagination(message.getTypedMessageBody(SearchPeoplePaginationRequestProto.class));
        }
        return null;
    }
}
