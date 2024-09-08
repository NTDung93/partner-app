package elca.ntig.partnerapp.be.service.impl;

import elca.ntig.partnerapp.be.mapper.PersonMapper;
import elca.ntig.partnerapp.be.model.dto.person.PersonResponse;
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
    public PersonResponse getPersonById(Integer id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person", "id", id));
        return personMapper.toPersonResponse(person);
    }
}
