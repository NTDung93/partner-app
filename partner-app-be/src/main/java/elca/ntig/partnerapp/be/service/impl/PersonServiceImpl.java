package elca.ntig.partnerapp.be.service.impl;

import elca.ntig.partnerapp.be.utils.mapper.PersonMapper;
import elca.ntig.partnerapp.be.model.dto.person.PersonResponseDto;
import elca.ntig.partnerapp.be.model.entity.Person;
import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;
import elca.ntig.partnerapp.be.repository.PersonRepository;
import elca.ntig.partnerapp.be.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    public PersonResponseDto getPersonById(Integer id) {
//        Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person", "id", id));
        Person person = personRepository.findById(id).get();
        return personMapper.toPersonResponseDto(person);
    }
}
