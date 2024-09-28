package elca.ntig.partnerapp.be.mappingservice;

import elca.ntig.partnerapp.common.proto.entity.organisation.*;

public interface OrganisationMappingService {
    OrganisationResponseProto getOrganisationByIdHelper(GetOrganisationRequestProto request);
    SearchOrganisationPaginationResponseProto searchOrganisationPaginationHelper(SearchOrganisationPaginationRequestProto request);
    DeleteOrganisationResponseProto deleteOrganisationByIdHelper(Integer id);
    OrganisationResponseProto createOrganisationHelper(CreateOrganisationRequestProto request);
    OrganisationResponseProto updateOrganisationHelper(UpdateOrganisationRequestProto request);
    GetOrganisationAlongWithAddressResponseProto getOrganisationAlongWithAddressHelper(Integer id);
}
