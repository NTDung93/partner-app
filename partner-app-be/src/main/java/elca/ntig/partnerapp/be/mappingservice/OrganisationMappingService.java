package elca.ntig.partnerapp.be.mappingservice;

import elca.ntig.partnerapp.common.proto.entity.organisation.*;

public interface OrganisationMappingService {
    OrganisationResponseProto getOrganisationByIdHelper(GetOrganisationRequestProto request);
    SearchOrganisationPaginationResponseProto searchOrganisationPaginationHelper(SearchOrganisationPaginationRequestProto request);
    DeleteOrganisationResponseProto deleteOrganisationById(Integer id);
}
