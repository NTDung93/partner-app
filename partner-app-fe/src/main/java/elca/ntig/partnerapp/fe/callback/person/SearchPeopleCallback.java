package elca.ntig.partnerapp.fe.callback.person;

import elca.ntig.partnerapp.common.proto.entity.person.PersonServiceGrpc;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationRequestProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationResponseProto;
import elca.ntig.partnerapp.fe.common.constant.ServerContant;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.event.Event;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.jacpfx.api.annotations.component.Component;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.annotations.lifecycle.PreDestroy;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.CallbackComponent;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;

@Service
@Component(id = SearchPeopleCallback.ID, name = SearchPeopleCallback.ID)
public class SearchPeopleCallback implements CallbackComponent {
    public static final String ID = "SearchPeopleCallback";

    private ManagedChannel channel;

    @GrpcClient("grpcService")
    private PersonServiceGrpc.PersonServiceBlockingStub stub;

    @PostConstruct
    public void onPostConstruct(final ResourceBundle resourceBundle) {
        this.channel = ManagedChannelBuilder
                .forAddress(ServerContant.SERVER_HOST, ServerContant.SERVER_PORT)
                .usePlaintext()
                .build();
        this.stub = PersonServiceGrpc.newBlockingStub(channel);
    }

    @PreDestroy
    public void onPreDestroy() {
        this.channel.shutdown();
    }

    @Override
    public Object handle(Message<Event, Object> message) throws Exception {
        if (message.isMessageBodyTypeOf(SearchPeoplePaginationRequestProto.class)) {
            SearchPeoplePaginationResponseProto response = searchPeoplePagination((SearchPeoplePaginationRequestProto) message.getMessageBody());
            return response;
        }
        return null;
    }

    private SearchPeoplePaginationResponseProto searchPeoplePagination(SearchPeoplePaginationRequestProto request) {
        return stub.searchPeoplePagination(request);
    }
}
