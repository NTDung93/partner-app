package elca.ntig.partnerapp.fe.service;

import elca.ntig.partnerapp.common.proto.entity.person.PersonServiceGrpc;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationRequestProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationResponseProto;
import io.grpc.ManagedChannel;

public class PersonClientService {
    private final PersonServiceGrpc.PersonServiceBlockingStub personServiceStub;
    private final ManagedChannel channel;

    public PersonClientService(ManagedChannel managedChannel){
        this.channel = managedChannel;
        this.personServiceStub = PersonServiceGrpc.newBlockingStub(channel);
    }

    public SearchPeoplePaginationResponseProto searchPeoplePagination(SearchPeoplePaginationRequestProto request) {
        return personServiceStub.searchPeoplePagination(request);
    }
}
