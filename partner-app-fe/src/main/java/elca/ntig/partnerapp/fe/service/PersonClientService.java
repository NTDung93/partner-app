package elca.ntig.partnerapp.fe.service;

import elca.ntig.partnerapp.common.proto.entity.person.*;
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

    public DeletePersonResponseProto deletePersonById(GetPersonRequestProto request) {
        return personServiceStub.deletePersonById(request);
    }

    public PersonResponseProto createPerson(CreatePersonRequestProto request) {
        return personServiceStub.createPerson(request);
    }

    public PersonResponseProto updatePerson(UpdatePersonRequestProto request) {
        return personServiceStub.updatePerson(request);
    }
}
