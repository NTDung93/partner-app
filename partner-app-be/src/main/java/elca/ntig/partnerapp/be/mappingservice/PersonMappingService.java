package elca.ntig.partnerapp.be.mappingservice;

import elca.ntig.partnerapp.common.proto.entity.person.GetPersonRequestProto;
import elca.ntig.partnerapp.common.proto.entity.person.PersonResponseProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationRequestProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationResponseProto;

public interface PersonMappingService {
    PersonResponseProto getPersonByIdHelper(GetPersonRequestProto request);
    SearchPeoplePaginationResponseProto searchPeoplePaginationHelper(SearchPeoplePaginationRequestProto request);
}
