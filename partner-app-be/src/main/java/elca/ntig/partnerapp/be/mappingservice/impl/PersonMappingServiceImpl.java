package elca.ntig.partnerapp.be.mappingservice.impl;

import elca.ntig.partnerapp.be.mappingservice.PersonMappingService;
import elca.ntig.partnerapp.be.model.dto.person.SearchPeopleCriteriasDto;
import elca.ntig.partnerapp.be.model.dto.person.SearchPeoplePaginationResponseDto;
import elca.ntig.partnerapp.be.service.PersonService;
import elca.ntig.partnerapp.be.utils.mapper.PersonMapper;
import elca.ntig.partnerapp.be.utils.validator.ArgumentValidator;
import elca.ntig.partnerapp.common.proto.entity.person.GetPersonRequestProto;
import elca.ntig.partnerapp.common.proto.entity.person.PersonResponseProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationRequestProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationResponseProto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonMappingServiceImpl implements PersonMappingService {
    private final PersonMapper personMapper;
    private final ArgumentValidator argumentValidator;
    private final PersonService personService;

    @Override
    public PersonResponseProto getPersonByIdHelper(GetPersonRequestProto request){
        return personMapper.toPersonResponseProto(personService.getPersonById(request.getId()));
    }

    @Override
    public SearchPeoplePaginationResponseProto searchPeoplePaginationHelper(SearchPeoplePaginationRequestProto request){
        SearchPeopleCriteriasDto searchPeopleCriterias = personMapper.toSearchPeopleCriteriasDto(request.getCriterias());
        argumentValidator.validate(searchPeopleCriterias);
        SearchPeoplePaginationResponseDto searchPeoplePaginationResponseDto = personService.searchPeoplePagination(request.getPageNo(), request.getPageSize(), request.getSortBy(), request.getSortDir(), searchPeopleCriterias);
        return personMapper.toSearchPeoplePaginationResponse(searchPeoplePaginationResponseDto);
    }
}
