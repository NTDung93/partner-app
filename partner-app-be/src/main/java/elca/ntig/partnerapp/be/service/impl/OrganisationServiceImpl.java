package elca.ntig.partnerapp.be.service.impl;

import elca.ntig.partnerapp.be.model.dto.organisation.OrganisationResponseDto;
import elca.ntig.partnerapp.be.model.dto.organisation.SearchOrganisationCriteriasDto;
import elca.ntig.partnerapp.be.model.dto.organisation.SearchOrganisationPaginationResponseDto;
import elca.ntig.partnerapp.be.model.entity.Organisation;
import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;
import elca.ntig.partnerapp.be.repository.OrganisationRepository;
import elca.ntig.partnerapp.be.service.OrganisationService;
import elca.ntig.partnerapp.be.utils.mapper.OrganisationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganisationServiceImpl implements OrganisationService {
    private final OrganisationRepository organisationRepository;
    private final OrganisationMapper organisationMapper;

    @Override
    public OrganisationResponseDto getOrganisationById(Integer id) {
        Organisation organisation = organisationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Organisation", "id", id));
        return organisationMapper.toOrganisationResponseDto(organisation);
    }

    @Override
    public SearchOrganisationPaginationResponseDto searchOrganisationPagination(int pageNo, int pageSize, String sortBy, String sortDir, SearchOrganisationCriteriasDto criterias) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Organisation> organisation = organisationRepository.searchOrganisationPagination(criterias, pageable);
        List<Organisation> organisationList = organisation.getContent();

        List<OrganisationResponseDto> content = organisationList.stream().map(org -> organisationMapper.toOrganisationResponseDto(org)).collect(Collectors.toList());
        return SearchOrganisationPaginationResponseDto.builder()
                .pageNo(organisation.getNumber())
                .pageSize(organisation.getSize())
                .totalPages(organisation.getTotalPages())
                .totalRecords(organisation.getTotalElements())
                .last(organisation.isLast())
                .content(content)
                .build();
    }
}
