package elca.ntig.partnerapp.be.mappingservice.impl;

import elca.ntig.partnerapp.be.mappingservice.OrganisationMappingService;
import elca.ntig.partnerapp.be.model.dto.organisation.CreateOrganisationRequestDto;
import elca.ntig.partnerapp.be.model.dto.organisation.SearchOrganisationCriteriasDto;
import elca.ntig.partnerapp.be.model.dto.organisation.SearchOrganisationPaginationResponseDto;
import elca.ntig.partnerapp.be.model.dto.organisation.UpdateOrganisationRequestDto;
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
    private final ArgumentValidator argumentValidator;
    private final OrganisationService organisationService;
    private final PartnerService partnerService;
    private final OrganisationMapper organisationMapper;

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
    public DeleteOrganisationResponseProto deleteOrganisationByIdHelper(Integer id) {
        DeletePartnerResponseDto deletePartnerResponseDto = partnerService.deletePartnerById(id);
        return organisationMapper.toDeleteOrganisationResponseProto(deletePartnerResponseDto);
    }

    @Override
    public OrganisationResponseProto createOrganisationHelper(CreateOrganisationRequestProto request) {
        CreateOrganisationRequestDto createOrganisationRequestDto = organisationMapper.toCreateOrganisationRequestDto(request);
        argumentValidator.validate(createOrganisationRequestDto);
        return organisationMapper.toOrganisationResponseProto(organisationService.createOrganisation(createOrganisationRequestDto));
    }

    @Override
    public OrganisationResponseProto updateOrganisationHelper(UpdateOrganisationRequestProto request) {
        UpdateOrganisationRequestDto updateOrganisationRequestDto = organisationMapper.toUpdateOrganisationRequestDto(request);
        argumentValidator.validate(updateOrganisationRequestDto);
        return organisationMapper.toOrganisationResponseProto(organisationService.updateOrganisation(updateOrganisationRequestDto));
    }

    @Override
    public GetOrganisationAlongWithAddressResponseProto getOrganisationAlongWithAddressHelper(Integer id) {
        return organisationMapper.toGetOrganisationAlongWithAddressResponseProto(organisationService.getOrganisationAlongWithAddress(id));
    }
}
