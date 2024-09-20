package elca.ntig.partnerapp.fe.service;

import elca.ntig.partnerapp.common.proto.entity.organisation.OrganisationServiceGrpc;
import elca.ntig.partnerapp.common.proto.entity.organisation.SearchOrganisationPaginationRequestProto;
import elca.ntig.partnerapp.common.proto.entity.organisation.SearchOrganisationPaginationResponseProto;
import io.grpc.ManagedChannel;

public class OrganisationClientService {
    private final OrganisationServiceGrpc.OrganisationServiceBlockingStub organisationServiceStub;
    private final ManagedChannel channel;

    public OrganisationClientService(ManagedChannel managedChannel){
        this.channel = managedChannel;
        this.organisationServiceStub = OrganisationServiceGrpc.newBlockingStub(channel);
    }

    public SearchOrganisationPaginationResponseProto searchOrganisationPagination(SearchOrganisationPaginationRequestProto request) {
        return organisationServiceStub.searchOrganisationPagination(request);
    }
}
