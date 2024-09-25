package elca.ntig.partnerapp.be.service.impl;

import elca.ntig.partnerapp.be.model.dto.person.*;
import elca.ntig.partnerapp.be.model.entity.Partner;
import elca.ntig.partnerapp.be.model.enums.common.Status;
import elca.ntig.partnerapp.be.model.exception.*;
import elca.ntig.partnerapp.be.repository.PartnerRepository;
import elca.ntig.partnerapp.be.utils.mapper.PersonMapper;
import elca.ntig.partnerapp.be.model.entity.Person;
import elca.ntig.partnerapp.be.repository.PersonRepository;
import elca.ntig.partnerapp.be.service.PersonService;
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
public class PersonServiceImpl implements PersonService {
    private final PartnerRepository partnerRepository;
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    public PersonResponseDto getPersonById(Integer id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person", "id", id));
        return personMapper.toPersonResponseDto(person);
    }

    @Override
    public SearchPeoplePaginationResponseDto searchPeoplePagination(int pageNo, int pageSize, String sortBy, String sortDir, SearchPeopleCriteriasDto criterias) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Person> people = personRepository.searchPeoplePagination(criterias, pageable);
        List<Person> peopleList = people.getContent();

        List<PersonResponseDto> content = peopleList.stream().map(person -> personMapper.toPersonResponseDto(person)).collect(Collectors.toList());
        return SearchPeoplePaginationResponseDto.builder()
                .pageNo(people.getNumber())
                .pageSize(people.getSize())
                .totalPages(people.getTotalPages())
                .totalRecords(people.getTotalElements())
                .last(people.isLast())
                .content(content)
                .build();
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public PersonResponseDto createPerson(CreatePersonRequestDto createPersonRequestDto) {
        validateCreateRequest(createPersonRequestDto);

        Partner partner = Partner.builder()
                .language(createPersonRequestDto.getLanguage())
                .phoneNumber(createPersonRequestDto.getPhoneNumber())
                .status(Status.ACTIVE)
                .build();
        partnerRepository.save(partner);

        Person person = Person.builder()
                .id(partner.getId())
                .lastName(createPersonRequestDto.getLastName())
                .firstName(createPersonRequestDto.getFirstName())
                .sex(createPersonRequestDto.getSex())
                .nationality(createPersonRequestDto.getNationality())
                .avsNumber(createPersonRequestDto.getAvsNumber())
                .birthDate(createPersonRequestDto.getBirthDate())
                .maritalStatus(createPersonRequestDto.getMaritalStatus())
                .partner(partner)
                .build();
        personRepository.save(person);

        return personMapper.toPersonResponseDto(person);
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public PersonResponseDto updatePerson(UpdatePersonRequestDto updatePersonRequestDto) {
        Person person = personRepository.findById(updatePersonRequestDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Person", "id", updatePersonRequestDto.getId()));
//        Partner partner = person.getPartner();

        validateUpdateRequest(updatePersonRequestDto);

        personMapper.updatePerson(updatePersonRequestDto, person);
        person = personRepository.save(person);

//        partner.setLanguage(updatePersonRequestDto.getLanguage());
//        partner.setPhoneNumber(updatePersonRequestDto.getPhoneNumber());
//        partnerRepository.save(partner);
//
//        person.setLastName(updatePersonRequestDto.getLastName());
//        person.setFirstName(updatePersonRequestDto.getFirstName());
//        person.setSex(updatePersonRequestDto.getSex());
//        person.setNationality(updatePersonRequestDto.getNationality());
//        person.setAvsNumber(updatePersonRequestDto.getAvsNumber());
//        person.setBirthDate(updatePersonRequestDto.getBirthDate());
//        person.setMaritalStatus(updatePersonRequestDto.getMaritalStatus());
//        person.setPartner(partner);
//        person = personRepository.save(person);

        return personMapper.toPersonResponseDto(person);
    }

    private void validateCreateRequest(CreatePersonRequestDto createPersonRequestDto) {
        validateAVSNumber(createPersonRequestDto.getAvsNumber());
        validateBirthDate(createPersonRequestDto.getBirthDate());
        validatePhoneNumber(createPersonRequestDto.getPhoneNumber());
    }

    private void validateUpdateRequest(UpdatePersonRequestDto updatePersonRequestDto) {
        validateUpdateAVSNumber(updatePersonRequestDto.getAvsNumber(), updatePersonRequestDto.getId());
        validateBirthDate(updatePersonRequestDto.getBirthDate());
        validatePhoneNumber(updatePersonRequestDto.getPhoneNumber());
    }

    private void validateUpdateAVSNumber(String avsNumber, Integer id) {
        if (StringUtils.isNotBlank(avsNumber)) {
            String regex = "^756\\d{10}$";
            if (!avsNumber.matches(regex)) {
                throw new InvalidAVSNumberFormatException("Invalid AVS number format");
            }

            Person checkAvsPerson = personRepository.findPersonByAvsNumberAndPartnerStatus(avsNumber, Status.ACTIVE);
            // avoid checking against the person being updated
            if (checkAvsPerson != null && !checkAvsPerson.getId().equals(id)) {
                throw new ExistingActiveAVSNumberException("Person with AVS number " + avsNumber + " already exists");
            }
        }
    }

    private void validateAVSNumber(String avsNumber) {
        if (StringUtils.isNotBlank(avsNumber)) {
            String regex = "^756\\d{10}$";
            if (!avsNumber.matches(regex)) {
                throw new InvalidAVSNumberFormatException("Invalid AVS number format");
            }

            Person checkAvsPerson = personRepository.findPersonByAvsNumberAndPartnerStatus(avsNumber, Status.ACTIVE);
            if (checkAvsPerson != null) {
                throw new ExistingActiveAVSNumberException("Person with AVS number " + avsNumber + " already exists");
            }
        }
    }

    private void validateBirthDate(LocalDate birthDate) {
        if ((birthDate != null) && (!birthDate.isBefore(LocalDate.now()))) {
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
