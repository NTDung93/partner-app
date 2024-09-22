package elca.ntig.partnerapp.be.repository.custom;

import elca.ntig.partnerapp.be.model.dto.organisation.SearchOrganisationCriteriasDto;
import elca.ntig.partnerapp.be.model.entity.Organisation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrganisationRepositoryCustom {
    Page<Organisation> searchOrganisationPagination(SearchOrganisationCriteriasDto criterias, Pageable pageable);
}
