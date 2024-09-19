package elca.ntig.partnerapp.be.service;

import elca.ntig.partnerapp.be.model.dto.organisation.OrganisationResponseDto;
import elca.ntig.partnerapp.be.model.dto.organisation.SearchOrganisationCriteriasDto;
import elca.ntig.partnerapp.be.model.dto.organisation.SearchOrganisationPaginationResponseDto;

public interface OrganisationService {
    OrganisationResponseDto getOrganisationById(Integer id);
    SearchOrganisationPaginationResponseDto searchOrganisationPagination(int pageNo, int pageSize, String sortBy, String sortDir, SearchOrganisationCriteriasDto criterias);
}
