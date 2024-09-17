package elca.ntig.partnerapp.fe.service;

import elca.ntig.partnerapp.common.proto.entity.person.PersonServiceGrpc;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationRequestProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationResponseProto;
import elca.ntig.partnerapp.fe.common.constant.ServerContant;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class PersonClientService {
    private final ManagedChannel channel;
    private final PersonServiceGrpc.PersonServiceBlockingStub personServiceStub;

    public PersonClientService(){
        this.channel = ManagedChannelBuilder
                .forAddress(ServerContant.SERVER_HOST, ServerContant.SERVER_PORT)
                .usePlaintext()
                .build();
        this.personServiceStub = PersonServiceGrpc.newBlockingStub(channel);
    }

    public SearchPeoplePaginationResponseProto searchPeoplePagination(SearchPeoplePaginationRequestProto request) {
        return personServiceStub.searchPeoplePagination(request);
    }
}
