package elca.ntig.partnerapp.be.controller;

import elca.ntig.partnerapp.be.model.dto.person.SearchPeopleCriteriasDto;
import elca.ntig.partnerapp.be.model.dto.person.SearchPeoplePaginationResponseDto;
import elca.ntig.partnerapp.be.utils.mapper.PersonMapper;
import elca.ntig.partnerapp.be.model.dto.person.PersonResponseDto;
import elca.ntig.partnerapp.be.service.PersonService;
import elca.ntig.partnerapp.be.utils.validator.ArgumentValidator;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import elca.ntig.partnerapp.common.proto.entity.person.PersonServiceGrpc;
import elca.ntig.partnerapp.common.proto.entity.person.GetPersonRequestProto;
import elca.ntig.partnerapp.common.proto.entity.person.PersonResponseProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationResponseProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationRequestProto;

@GrpcService
@RequiredArgsConstructor
public class PersonServiceGrpcImpl extends PersonServiceGrpc.PersonServiceImplBase {
    private final PersonService personService;
    private final PersonMapper personMapper;
    private final ArgumentValidator argumentValidator;

    @Override
    public void getPersonById(GetPersonRequestProto request, StreamObserver<PersonResponseProto> responseObserver) {
        PersonResponseDto personResponseDto = personService.getPersonById(request.getId());
        PersonResponseProto personResponseProto = personMapper.toPersonResponseProto(personResponseDto);
        responseObserver.onNext(personResponseProto);
        responseObserver.onCompleted();
    }

    @Override
    public void searchPeoplePagination(SearchPeoplePaginationRequestProto request, StreamObserver<SearchPeoplePaginationResponseProto> responseObserver) {
        SearchPeopleCriteriasDto searchPeopleCriterias = personMapper.toSearchPeopleCriteriasDto(request.getCriterias());

        argumentValidator.validate(searchPeopleCriterias);

        SearchPeoplePaginationResponseDto searchPeoplePaginationResponseDto = personService.searchPeoplePagination(request.getPageNo(), request.getPageSize(), request.getSortBy(), request.getSortDir(), searchPeopleCriterias);
        SearchPeoplePaginationResponseProto searchPeoplePaginationResponse = personMapper.toSearchPeoplePaginationResponse(searchPeoplePaginationResponseDto);
        responseObserver.onNext(searchPeoplePaginationResponse);
        responseObserver.onCompleted();
    }
}
