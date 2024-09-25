package elca.ntig.partnerapp.be.service.impl;

import elca.ntig.partnerapp.be.model.dto.organisation.*;
import elca.ntig.partnerapp.be.model.entity.Organisation;
import elca.ntig.partnerapp.be.model.entity.Partner;
import elca.ntig.partnerapp.be.model.enums.common.Status;
import elca.ntig.partnerapp.be.model.exception.*;
import elca.ntig.partnerapp.be.repository.OrganisationRepository;
import elca.ntig.partnerapp.be.repository.PartnerRepository;
import elca.ntig.partnerapp.be.service.OrganisationService;
import elca.ntig.partnerapp.be.utils.mapper.OrganisationMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganisationServiceImpl implements OrganisationService {
    private final PartnerRepository partnerRepository;
    private final OrganisationRepository organisationRepository;
    private final OrganisationMapper OrganisationMapper;

    @Override
    public OrganisationResponseDto getOrganisationById(Integer id) {
        Organisation organisation = organisationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Organisation", "id", id));
        return OrganisationMapper.toOrganisationResponseDto(organisation);
    }

    @Override
    public SearchOrganisationPaginationResponseDto searchOrganisationPagination(int pageNo, int pageSize, String sortBy, String sortDir, SearchOrganisationCriteriasDto criterias) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Organisation> organisation = organisationRepository.searchOrganisationPagination(criterias, pageable);
        List<Organisation> organisationList = organisation.getContent();

        List<OrganisationResponseDto> content = organisationList.stream().map(org -> OrganisationMapper.toOrganisationResponseDto(org)).collect(Collectors.toList());
        return SearchOrganisationPaginationResponseDto.builder()
                .pageNo(organisation.getNumber())
                .pageSize(organisation.getSize())
                .totalPages(organisation.getTotalPages())
                .totalRecords(organisation.getTotalElements())
                .last(organisation.isLast())
                .content(content)
                .build();
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public OrganisationResponseDto createOrganisation(CreateOrganisationRequestDto createOrganisationRequestDto) {
        validateCreateRequest(createOrganisationRequestDto);

        Partner partner = Partner.builder()
                .language(createOrganisationRequestDto.getLanguage())
                .phoneNumber(createOrganisationRequestDto.getPhoneNumber())
                .status(Status.ACTIVE)
                .build();
        partnerRepository.save(partner);

        Organisation organisation = Organisation.builder()
                .id(partner.getId())
                .name(createOrganisationRequestDto.getName())
                .additionalName(createOrganisationRequestDto.getAdditionalName())
                .ideNumber(createOrganisationRequestDto.getIdeNumber())
                .codeNoga(createOrganisationRequestDto.getCodeNoga())
                .legalStatus(createOrganisationRequestDto.getLegalStatus())
                .creationDate(createOrganisationRequestDto.getCreationDate())
                .partner(partner)
                .build();
        organisation = organisationRepository.save(organisation);

        return OrganisationMapper.toOrganisationResponseDto(organisation);
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public OrganisationResponseDto updateOrganisation(UpdateOrganisationRequestDto updateOrganisationRequestDto) {
        Organisation organisation = organisationRepository.findById(updateOrganisationRequestDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Organisation", "id", updateOrganisationRequestDto.getId()));

        validateUpdateRequest(updateOrganisationRequestDto);

        OrganisationMapper.updateOrganisation(updateOrganisationRequestDto, organisation);
        organisation = organisationRepository.save(organisation);

        return OrganisationMapper.toOrganisationResponseDto(organisation);
    }

    private void validateCreateRequest(CreateOrganisationRequestDto createOrganisationRequestDto) {
        validateIDENumber(createOrganisationRequestDto.getIdeNumber());
        validateCreationDate(createOrganisationRequestDto.getCreationDate());
        validatePhoneNumber(createOrganisationRequestDto.getPhoneNumber());
    }

    private void validateUpdateRequest(UpdateOrganisationRequestDto updateOrganisationRequestDto) {
        validateUpdateIDENumber(updateOrganisationRequestDto.getIdeNumber(), updateOrganisationRequestDto.getId());
        validateCreationDate(updateOrganisationRequestDto.getCreationDate());
        validatePhoneNumber(updateOrganisationRequestDto.getPhoneNumber());
    }

    private void validateUpdateIDENumber(String ideNumber, Integer id) {
        if (StringUtils.isNotBlank(ideNumber)) {
            String plainIde = ideNumber.trim().replaceAll("[-]", "");
            String ideNumberRegex = "^(ADM|CHE)\\d{9}$";
            if (!plainIde.matches(ideNumberRegex)) {
                throw new InvalidIDENumberFormatException("Invalid IDE number format");
            }

            Organisation checkIdePerson = organisationRepository.findOrganisationByIdeNumberAndPartnerStatus(ideNumber, Status.ACTIVE);
            if (checkIdePerson != null && !checkIdePerson.getId().equals(id)) {
                throw new ExistingActiveIDENumberException("Organisation with IDE number " + ideNumber + " already exists");
            }
        }
    }

    private void validateIDENumber(String ideNumber) {
        if (StringUtils.isNotBlank(ideNumber)) {
            String plainIde = ideNumber.trim().replaceAll("[-]", "");
            String ideNumberRegex = "^(ADM|CHE)\\d{9}$";
            if (!plainIde.matches(ideNumberRegex)) {
                throw new InvalidIDENumberFormatException("Invalid IDE number format");
            }

            Organisation checkIdePerson = organisationRepository.findOrganisationByIdeNumberAndPartnerStatus(ideNumber, Status.ACTIVE);
            if (checkIdePerson != null) {
                throw new ExistingActiveIDENumberException("Organisation with IDE number " + ideNumber + " already exists");
            }
        }
    }

    private void validateCreationDate(LocalDate creationDate) {
        if ((creationDate != null) && (!creationDate.isBefore(LocalDate.now()))) {
            throw new DateNotInThePastException("Date must be in the past");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (StringUtils.isNotBlank(phoneNumber)) {
            String regex = "^\\d{10}$";
            if (!phoneNumber.matches(regex)) {
                throw new InvalidPhoneNumberFormatException("Invalid phone number format");
            }
        }
    }
}
