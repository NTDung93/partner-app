package elca.ntig.partnerapp.be.service.impl;

import elca.ntig.partnerapp.be.model.dto.person.SearchPeopleCriteriasDto;
import elca.ntig.partnerapp.be.model.dto.person.SearchPeoplePaginationResponseDto;
import elca.ntig.partnerapp.be.utils.mapper.PersonMapper;
import elca.ntig.partnerapp.be.model.dto.person.PersonResponseDto;
import elca.ntig.partnerapp.be.model.entity.Person;
import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;
import elca.ntig.partnerapp.be.repository.PersonRepository;
import elca.ntig.partnerapp.be.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    public PersonResponseDto getPersonById(Integer id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person", "id", id));
        return personMapper.toPersonResponseDto(person);
    }

    @Override
    public SearchPeoplePaginationResponseDto searchPeoplePagination(int pageNo, int pageSize, String sortBy, String sortDir, SearchPeopleCriteriasDto criterias) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Person> people = personRepository.searchPeoplePagination(criterias, pageable);

        //get content of page
        List<Person> peopleList = people.getContent();

        //format the response
        List<PersonResponseDto> content = peopleList.stream().map(person -> personMapper.toPersonResponseDto(person)).collect(Collectors.toList());
        return SearchPeoplePaginationResponseDto.builder()
                .pageNo(people.getNumber())
                .pageSize(people.getSize())
                .totalPages(people.getTotalPages())
                .totalRecords(people.getTotalElements())
                .last(people.isLast())
                .content(content)
                .build();
    }
}
