package elca.ntig.partnerapp.be.controller;

import elca.ntig.partnerapp.be.mappingservice.PersonMappingService;
import elca.ntig.partnerapp.common.proto.entity.person.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class PersonServiceGrpcImpl extends PersonServiceGrpc.PersonServiceImplBase {
    private final PersonMappingService personServiceGrpcHelper;

    @Override
    public void getPersonById(GetPersonRequestProto request, StreamObserver<PersonResponseProto> responseObserver) {
        responseObserver.onNext(personServiceGrpcHelper.getPersonByIdHelper(request));
        responseObserver.onCompleted();
    }

    @Override
    public void searchPeoplePagination(SearchPeoplePaginationRequestProto request, StreamObserver<SearchPeoplePaginationResponseProto> responseObserver) {
        responseObserver.onNext(personServiceGrpcHelper.searchPeoplePaginationHelper(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deletePersonById(GetPersonRequestProto request, StreamObserver<DeletePersonResponseProto> responseObserver) {
        responseObserver.onNext(personServiceGrpcHelper.deletePersonById(request.getId()));
        responseObserver.onCompleted();
    }
}
