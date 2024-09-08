package elca.ntig.partnerapp.be.service;

import elca.ntig.partnerapp.be.model.dto.person.PersonResponseDto;

public interface PersonService {
    PersonResponseDto getPersonById(Integer id);
}
