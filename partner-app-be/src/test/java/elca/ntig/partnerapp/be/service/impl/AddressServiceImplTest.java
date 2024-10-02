package elca.ntig.partnerapp.be.service.impl;

import elca.ntig.partnerapp.be.model.dto.address.AddressResponseDto;
import elca.ntig.partnerapp.be.model.dto.address.CreateAddressRequestDto;
import elca.ntig.partnerapp.be.model.entity.Address;
import elca.ntig.partnerapp.be.model.entity.Partner;
import elca.ntig.partnerapp.be.model.enums.addess.AddressType;
import elca.ntig.partnerapp.be.model.enums.common.Status;
import elca.ntig.partnerapp.be.model.enums.partner.Language;
import elca.ntig.partnerapp.be.model.exception.EndDateBeforeStartDateException;
import elca.ntig.partnerapp.be.model.exception.OverlapPeriodException;
import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;
import elca.ntig.partnerapp.be.repository.AddressRepository;
import elca.ntig.partnerapp.be.repository.PartnerRepository;
import elca.ntig.partnerapp.be.utils.mapper.AddressMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {
    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Partner partner01;
    private Partner partner02;
    private CreateAddressRequestDto address01;
    private CreateAddressRequestDto address02;

    @BeforeEach
    void setUp() {
        partner01 = Partner.builder()
                .id(1)
                .language(Language.LANG_ENGLISH)
                .phoneNumber("0123456789")
                .status(Status.ACTIVE)
                .build();

        partner02 = Partner.builder()
                .id(2)
                .language(Language.LANG_DEUTSCH)
                .phoneNumber("0123456229")
                .status(Status.INACTIVE)
                .build();

        address01 = CreateAddressRequestDto.builder()
                .partnerId(partner01.getId())
                .category(AddressType.MAIN)
                .validityStart(LocalDate.of(2021, 1, 1))
                .validityEnd(LocalDate.of(2021, 5, 3))
                .build();

        address02 = CreateAddressRequestDto.builder()
                .partnerId(partner01.getId())
                .category(AddressType.MAIN)
                .validityStart(LocalDate.of(2023, 2, 15))
                .validityEnd(LocalDate.of(2023, 7, 23))
                .build();
    }

    @Test
    void createAddressForPartnerSuccessfully() {
        List<CreateAddressRequestDto> addresses = Arrays.asList(address01, address02);

        Address mappedAddress01 = Address.builder()
                .partner(partner01)
                .category(address01.getCategory())
                .validityStart(address01.getValidityStart())
                .validityEnd(address01.getValidityEnd())
                .status(Status.ACTIVE)
                .build();

        Address mappedAddress02 = Address.builder()
                .partner(partner01)
                .category(address02.getCategory())
                .validityStart(address02.getValidityStart())
                .validityEnd(address02.getValidityEnd())
                .status(Status.ACTIVE)
                .build();

        when(addressMapper.toAddressFromCreateAddressRequestDto(address01)).thenReturn(mappedAddress01);
        when(addressMapper.toAddressFromCreateAddressRequestDto(address02)).thenReturn(mappedAddress02);

        // Act
        addressService.createAddressForPartner(partner01, addresses);

        // Assert
        verify(addressMapper, times(1)).toAddressFromCreateAddressRequestDto(address01);
        verify(addressMapper, times(1)).toAddressFromCreateAddressRequestDto(address02);
        verify(addressRepository, times(1)).saveAll(Arrays.asList(mappedAddress01, mappedAddress02));
    }

    @Test
    void createAddressForPartnerThrowsOverlapPeriodException() {
        // Arrange
        CreateAddressRequestDto addressOverlap01 = CreateAddressRequestDto.builder()
                .partnerId(partner01.getId())
                .category(AddressType.MAIN)
                .validityStart(LocalDate.of(2021, 1, 1))
                .validityEnd(LocalDate.of(2021, 5, 3))
                .build();

        CreateAddressRequestDto addressOverlap02 = CreateAddressRequestDto.builder()
                .partnerId(partner01.getId())
                .category(AddressType.MAIN)
                .validityStart(LocalDate.of(2021, 4, 1))
                .validityEnd(LocalDate.of(2021, 6, 1))
                .build();

        List<CreateAddressRequestDto> addresses = Arrays.asList(addressOverlap01, addressOverlap02);

        // Act
        assertThrows(OverlapPeriodException.class, () -> {
            addressService.createAddressForPartner(partner01, addresses);
        });

        // Assert
        verify(addressRepository, times(0)).saveAll(any());
    }

    @Test
    void createAddressForPartner_ThrowsEndDateBeforeStartDateException() {
        // Arrange: address with end date before start date
        CreateAddressRequestDto addressInvalidDates = CreateAddressRequestDto.builder()
                .partnerId(partner01.getId())
                .category(AddressType.MAIN)
                .validityStart(LocalDate.of(2021, 5, 3))
                .validityEnd(LocalDate.of(2021, 1, 1)) // End date before start date
                .build();

        List<CreateAddressRequestDto> addresses = Arrays.asList(addressInvalidDates);

        // Act & Assert
        assertThrows(EndDateBeforeStartDateException.class, () -> {
            addressService.createAddressForPartner(partner01, addresses);
        });

        verify(addressRepository, times(0)).saveAll(any());
    }

    @Test
    void getAddressesByPartnerIdSuccess() {
        // Arrange
        Address address01 = Address.builder()
                .id(1)
                .partner(partner01)
                .category(AddressType.MAIN)
                .validityStart(LocalDate.of(2021, 1, 1))
                .validityEnd(LocalDate.of(2021, 5, 3))
                .status(Status.ACTIVE)
                .build();

        Address address02 = Address.builder()
                .id(2)
                .partner(partner01)
                .category(AddressType.MAIN)
                .validityStart(LocalDate.of(2023, 2, 15))
                .validityEnd(LocalDate.of(2023, 7, 23))
                .status(Status.ACTIVE)
                .build();

        List<Address> addresses = Arrays.asList(address01, address02);

        AddressResponseDto responseDto01 = AddressResponseDto.builder()
                .id(1)
                .category(AddressType.MAIN)
                .validityStart(LocalDate.of(2021, 1, 1))
                .validityEnd(LocalDate.of(2021, 5, 3))
                .build();

        AddressResponseDto responseDto02 = AddressResponseDto.builder()
                .id(2)
                .category(AddressType.MAIN)
                .validityStart(LocalDate.of(2023, 2, 15))
                .validityEnd(LocalDate.of(2023, 7, 23))
                .build();

        when(addressRepository.findByPartnerId(partner01.getId())).thenReturn(addresses);
        when(addressMapper.toAddressResponseDto(address01)).thenReturn(responseDto01);
        when(addressMapper.toAddressResponseDto(address02)).thenReturn(responseDto02);

        // Act
        List<AddressResponseDto> result = addressService.getAddressesByPartnerId(partner01.getId());

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(addressRepository, times(1)).findByPartnerId(partner01.getId());
        verify(addressMapper, times(1)).toAddressResponseDto(address01);
        verify(addressMapper, times(1)).toAddressResponseDto(address02);
    }

    @Test
    void getAddressesByPartnerIdNoAddressesFound() {
        // Arrange
        when(addressRepository.findByPartnerId(partner01.getId())).thenReturn(Collections.emptyList());

        // Act
        List<AddressResponseDto> result = addressService.getAddressesByPartnerId(partner01.getId());

        // Assert
        assertNull(result);
        verify(addressRepository, times(1)).findByPartnerId(partner01.getId());
        verify(addressMapper, times(0)).toAddressResponseDto(any());
    }

    @Test
    void updateAddressForPartnerSuccessfully() {
        // Arrange
        AddressResponseDto updateAddressRequest = AddressResponseDto.builder()
                .id(1)
                .category(AddressType.MAIN)
                .validityStart(LocalDate.of(2021, 1, 1))
                .validityEnd(LocalDate.of(2021, 5, 3))
                .status(Status.ACTIVE)
                .build();

        Address updateAddress = Address.builder()
                .id(1)
                .partner(partner01)
                .category(AddressType.MAIN)
                .validityStart(LocalDate.of(2021, 1, 1))
                .validityEnd(LocalDate.of(2021, 5, 3))
                .status(Status.ACTIVE)
                .build();

        when(addressRepository.findById(1)).thenReturn(Optional.of(updateAddress));
        doNothing().when(addressMapper).updateExistingAddress(updateAddressRequest, updateAddress);

        // Act
        addressService.updateAddressForPartner(partner01, Arrays.asList(updateAddressRequest));

        // Assert
        verify(addressRepository, times(1)).findById(1);
        verify(addressMapper, times(1)).updateExistingAddress(updateAddressRequest, updateAddress);
        verify(addressRepository, times(1)).save(updateAddress);
    }

    @Test
    void updateAddressForPartnerThrowsResourceNotFoundException() {
        // Arrange
        AddressResponseDto addressResponseDto = AddressResponseDto.builder()
                .id(-1) // non-existent address ID
                .category(AddressType.MAIN)
                .validityStart(LocalDate.of(2021, 1, 1))
                .validityEnd(LocalDate.of(2021, 5, 3))
                .status(Status.ACTIVE)
                .build();

        when(addressRepository.findById(-1)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            addressService.updateAddressForPartner(partner01, Arrays.asList(addressResponseDto));
        });

        verify(addressRepository, times(1)).findById(-1);
        verify(addressRepository, times(0)).save(any());
    }

    @Test
    void updateAddressForPartnerThrowsEndDateBeforeStartDateException() {
        // Arrange
        AddressResponseDto addressResponseDto = AddressResponseDto.builder()
                .id(1)
                .category(AddressType.MAIN)
                .validityStart(LocalDate.of(2021, 5, 3))
                .validityEnd(LocalDate.of(2021, 1, 1)) // End date before start date
                .status(Status.ACTIVE)
                .build();

        // Act & Assert
        assertThrows(EndDateBeforeStartDateException.class, () -> {
            addressService.updateAddressForPartner(partner01, Arrays.asList(addressResponseDto));
        });

        verify(addressRepository, times(0)).save(any());
    }

    @Test
    void updateAddressForPartnerThrowsOverlapPeriodException() {
        // Arrange
        AddressResponseDto addressResponseDto1 = AddressResponseDto.builder()
                .id(1)
                .category(AddressType.MAIN)
                .validityStart(LocalDate.of(2021, 1, 1))
                .validityEnd(LocalDate.of(2021, 5, 3))
                .status(Status.ACTIVE)
                .build();

        AddressResponseDto addressResponseDto2 = AddressResponseDto.builder()
                .id(2)
                .category(AddressType.MAIN)
                .validityStart(LocalDate.of(2021, 3, 1))
                .validityEnd(LocalDate.of(2021, 6, 1))
                .status(Status.ACTIVE)
                .build();

        // Act & Assert
        assertThrows(OverlapPeriodException.class, () -> {
            addressService.updateAddressForPartner(partner01, Arrays.asList(addressResponseDto1, addressResponseDto2));
        });

        verify(addressRepository, times(0)).save(any());
    }
}