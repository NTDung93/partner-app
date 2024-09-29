package elca.ntig.partnerapp.be.mappingservice;

import elca.ntig.partnerapp.common.proto.entity.person.*;

public interface PersonMappingService {
    PersonResponseProto getPersonByIdHelper(GetPersonRequestProto request);
    SearchPeoplePaginationResponseProto searchPeoplePaginationHelper(SearchPeoplePaginationRequestProto request);
    DeletePersonResponseProto deletePersonByIdHelper(Integer id);
    PersonResponseProto createPersonHelper(CreatePersonRequestProto request);
    PersonResponseProto updatePersonHelper(UpdatePersonRequestProto request);
    GetPersonAlongWithAddressResponseProto getPersonAlongWithAddressHelper(Integer id);
}
