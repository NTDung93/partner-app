package elca.ntig.partnerapp.be.service;

import elca.ntig.partnerapp.be.model.dto.person.PersonResponse;

public interface PersonService {
    PersonResponse getPersonById(Integer id);
}
