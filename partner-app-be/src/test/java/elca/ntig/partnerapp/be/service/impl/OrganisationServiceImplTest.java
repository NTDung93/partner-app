package elca.ntig.partnerapp.be.service.impl;

import elca.ntig.partnerapp.be.model.dto.address.AddressResponseDto;
import elca.ntig.partnerapp.be.model.dto.organisation.*;
import elca.ntig.partnerapp.be.model.entity.Organisation;
import elca.ntig.partnerapp.be.model.entity.Partner;
import elca.ntig.partnerapp.be.model.enums.addess.AddressType;
import elca.ntig.partnerapp.be.model.enums.common.Status;
import elca.ntig.partnerapp.be.model.exception.*;
import elca.ntig.partnerapp.be.repository.OrganisationRepository;
import elca.ntig.partnerapp.be.repository.PartnerRepository;
import elca.ntig.partnerapp.be.service.AddressService;
import elca.ntig.partnerapp.be.utils.mapper.OrganisationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrganisationServiceImplTest {
    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private OrganisationRepository organisationRepository;

    @Mock
    private OrganisationMapper organisationMapper;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private OrganisationServiceImpl organisationService;

    private CreateOrganisationRequestDto validCreateRequest;
    private UpdateOrganisationRequestDto validUpdateRequest;

    @BeforeEach
    void setUp() {
        validCreateRequest = CreateOrganisationRequestDto.builder()
                .name("New Organisation")
                .phoneNumber("0123456789")
                .ideNumber("CHE123456789")
                .creationDate(LocalDate.of(2020, 1, 1))
                .build();

        validUpdateRequest = UpdateOrganisationRequestDto.builder()
                .id(1)
                .name("Updated Organisation")
                .phoneNumber("0123456789")
                .ideNumber("CHE123456789")
                .creationDate(LocalDate.of(2020, 1, 1))
                .build();
    }

    // tests for getOrganisationById
    @Test
    void getOrganisationByIdSuccess() {
        // Arrange
        Organisation organisation = Organisation.builder()
                .id(1)
                .name("Organisation 1")
                .build();

        OrganisationResponseDto organisationResponseDto = OrganisationResponseDto.builder()
                .id(1)
                .name("Organisation 1")
                .build();

        when(organisationRepository.findById(1)).thenReturn(Optional.of(organisation));
        when(organisationMapper.toOrganisationResponseDto(organisation)).thenReturn(organisationResponseDto);

        // Act
        OrganisationResponseDto result = organisationService.getOrganisationById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Organisation 1", result.getName());
        verify(organisationRepository, times(1)).findById(1);
        verify(organisationMapper, times(1)).toOrganisationResponseDto(organisation);
    }

    @Test
    void getOrganisationByIdThrowsResourceNotFoundException() {
        // Arrange
        when(organisationRepository.findById(1)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            organisationService.getOrganisationById(1);
        });

        verify(organisationRepository, times(1)).findById(1);
        verify(organisationMapper, times(0)).toOrganisationResponseDto(any());
    }

    // tests for searchOrganisationPagination
    @Test
    void searchOrganisationPaginationSuccess() {
        // Arrange
        SearchOrganisationCriteriasDto criterias = new SearchOrganisationCriteriasDto();
        Page<Organisation> organisationPage = mock(Page.class);
        Organisation organisation = Organisation.builder()
                .id(1)
                .name("Organisation 1")
                .build();

        OrganisationResponseDto organisationResponseDto = OrganisationResponseDto.builder()
                .id(1)
                .name("Organisation 1")
                .build();

        when(organisationPage.getContent()).thenReturn(Arrays.asList(organisation));
        when(organisationPage.getNumber()).thenReturn(0);
        when(organisationPage.getSize()).thenReturn(10);
        when(organisationPage.getTotalPages()).thenReturn(1);
        when(organisationPage.getTotalElements()).thenReturn(1L);
        when(organisationPage.isLast()).thenReturn(true);

        when(organisationRepository.searchOrganisationPagination(criterias, PageRequest.of(0, 10, Sort.by("name").ascending())))
                .thenReturn(organisationPage);
        when(organisationMapper.toOrganisationResponseDto(organisation)).thenReturn(organisationResponseDto);

        // Act
        SearchOrganisationPaginationResponseDto result = organisationService.searchOrganisationPagination(0, 10, "name", "asc", criterias);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalRecords());
        assertEquals(1, result.getTotalPages());
        assertTrue(result.isLast());
        verify(organisationRepository, times(1)).searchOrganisationPagination(any(), any());
        verify(organisationMapper, times(1)).toOrganisationResponseDto(organisation);
    }

    // tests for createOrganisation
    @Test
    void createOrganisationSuccess() {
        // Arrange
        CreateOrganisationRequestDto createRequest = CreateOrganisationRequestDto.builder()
                .name("New Organisation")
                .phoneNumber("0123456789")
                .ideNumber("CHE123456789")
                .creationDate(LocalDate.of(2020, 1, 1))
                .addresses(null)
                .build();

        Partner partner = Partner.builder()
                .id(1)
                .phoneNumber("0123456789")
                .status(Status.ACTIVE)
                .build();

        Organisation organisation = Organisation.builder()
                .id(1)
                .name("New Organisation")
                .partner(partner)
                .build();

        OrganisationResponseDto organisationResponseDto = OrganisationResponseDto.builder()
                .id(1)
                .name("New Organisation")
                .build();

        when(partnerRepository.save(any(Partner.class))).thenReturn(partner);
        when(organisationRepository.save(any(Organisation.class))).thenReturn(organisation);
        when(organisationMapper.toOrganisationResponseDto(organisation)).thenReturn(organisationResponseDto);

        // Act
        OrganisationResponseDto result = organisationService.createOrganisation(createRequest);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("New Organisation", result.getName());

        // Use ArgumentMatchers for the partner object
        verify(partnerRepository, times(1)).save(any(Partner.class));
        verify(organisationRepository, times(1)).save(any(Organisation.class));
    }

    @Test
    void createOrganisationInvalidIDENumberFormat() {
        // Invalid IDE number format
        validCreateRequest.setIdeNumber("XYZ123456789");

        InvalidIDENumberFormatException thrown = assertThrows(InvalidIDENumberFormatException.class, () -> {
            organisationService.createOrganisation(validCreateRequest);
        });

        assertEquals("Invalid IDE number format", thrown.getMessage());
        verify(partnerRepository, never()).save(any(Partner.class));
        verify(organisationRepository, never()).save(any(Organisation.class));
        verify(addressService, never()).createAddressForPartner(any(), any());
    }

    @Test
    void createOrganisationExistingActiveIDENumber() {
        when(organisationRepository.findOrganisationByIdeNumberAndPartnerStatus(validCreateRequest.getIdeNumber(), Status.ACTIVE))
                .thenReturn(Organisation.builder().build());

        ExistingActiveIDENumberException thrown = assertThrows(ExistingActiveIDENumberException.class, () -> {
            organisationService.createOrganisation(validCreateRequest);
        });

        assertEquals("Dialog.err.message.ExistingActiveIDENumberException", thrown.getMessage());
        verify(partnerRepository, never()).save(any(Partner.class));
        verify(organisationRepository, never()).save(any(Organisation.class));
        verify(addressService, never()).createAddressForPartner(any(), any());
    }

    @Test
    void createOrganisationInvalidCreationDate() {
        // Invalid creation date (in the future)
        validCreateRequest.setCreationDate(LocalDate.now().plusDays(1));

        DateNotInThePastException thrown = assertThrows(DateNotInThePastException.class, () -> {
            organisationService.createOrganisation(validCreateRequest);
        });

        assertEquals("Date must be in the past", thrown.getMessage());
        verify(partnerRepository, never()).save(any(Partner.class));
        verify(organisationRepository, never()).save(any(Organisation.class));
        verify(addressService, never()).createAddressForPartner(any(), any());
    }

    @Test
    void createOrganisationInvalidPhoneNumber() {
        // Invalid phone number format
        validCreateRequest.setPhoneNumber("12345");

        InvalidPhoneNumberFormatException thrown = assertThrows(InvalidPhoneNumberFormatException.class, () -> {
            organisationService.createOrganisation(validCreateRequest);
        });

        assertEquals("Invalid phone number format", thrown.getMessage());
        verify(partnerRepository, never()).save(any(Partner.class));
        verify(organisationRepository, never()).save(any(Organisation.class));
        verify(addressService, never()).createAddressForPartner(any(), any());
    }

    // tests for updateOrganisation
    @Test
    void updateOrganisationSuccess() {
        // Arrange
        UpdateOrganisationRequestDto updateRequest = UpdateOrganisationRequestDto.builder()
                .id(1)
                .name("Updated Organisation")
                .phoneNumber("0123456789")
                .build();

        Partner partner = Partner.builder()
                .id(1)
                .phoneNumber("0123456789")
                .status(Status.ACTIVE)
                .build();

        Organisation organisation = Organisation.builder()
                .id(1)
                .name("Old Organisation")
                .partner(partner)
                .build();

        OrganisationResponseDto organisationResponseDto = OrganisationResponseDto.builder()
                .id(1)
                .name("Updated Organisation")
                .build();

        when(organisationRepository.findById(1)).thenReturn(java.util.Optional.of(organisation));
        when(organisationRepository.save(any(Organisation.class))).thenReturn(organisation);
        when(organisationMapper.toOrganisationResponseDto(organisation)).thenReturn(organisationResponseDto);

        // Act
        OrganisationResponseDto result = organisationService.updateOrganisation(updateRequest);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Updated Organisation", result.getName());
        verify(organisationRepository, times(1)).findById(1);
        verify(organisationRepository, times(1)).save(any(Organisation.class));
        verify(addressService, times(1)).updateAddressForPartner(partner, updateRequest.getAddresses());
    }

    @Test
    void updateOrganisationInvalidIDENumberFormat() {
        // Arrange
        Organisation organisation = Organisation.builder()
                .id(1)
                .name("Old Organisation")
                .build();

        when(organisationRepository.findById(1)).thenReturn(Optional.of(organisation));

        // Set an invalid IDE number in the update request
        validUpdateRequest.setIdeNumber("XYZ123456789");

        // Act & Assert
        InvalidIDENumberFormatException thrown = assertThrows(InvalidIDENumberFormatException.class, () -> {
            organisationService.updateOrganisation(validUpdateRequest);
        });

        assertEquals("Invalid IDE number format", thrown.getMessage());

        verify(organisationRepository, times(1)).findById(1);
        verify(organisationRepository, never()).save(any(Organisation.class)); // No save should occur
    }

    @Test
    void updateOrganisationExistingActiveIDENumber() {
        when(organisationRepository.findById(1)).thenReturn(Optional.of(Organisation.builder().id(1).build()));
        when(organisationRepository.findOrganisationByIdeNumberAndPartnerStatus(validUpdateRequest.getIdeNumber(), Status.ACTIVE))
                .thenReturn(Organisation.builder().id(2).build());

        ExistingActiveIDENumberException thrown = assertThrows(ExistingActiveIDENumberException.class, () -> {
            organisationService.updateOrganisation(validUpdateRequest);
        });

        assertEquals("Dialog.err.message.ExistingActiveIDENumberException", thrown.getMessage());
        verify(organisationRepository, never()).save(any(Organisation.class));
        verify(addressService, never()).updateAddressForPartner(any(), any());
    }

    @Test
    void updateOrganisationInvalidCreationDate() {
        // Arrange
        Organisation organisation = Organisation.builder()
                .id(1)
                .name("Old Organisation")
                .build();
        when(organisationRepository.findById(1)).thenReturn(Optional.of(organisation));

        validUpdateRequest.setCreationDate(LocalDate.now().plusDays(1));

        // Act
        DateNotInThePastException thrown = assertThrows(DateNotInThePastException.class, () -> {
            organisationService.updateOrganisation(validUpdateRequest);
        });

        assertEquals("Date must be in the past", thrown.getMessage());

        verify(organisationRepository, times(1)).findById(1);
        verify(organisationRepository, never()).save(any(Organisation.class)); // No save should occur
    }

    @Test
    void updateOrganisationInvalidPhoneNumber() {
        // Arrange
        Organisation organisation = Organisation.builder()
                .id(1)
                .name("Old Organisation")
                .build();

        when(organisationRepository.findById(1)).thenReturn(Optional.of(organisation));

        validUpdateRequest.setPhoneNumber("12345"); // Invalid format

        // Act & Assert: verify that the InvalidPhoneNumberFormatException is thrown
        InvalidPhoneNumberFormatException thrown = assertThrows(InvalidPhoneNumberFormatException.class, () -> {
            organisationService.updateOrganisation(validUpdateRequest);
        });

        assertEquals("Invalid phone number format", thrown.getMessage());

        verify(organisationRepository, times(1)).findById(1);
        verify(organisationRepository, never()).save(any(Organisation.class));
    }

    // tests for getOrganisationAlongWithAddress
    @Test
    void getOrganisationAlongWithAddressSuccess() {
        // Arrange
        Organisation organisation = Organisation.builder()
                .id(1)
                .name("Organisation 1")
                .partner(Partner.builder().id(1).build())
                .build();

        OrganisationResponseDto organisationResponseDto = OrganisationResponseDto.builder()
                .id(1)
                .name("Organisation 1")
                .build();

        AddressResponseDto addressResponseDto = AddressResponseDto.builder()
                .id(1)
                .category(AddressType.MAIN)
                .build();

        when(organisationRepository.findById(1)).thenReturn(java.util.Optional.of(organisation));
        when(organisationMapper.toOrganisationResponseDto(organisation)).thenReturn(organisationResponseDto);
        when(addressService.getAddressesByPartnerId(organisation.getPartner().getId())).thenReturn(Arrays.asList(addressResponseDto));

        // Act
        GetOrganisationAlongWithAddressResponseDto result = organisationService.getOrganisationAlongWithAddress(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getOrganisation().getId());
        assertEquals(1, result.getAddresses().size());
        verify(organisationRepository, times(1)).findById(1);
        verify(addressService, times(1)).getAddressesByPartnerId(organisation.getPartner().getId());
    }
}