package elca.ntig.partnerapp.be.service.impl;

import elca.ntig.partnerapp.be.model.entity.Address;
import elca.ntig.partnerapp.be.model.entity.Partner;
import elca.ntig.partnerapp.be.model.enums.common.Status;
import elca.ntig.partnerapp.be.model.enums.partner.Language;
import elca.ntig.partnerapp.be.model.exception.DeleteInactivePartnerException;
import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;
import elca.ntig.partnerapp.be.repository.AddressRepository;
import elca.ntig.partnerapp.be.repository.PartnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PartnerServiceImplTest {
    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private PartnerServiceImpl partnerService;

    private Partner partner01;
    private Partner partner02;
    private Address address01;
    private Address address02;

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

        address01 = Address.builder()
                .id(1)
                .partner(partner01)
                .status(Status.ACTIVE)
                .build();

        address02 = Address.builder()
                .id(2)
                .partner(partner01)
                .status(Status.INACTIVE)
                .build();
    }

    @Test
    void deletePartnerByIdSuccessFully() {
        // Arrange
        Integer partnerId = partner01.getId();
        when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(partner01));
        when(partnerRepository.save(partner01)).thenReturn(partner01);
        when(addressRepository.findByPartnerIdAndStatus(partnerId, Status.ACTIVE)).thenReturn(Arrays.asList(address01, address02));
        when(addressRepository.save(address01)).thenReturn(address01);
        when(addressRepository.save(address02)).thenReturn(address02);

        // Act
        partnerService.deletePartnerById(partnerId);

        // Assert
        assertEquals(Status.INACTIVE, partner01.getStatus());
        assertEquals(Status.INACTIVE, address01.getStatus());
        assertEquals(Status.INACTIVE, address02.getStatus());
    }

    @Test
    void deleteInactivePartnerThrowDeleteInactivePartnerException() {
        // Arrange
        Integer partnerId = partner02.getId();
        when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(partner02));

        // Act
        DeleteInactivePartnerException exception = assertThrows(DeleteInactivePartnerException.class, () -> {
            partnerService.deletePartnerById(partnerId);
        });

        // Assert
        assertEquals("Cannot delete inactive partner", exception.getMessage());
    }

    @Test
    void deleteNotFoundPartnerThrowResourceNotFoundException() {
        // Arrange
        Integer partnerId = -1;
        when(partnerRepository.findById(partnerId)).thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            partnerService.deletePartnerById(partnerId);
        });

        // Assert
        assertEquals("Partner with id: '-1' not found!", exception.getMessage());
    }
}