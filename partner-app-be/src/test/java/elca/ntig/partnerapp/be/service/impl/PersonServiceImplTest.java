package elca.ntig.partnerapp.be.service.impl;

import elca.ntig.partnerapp.be.model.dto.address.AddressResponseDto;
import elca.ntig.partnerapp.be.model.dto.person.*;
import elca.ntig.partnerapp.be.model.entity.Partner;
import elca.ntig.partnerapp.be.model.entity.Person;
import elca.ntig.partnerapp.be.model.enums.addess.AddressType;
import elca.ntig.partnerapp.be.model.enums.addess.CantonAbbr;
import elca.ntig.partnerapp.be.model.enums.addess.Country;
import elca.ntig.partnerapp.be.model.enums.common.Status;
import elca.ntig.partnerapp.be.model.enums.partner.Language;
import elca.ntig.partnerapp.be.model.enums.person.MaritalStatus;
import elca.ntig.partnerapp.be.model.enums.person.Nationality;
import elca.ntig.partnerapp.be.model.enums.person.SexEnum;
import elca.ntig.partnerapp.be.model.exception.DateNotInThePastException;
import elca.ntig.partnerapp.be.model.exception.InvalidAVSNumberFormatException;
import elca.ntig.partnerapp.be.model.exception.InvalidPhoneNumberFormatException;
import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;
import elca.ntig.partnerapp.be.repository.PartnerRepository;
import elca.ntig.partnerapp.be.repository.PersonRepository;
import elca.ntig.partnerapp.be.service.AddressService;
import elca.ntig.partnerapp.be.utils.mapper.PersonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {
    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private PersonServiceImpl personService;

    private Person person01;
    private Partner partner01;

    private Person person02;
    private Partner partner02;

    private Person person03;
    private Partner partner03;

    private Person person04;
    private Partner partner04;

    @BeforeEach
    void setUp() {
        partner01 = Partner.builder()
                .id(1)
                .language(Language.LANG_ENGLISH)
                .phoneNumber("0123456789")
                .status(Status.ACTIVE)
                .build();

        person01 = Person.builder()
                .id(1)
                .partner(partner01)
                .firstName("John")
                .lastName("Doe")
                .sex(SexEnum.MALE)
                .nationality(Nationality.NATIONALITY_FRENCH)
                .birthDate(LocalDate.of(1999, 9, 20))
                .avsNumber("7561234567890")
                .maritalStatus(MaritalStatus.SINGLE)
                .build();

        // Partner 2 and Person 2
        partner02 = Partner.builder()
                .id(2)
                .language(Language.LANG_FRENCH)
                .phoneNumber("0987654321")
                .status(Status.ACTIVE)
                .build();

        person02 = Person.builder()
                .id(2)
                .partner(partner02)
                .firstName("Marie")
                .lastName("Dupont")
                .sex(SexEnum.FEMALE)
                .nationality(Nationality.NATIONALITY_BRITISH)
                .birthDate(LocalDate.of(1995, 3, 15))
                .avsNumber("7560987654321")
                .maritalStatus(MaritalStatus.MARRIED)
                .build();

        // Partner 3 and Person 3
        partner03 = Partner.builder()
                .id(3)
                .language(Language.LANG_ITALIAN)
                .phoneNumber("1234567890")
                .status(Status.ACTIVE)
                .build();

        person03 = Person.builder()
                .id(3)
                .partner(partner03)
                .firstName("Hans")
                .lastName("Müller")
                .sex(SexEnum.MALE)
                .nationality(Nationality.NATIONALITY_GERMAN)
                .birthDate(LocalDate.of(1988, 7, 22))
                .avsNumber("7563456789012")
                .maritalStatus(MaritalStatus.DIVORCED)
                .build();

        // Partner 4 and Person 4
        partner04 = Partner.builder()
                .id(4)
                .language(Language.LANG_DEUTSCH)
                .phoneNumber("2345678901")
                .status(Status.ACTIVE)
                .build();

        person04 = Person.builder()
                .id(4)
                .partner(partner04)
                .firstName("Varia")
                .lastName("Kyo")
                .sex(SexEnum.FEMALE)
                .nationality(Nationality.NATIONALITY_SPANISH)
                .birthDate(LocalDate.of(1990, 12, 10))
                .avsNumber("7564567890123")
                .maritalStatus(MaritalStatus.WIDOWED)
                .build();
    }

    // getPersonById Tests
    @Test
    void getPersonByIdPersonFoundReturnsPersonResponseDto() {
        // Arrange
        Integer personId = person01.getId();
        when(personRepository.findById(personId)).thenReturn(Optional.of(person01));

        PersonResponseDto mockResponseDto = PersonResponseDto.builder()
                .id(person01.getId())
                .firstName(person01.getFirstName())
                .lastName(person01.getLastName())
                .language(partner01.getLanguage())
                .sex(person01.getSex())
                .nationality(person01.getNationality())
                .avsNumber(person01.getAvsNumber())
                .birthDate(person01.getBirthDate())
                .maritalStatus(person01.getMaritalStatus())
                .phoneNumber(partner01.getPhoneNumber())
                .status(partner01.getStatus())
                .build();
        when(personMapper.toPersonResponseDto(person01)).thenReturn(mockResponseDto);

        // Act
        PersonResponseDto result = personService.getPersonById(personId);

        // Assert
        assertNotNull(result);
        assertEquals(person01.getId(), result.getId());
        assertEquals(person01.getFirstName(), result.getFirstName());
        assertEquals(person01.getLastName(), result.getLastName());
        assertEquals(partner01.getLanguage(), result.getLanguage());
        assertEquals(person01.getSex(), result.getSex());
        assertEquals(person01.getNationality(), result.getNationality());
        assertEquals(person01.getAvsNumber(), result.getAvsNumber());
        assertEquals(person01.getBirthDate(), result.getBirthDate());
        assertEquals(person01.getMaritalStatus(), result.getMaritalStatus());
        assertEquals(partner01.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(partner01.getStatus(), result.getStatus());


        verify(personRepository, times(1)).findById(personId);
        verify(personMapper, times(1)).toPersonResponseDto(person01);
    }

    @Test
    void getPersonByIdPersonNotFoundThrowsResourceNotFoundException() {
        // Arrange
        Integer personId = -1;
        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            personService.getPersonById(personId);
        });

        // Assert
        assertEquals("Person with id: '-1' not found!", exception.getMessage());

        verify(personRepository, times(1)).findById(personId);
        verifyNoInteractions(personMapper);
    }

    // searchPeoplePagination Tests
    @Test
    void searchPeoplePaginationWithResultsReturnsPaginatedResponse() {
        // Arrange
        int pageNo = 0;
        int pageSize = 2;
        String sortBy = "firstName";
        String sortDir = "asc";

        SearchPeopleCriteriasDto criterias = new SearchPeopleCriteriasDto();
        criterias.setLastName("u");

        Sort sort = Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        List<Person> peopleList = Arrays.asList(person01, person02);
        Page<Person> page = new PageImpl<>(peopleList, pageable, peopleList.size());

        when(personRepository.searchPeoplePagination(criterias, pageable)).thenReturn(page);

        PersonResponseDto mockResponseDto1 = PersonResponseDto.builder()
                .id(person01.getId())
                .firstName(person01.getFirstName())
                .lastName(person01.getLastName())
                .language(partner01.getLanguage())
                .sex(person01.getSex())
                .nationality(person01.getNationality())
                .avsNumber(person01.getAvsNumber())
                .birthDate(person01.getBirthDate())
                .maritalStatus(person01.getMaritalStatus())
                .phoneNumber(partner01.getPhoneNumber())
                .status(partner01.getStatus())
                .build();

        PersonResponseDto mockResponseDto2 = PersonResponseDto.builder()
                .id(person02.getId())
                .firstName(person02.getFirstName())
                .lastName(person02.getLastName())
                .language(partner02.getLanguage())
                .sex(person02.getSex())
                .nationality(person02.getNationality())
                .avsNumber(person02.getAvsNumber())
                .birthDate(person02.getBirthDate())
                .maritalStatus(person02.getMaritalStatus())
                .phoneNumber(partner02.getPhoneNumber())
                .status(partner02.getStatus())
                .build();

        when(personMapper.toPersonResponseDto(person01)).thenReturn(mockResponseDto1);
        when(personMapper.toPersonResponseDto(person02)).thenReturn(mockResponseDto2);

        // Act
        SearchPeoplePaginationResponseDto result = personService.searchPeoplePagination(pageNo, pageSize, sortBy, sortDir, criterias);

        // Assert
        assertNotNull(result);
        assertEquals(pageNo, result.getPageNo());
        assertEquals(pageSize, result.getPageSize());
        assertEquals(1, result.getTotalPages());
        assertEquals(2, result.getTotalRecords());
        assertTrue(result.isLast());
        assertEquals(2, result.getContent().size());
        assertEquals(person01.getId(), result.getContent().get(0).getId());
        assertEquals(person02.getId(), result.getContent().get(1).getId());

        verify(personRepository, times(1)).searchPeoplePagination(criterias, pageable);
        verify(personMapper, times(2)).toPersonResponseDto(any(Person.class));
    }

    // createPerson Tests
    @Test
    void createPersonValidRequestReturnsPersonResponseDto() {
        // Arrange
        CreatePersonRequestDto createRequest = CreatePersonRequestDto.builder()
                .language(Language.LANG_ENGLISH)
                .phoneNumber("0123456789")
                .lastName("Doe")
                .firstName("John")
                .sex(SexEnum.MALE)
                .nationality(Nationality.NATIONALITY_FRENCH)
                .avsNumber("7561234567890")
                .birthDate(LocalDate.of(1999, 9, 20))
                .maritalStatus(MaritalStatus.SINGLE)
                .build();

        when(partnerRepository.save(any(Partner.class))).thenReturn(partner01);
        when(personRepository.save(any(Person.class))).thenReturn(person01);

        PersonResponseDto mockResponseDto = PersonResponseDto.builder()
                .id(person01.getId())
                .firstName(person01.getFirstName())
                .lastName(person01.getLastName())
                .sex(person01.getSex())
                .nationality(person01.getNationality())
                .avsNumber(person01.getAvsNumber())
                .birthDate(person01.getBirthDate())
                .maritalStatus(person01.getMaritalStatus())
                .build();
        when(personMapper.toPersonResponseDto(any(Person.class))).thenReturn(mockResponseDto);

        // Act
        PersonResponseDto result = personService.createPerson(createRequest);

        // Assert
        assertNotNull(result);
        assertEquals(mockResponseDto, result);

        verify(partnerRepository, times(1)).save(any(Partner.class));
        verify(personRepository, times(1)).save(any(Person.class));
        verify(personMapper, times(1)).toPersonResponseDto(any(Person.class));
    }

    @Test
    void createPersonInvalidAVSNumberThrowsInvalidAVSNumberFormatException() {
        // Arrange
        CreatePersonRequestDto createRequest = CreatePersonRequestDto.builder()
                .language(Language.LANG_ENGLISH)
                .phoneNumber("0123456789")
                .lastName("Doe")
                .firstName("John")
                .sex(SexEnum.MALE)
                .nationality(Nationality.NATIONALITY_FRENCH)
                .avsNumber("12345") // Invalid AVS number
                .birthDate(LocalDate.of(1999, 9, 20))
                .maritalStatus(MaritalStatus.SINGLE)
                .build();

        // Act & Assert
        InvalidAVSNumberFormatException exception = assertThrows(InvalidAVSNumberFormatException.class, () -> {
            personService.createPerson(createRequest);
        });

        assertEquals("Invalid AVS number format", exception.getMessage());

        verify(partnerRepository, never()).save(any(Partner.class));
        verify(personRepository, never()).save(any(Person.class));
        verify(addressService, never()).createAddressForPartner(any(), any());
        verify(personMapper, never()).toPersonResponseDto(any());
    }

    @Test
    void createPersonInvalidBirthDateThrowsDateNotInThePastException() {
        // Arrange
        CreatePersonRequestDto createRequest = CreatePersonRequestDto.builder()
                .language(Language.LANG_ENGLISH)
                .phoneNumber("0123456789")
                .lastName("Doe")
                .firstName("John")
                .sex(SexEnum.MALE)
                .nationality(Nationality.NATIONALITY_FRENCH)
                .avsNumber("7561234567890")
                .birthDate(LocalDate.now()) // Invalid birth date
                .maritalStatus(MaritalStatus.SINGLE)
                .build();

        // Act
        DateNotInThePastException exception = assertThrows(DateNotInThePastException.class, () -> {
            personService.createPerson(createRequest);
        });

        // Assert
        assertEquals("Date must be in the past", exception.getMessage());

        verify(partnerRepository, never()).save(any(Partner.class));
        verify(personRepository, never()).save(any(Person.class));
        verify(addressService, never()).createAddressForPartner(any(), any());
        verify(personMapper, never()).toPersonResponseDto(any());
    }

    @Test
    void createPersonInvalidPhoneNumberThrowsInvalidPhoneNumberFormatException() {
        // Arrange
        CreatePersonRequestDto createRequest = CreatePersonRequestDto.builder()
                .language(Language.LANG_ENGLISH)
                .phoneNumber("12345") // Invalid phone number
                .lastName("Doe")
                .firstName("John")
                .sex(SexEnum.MALE)
                .nationality(Nationality.NATIONALITY_FRENCH)
                .avsNumber("7561234567890")
                .birthDate(LocalDate.of(1999, 9, 20))
                .maritalStatus(MaritalStatus.SINGLE)
                .build();

        // Act
        InvalidPhoneNumberFormatException exception = assertThrows(InvalidPhoneNumberFormatException.class, () -> {
            personService.createPerson(createRequest);
        });

        // Assert
        assertEquals("Invalid phone number format", exception.getMessage());

        verify(partnerRepository, never()).save(any(Partner.class));
        verify(personRepository, never()).save(any(Person.class));
        verify(addressService, never()).createAddressForPartner(any(), any());
        verify(personMapper, never()).toPersonResponseDto(any());
    }

    // updatePerson Tests
    @Test
    void updatePersonValidRequestReturnsUpdatedPersonResponseDto() {
        // Arrange
        UpdatePersonRequestDto updateRequest = UpdatePersonRequestDto.builder()
                .id(person01.getId())
                .lastName("Doe")
                .firstName("John")
                .sex(SexEnum.MALE)
                .nationality(Nationality.NATIONALITY_FRENCH)
                .avsNumber("7560987654321")
                .birthDate(LocalDate.of(2000, 1, 1))
                .maritalStatus(MaritalStatus.SINGLE)
                .phoneNumber("0123456789")
                .build();
        when(personRepository.findById(updateRequest.getId())).thenReturn(Optional.of(person01));
        doNothing().when(personMapper).updatePerson(updateRequest, person01);
        when(personRepository.save(person01)).thenReturn(person01);

        // Mocking response DTO
        PersonResponseDto mockResponseDto = PersonResponseDto.builder()
                .id(person01.getId())
                .firstName(updateRequest.getFirstName())
                .lastName(updateRequest.getLastName())
                .language(partner01.getLanguage())
                .sex(updateRequest.getSex())
                .nationality(updateRequest.getNationality())
                .avsNumber(updateRequest.getAvsNumber())
                .birthDate(updateRequest.getBirthDate())
                .maritalStatus(updateRequest.getMaritalStatus())
                .phoneNumber(updateRequest.getPhoneNumber())
                .status(partner01.getStatus())
                .build();
        when(personMapper.toPersonResponseDto(person01)).thenReturn(mockResponseDto);

        // Act
        PersonResponseDto result = personService.updatePerson(updateRequest);

        // Assert
        assertNotNull(result);
        assertEquals(person01.getId(), result.getId());
        assertEquals(updateRequest.getFirstName(), result.getFirstName());
        assertEquals(updateRequest.getLastName(), result.getLastName());
        assertEquals(updateRequest.getAvsNumber(), result.getAvsNumber());
        assertEquals(updateRequest.getPhoneNumber(), result.getPhoneNumber());

        verify(personRepository, times(1)).findById(updateRequest.getId());
        verify(personMapper, times(1)).updatePerson(updateRequest, person01);
        verify(personRepository, times(1)).save(person01);
        verify(addressService, times(1)).updateAddressForPartner(partner01, updateRequest.getAddresses());
        verify(personMapper, times(1)).toPersonResponseDto(person01);
    }

    @Test
    void updatePersonPersonNotFoundThrowsResourceNotFoundException() {
        // Arrange
        UpdatePersonRequestDto updateRequest = UpdatePersonRequestDto.builder()
                .id(-1)
                .build();

        when(personRepository.findById(updateRequest.getId())).thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            personService.updatePerson(updateRequest);
        });

        // Assert
        assertEquals("Person with id: '-1' not found!", exception.getMessage());

        verify(personRepository, times(1)).findById(updateRequest.getId());
        verifyNoInteractions(personMapper);
    }

    // getPersonAlongWithAddress Tests
    @Test
    void getPersonAlongWithAddressPersonFoundReturnsDtoWithAddresses() {
        // Arrange
        Integer personId = person01.getId();
        when(personRepository.findById(personId)).thenReturn(Optional.of(person01));

        PersonResponseDto personResponseDto = PersonResponseDto.builder()
                .id(person01.getId())
                .firstName(person01.getFirstName())
                .lastName(person01.getLastName())
                .sex(person01.getSex())
                .nationality(person01.getNationality())
                .avsNumber(person01.getAvsNumber())
                .birthDate(person01.getBirthDate())
                .maritalStatus(person01.getMaritalStatus())
                .phoneNumber(partner01.getPhoneNumber())
                .status(partner01.getStatus())
                .build();
        when(personMapper.toPersonResponseDto(person01)).thenReturn(personResponseDto);

        AddressResponseDto addressResponseDto = AddressResponseDto.builder()
                .id(1)
                .street("123 Main St")
                .locality("Somewhere")
                .zipCode("12345")
                .canton(CantonAbbr.BASEL_CITY)
                .country(Country.COUNTRY_SWITZERLAND)
                .category(AddressType.MAIN)
                .houseNumber("101")
                .validityStart(LocalDate.of(2022, 1, 1))
                .validityEnd(LocalDate.of(2023, 1, 1))
                .status(Status.ACTIVE)
                .build();
        when(addressService.getAddressesByPartnerId(partner01.getId())).thenReturn(Collections.singletonList(addressResponseDto));

        // Act
        GetPersonAlongWithAddressResponseDto result = personService.getPersonAlongWithAddress(personId);

        // Assert
        assertNotNull(result);
        assertEquals(person01.getId(), result.getPerson().getId());
        assertEquals(1, result.getAddresses().size());
        assertEquals("123 Main St", result.getAddresses().get(0).getStreet());
        assertEquals("Somewhere", result.getAddresses().get(0).getLocality());
        assertEquals("12345", result.getAddresses().get(0).getZipCode());
        assertEquals(CantonAbbr.BASEL_CITY, result.getAddresses().get(0).getCanton());
        assertEquals(Country.COUNTRY_SWITZERLAND, result.getAddresses().get(0).getCountry());
        assertEquals(AddressType.MAIN, result.getAddresses().get(0).getCategory());
        assertEquals("101", result.getAddresses().get(0).getHouseNumber());
        assertEquals(LocalDate.of(2022, 1, 1), result.getAddresses().get(0).getValidityStart());
        assertEquals(LocalDate.of(2023, 1, 1), result.getAddresses().get(0).getValidityEnd());
        assertEquals(Status.ACTIVE, result.getAddresses().get(0).getStatus());

        verify(personRepository, times(1)).findById(personId);
        verify(addressService, times(1)).getAddressesByPartnerId(partner01.getId());
    }

    @Test
    void getPersonAlongWithAddressPersonNotFoundThrowsResourceNotFoundException() {
        // Arrange
        Integer personId = -1;
        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            personService.getPersonAlongWithAddress(personId);
        });

        // Assert
        assertEquals("Person with id: '-1' not found!", exception.getMessage());

        verify(personRepository, times(1)).findById(personId);
        verifyNoInteractions(addressService);
    }
}