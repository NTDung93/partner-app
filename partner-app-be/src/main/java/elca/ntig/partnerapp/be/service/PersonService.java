package elca.ntig.partnerapp.be.service;

import elca.ntig.partnerapp.be.model.dto.person.*;

public interface PersonService {
    PersonResponseDto getPersonById(Integer id);
    SearchPeoplePaginationResponseDto searchPeoplePagination(int pageNo, int pageSize, String sortBy, String sortDir, SearchPeopleCriteriasDto criterias);
    PersonResponseDto createPerson(CreatePersonRequestDto createPersonRequestDto);
    PersonResponseDto updatePerson(UpdatePersonRequestDto updatePersonRequestDto);
}
