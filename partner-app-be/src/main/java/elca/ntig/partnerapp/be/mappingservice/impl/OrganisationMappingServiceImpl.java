package elca.ntig.partnerapp.be.mappingservice.impl;

import elca.ntig.partnerapp.be.mappingservice.OrganisationMappingService;
import elca.ntig.partnerapp.be.model.dto.organisation.SearchOrganisationCriteriasDto;
import elca.ntig.partnerapp.be.model.dto.organisation.SearchOrganisationPaginationResponseDto;
import elca.ntig.partnerapp.be.model.dto.partner.DeletePartnerResponseDto;
import elca.ntig.partnerapp.be.service.OrganisationService;
import elca.ntig.partnerapp.be.service.PartnerService;
import elca.ntig.partnerapp.be.utils.mapper.OrganisationMapper;
import elca.ntig.partnerapp.be.utils.validator.ArgumentValidator;
import elca.ntig.partnerapp.common.proto.entity.organisation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrganisationMappingServiceImpl implements OrganisationMappingService {
    private final OrganisationMapper organisationMapper;
    private final ArgumentValidator argumentValidator;
    private final OrganisationService organisationService;
    private final PartnerService partnerService;


    @Override
    public OrganisationResponseProto getOrganisationByIdHelper(GetOrganisationRequestProto request) {
        return organisationMapper.toOrganisationResponseProto(organisationService.getOrganisationById(request.getId()));
    }

    @Override
    public SearchOrganisationPaginationResponseProto searchOrganisationPaginationHelper(SearchOrganisationPaginationRequestProto request) {
        SearchOrganisationCriteriasDto searchOrganisationCriterias = organisationMapper.toSearchOrganisationCriteriasDto(request.getCriterias());
        argumentValidator.validate(searchOrganisationCriterias);
        SearchOrganisationPaginationResponseDto searchOrganisationPaginationResponseDto = organisationService.searchOrganisationPagination(request.getPageNo(), request.getPageSize(), request.getSortBy(), request.getSortDir(), searchOrganisationCriterias);
        return organisationMapper.toSearchOrganisationPaginationResponse(searchOrganisationPaginationResponseDto);
    }

    @Override
    public DeleteOrganisationResponseProto deleteOrganisationById(Integer id) {
        DeletePartnerResponseDto deletePartnerResponseDto = partnerService.deletePartnerById(id);
        return organisationMapper.toDeleteOrganisationResponseProto(deletePartnerResponseDto);
    }
}
