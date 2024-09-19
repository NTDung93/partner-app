package elca.ntig.partnerapp.be.mappingservice;

import elca.ntig.partnerapp.be.model.dto.organisation.OrganisationResponseDto;
import elca.ntig.partnerapp.be.model.dto.organisation.SearchOrganisationPaginationResponseDto;
import elca.ntig.partnerapp.common.proto.entity.organisation.GetOrganisationRequestProto;
import elca.ntig.partnerapp.common.proto.entity.organisation.OrganisationResponseProto;
import elca.ntig.partnerapp.common.proto.entity.organisation.SearchOrganisationPaginationRequestProto;
import elca.ntig.partnerapp.common.proto.entity.organisation.SearchOrganisationPaginationResponseProto;
import elca.ntig.partnerapp.common.proto.entity.person.GetPersonRequestProto;
import elca.ntig.partnerapp.common.proto.entity.person.PersonResponseProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationRequestProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationResponseProto;

public interface OrganisationMappingService {
    OrganisationResponseProto getOrganisationByIdHelper(GetOrganisationRequestProto request);
    SearchOrganisationPaginationResponseProto searchOrganisationPaginationHelper(SearchOrganisationPaginationRequestProto request);
}
