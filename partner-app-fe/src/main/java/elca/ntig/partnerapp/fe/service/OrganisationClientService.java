package elca.ntig.partnerapp.fe.service;

import elca.ntig.partnerapp.common.proto.entity.organisation.*;
import elca.ntig.partnerapp.common.proto.entity.person.DeletePersonResponseProto;
import elca.ntig.partnerapp.common.proto.entity.person.GetPersonRequestProto;
import elca.ntig.partnerapp.fe.callback.organisation.DeleteOrganisationCallback;
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

    public DeleteOrganisationResponseProto deleteOrganisationById(GetOrganisationRequestProto request) {
        return organisationServiceStub.deleteOrganisationById(request);
    }

    public OrganisationResponseProto createOrganisation(CreateOrganisationRequestProto request) {
        return organisationServiceStub.createOrganisation(request);
    }

    public OrganisationResponseProto updateOrganisation(UpdateOrganisationRequestProto request) {
        return organisationServiceStub.updateOrganisation(request);
    }
}
