package elca.ntig.partnerapp.be.controller;

import elca.ntig.partnerapp.be.mappingservice.OrganisationMappingService;
import elca.ntig.partnerapp.common.proto.entity.organisation.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class OrganisationServiceGrpcImpl extends OrganisationServiceGrpc.OrganisationServiceImplBase {
    private final OrganisationMappingService organisationServiceGrpcHelper;

    @Override
    public void getOrganisationById(GetOrganisationRequestProto request, StreamObserver<OrganisationResponseProto> responseObserver) {
        responseObserver.onNext(organisationServiceGrpcHelper.getOrganisationByIdHelper(request));
        responseObserver.onCompleted();
    }

    @Override
    public void searchOrganisationPagination(SearchOrganisationPaginationRequestProto request, StreamObserver<SearchOrganisationPaginationResponseProto> responseObserver) {
        responseObserver.onNext(organisationServiceGrpcHelper.searchOrganisationPaginationHelper(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteOrganisationById(GetOrganisationRequestProto request, StreamObserver<DeleteOrganisationResponseProto> responseObserver) {
        responseObserver.onNext(organisationServiceGrpcHelper.deleteOrganisationByIdHelper(request.getId()));
        responseObserver.onCompleted();
    }

    @Override
    public void createOrganisation(CreateOrganisationRequestProto request, StreamObserver<OrganisationResponseProto> responseObserver) {
        responseObserver.onNext(organisationServiceGrpcHelper.createOrganisationHelper(request));
        responseObserver.onCompleted();
    }

    @Override
    public void updateOrganisation(UpdateOrganisationRequestProto request, StreamObserver<OrganisationResponseProto> responseObserver) {
        responseObserver.onNext(organisationServiceGrpcHelper.updateOrganisationHelper(request));
        responseObserver.onCompleted();
    }
}
