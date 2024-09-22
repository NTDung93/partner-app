package elca.ntig.partnerapp.be.service;

import elca.ntig.partnerapp.be.model.dto.organisation.*;

public interface OrganisationService {
    OrganisationResponseDto getOrganisationById(Integer id);
    SearchOrganisationPaginationResponseDto searchOrganisationPagination(int pageNo, int pageSize, String sortBy, String sortDir, SearchOrganisationCriteriasDto criterias);
    OrganisationResponseDto createOrganisation(CreateOrganisationRequestDto createOrganisationRequestDto);
    OrganisationResponseDto updateOrganisation(UpdateOrganisationRequestDto updateOrganisationRequestDto);
}
