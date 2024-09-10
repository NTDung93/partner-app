package elca.ntig.partnerapp.be.repository.custom;

import elca.ntig.partnerapp.be.model.dto.person.SearchPeopleCriteriasDto;
import elca.ntig.partnerapp.be.model.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonRepositoryCustom {
    Page<Person> searchPeoplePagination(SearchPeopleCriteriasDto criterias, Pageable pageable);
}
