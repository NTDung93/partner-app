package elca.ntig.partnerapp.be.service;

import elca.ntig.partnerapp.be.model.dto.person.PersonResponseDto;
import elca.ntig.partnerapp.be.model.dto.person.SearchPeopleCriteriasDto;
import elca.ntig.partnerapp.be.model.dto.person.SearchPeoplePaginationResponseDto;

public interface PersonService {
    PersonResponseDto getPersonById(Integer id);
    SearchPeoplePaginationResponseDto searchPeoplePagination(int pageNo, int pageSize, String sortBy, String sortDir, SearchPeopleCriteriasDto criterias);
}
