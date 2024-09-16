package elca.ntig.partnerapp.fe.service;

import elca.ntig.partnerapp.common.proto.entity.person.PersonServiceGrpc;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationRequestProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationResponseProto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class PersonClientService {
//    @GrpcClient("grpc-service")
    PersonServiceGrpc.PersonServiceBlockingStub personServiceStub;

    public SearchPeoplePaginationResponseProto searchPeoplePagination(SearchPeoplePaginationRequestProto request) {
        return personServiceStub.searchPeoplePagination(request);
    }
}
