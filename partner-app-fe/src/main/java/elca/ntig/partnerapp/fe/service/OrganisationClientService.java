package elca.ntig.partnerapp.fe.service;

import elca.ntig.partnerapp.common.proto.entity.organisation.*;
import io.grpc.ManagedChannel;

public class OrganisationClientService {
    private final OrganisationServiceGrpc.OrganisationServiceBlockingStub organisationServiceStub;
    private final ManagedChannel channel;

    public OrganisationClientService(ManagedChannel managedChannel){
        this.channel = managedChannel;
        this.organisationServiceStub = OrganisationServiceGrpc.newBlockingStub(channel);
    }

    public OrganisationResponseProto getOrganisationById(GetOrganisationRequestProto request) {
        return organisationServiceStub.getOrganisationById(request);
    }

    public SearchOrganisationPaginationResponseProto searchOrganisationPagination(SearchOrganisationPaginationRequestProto request) {
        return organisationServiceStub.searchOrganisationPagination(request);
    }

    public DeleteOrganisationResponseProto deleteOrganisationById(GetOrganisationRequestProto request) {
        return organisationServiceStub.deleteOrganisationById(request);
    }

    public OrganisationResponseProto createOrganisation(CreateOrganisationRequestProto request) throws Exception {
        return organisationServiceStub.createOrganisation(request);
    }

    public OrganisationResponseProto updateOrganisation(UpdateOrganisationRequestProto request) {
        return organisationServiceStub.updateOrganisation(request);
    }

    public GetOrganisationAlongWithAddressResponseProto getOrganisationAlongWithAddress(GetOrganisationRequestProto request) {
        return organisationServiceStub.getOrganisationAlongWithAddress(request);
    }
}
