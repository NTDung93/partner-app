package elca.ntig.partnerapp.be.mappingservice.impl;

import elca.ntig.partnerapp.be.mappingservice.PersonMappingService;
import elca.ntig.partnerapp.be.model.dto.partner.DeletePartnerResponseDto;
import elca.ntig.partnerapp.be.model.dto.person.CreatePersonRequestDto;
import elca.ntig.partnerapp.be.model.dto.person.SearchPeopleCriteriasDto;
import elca.ntig.partnerapp.be.model.dto.person.SearchPeoplePaginationResponseDto;
import elca.ntig.partnerapp.be.model.dto.person.UpdatePersonRequestDto;
import elca.ntig.partnerapp.be.service.PartnerService;
import elca.ntig.partnerapp.be.service.PersonService;
import elca.ntig.partnerapp.be.utils.mapper.PersonMapper;
import elca.ntig.partnerapp.be.utils.validator.ArgumentValidator;
import elca.ntig.partnerapp.common.proto.entity.person.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonMappingServiceImpl implements PersonMappingService {
    private final ArgumentValidator argumentValidator;
    private final PersonService personService;
    private final PartnerService partnerService;

    @Override
    public PersonResponseProto getPersonByIdHelper(GetPersonRequestProto request){
        return PersonMapper.INSTANCE.toPersonResponseProto(personService.getPersonById(request.getId()));
    }

    @Override
    public SearchPeoplePaginationResponseProto searchPeoplePaginationHelper(SearchPeoplePaginationRequestProto request){
        SearchPeopleCriteriasDto searchPeopleCriterias = PersonMapper.INSTANCE.toSearchPeopleCriteriasDto(request.getCriterias());
        argumentValidator.validate(searchPeopleCriterias);
        SearchPeoplePaginationResponseDto searchPeoplePaginationResponseDto = personService.searchPeoplePagination(request.getPageNo(), request.getPageSize(), request.getSortBy(), request.getSortDir(), searchPeopleCriterias);
        return PersonMapper.INSTANCE.toSearchPeoplePaginationResponse(searchPeoplePaginationResponseDto);
    }

    @Override
    public DeletePersonResponseProto deletePersonByIdHelper(Integer id) {
        DeletePartnerResponseDto deletePartnerResponseDto = partnerService.deletePartnerById(id);
        return PersonMapper.INSTANCE.toDeletePersonResponseProto(deletePartnerResponseDto);
    }

    @Override
    public PersonResponseProto createPersonHelper(CreatePersonRequestProto request) {
        CreatePersonRequestDto createPersonRequestDto = PersonMapper.INSTANCE.toCreatePersonRequestDto(request);
        argumentValidator.validate(createPersonRequestDto);
        return PersonMapper.INSTANCE.toPersonResponseProto(personService.createPerson(createPersonRequestDto));
    }

    @Override
    public PersonResponseProto updatePersonHelper(UpdatePersonRequestProto request) {
        UpdatePersonRequestDto updatePersonRequestDto = PersonMapper.INSTANCE.toUpdatePersonRequestDto(request);
        argumentValidator.validate(updatePersonRequestDto);
        return PersonMapper.INSTANCE.toPersonResponseProto(personService.updatePerson(updatePersonRequestDto));
    }
}
