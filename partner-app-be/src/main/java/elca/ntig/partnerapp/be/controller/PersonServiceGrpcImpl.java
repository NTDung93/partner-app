package elca.ntig.partnerapp.be.controller;

import elca.ntig.partnerapp.be.utils.mapper.PersonMapper;
import elca.ntig.partnerapp.be.model.dto.person.PersonResponseDto;
import elca.ntig.partnerapp.be.service.PersonService;
import elca.ntig.partnerapp.common.proto.entity.person.GetPersonRequest;
import elca.ntig.partnerapp.common.proto.entity.person.PersonResponse;
import elca.ntig.partnerapp.common.proto.entity.person.PersonServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class PersonServiceGrpcImpl extends PersonServiceGrpc.PersonServiceImplBase {
    private final PersonService personService;
    private final PersonMapper personMapper;

    @Override
    public void getPersonById(GetPersonRequest request, StreamObserver<PersonResponse> responseObserver) {
        PersonResponseDto personResponseDto = personService.getPersonById(request.getId());
        PersonResponse personResponse = personMapper.toPersonResponse(personResponseDto);
        responseObserver.onNext(personResponse);
        responseObserver.onCompleted();
    }
}
