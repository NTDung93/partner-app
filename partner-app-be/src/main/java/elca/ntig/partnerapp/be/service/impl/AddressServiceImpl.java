package elca.ntig.partnerapp.be.service.impl;

import elca.ntig.partnerapp.be.model.dto.address.CreateAddressRequestDto;
import elca.ntig.partnerapp.be.model.entity.Address;
import elca.ntig.partnerapp.be.model.entity.Partner;
import elca.ntig.partnerapp.be.model.enums.addess.AddressType;
import elca.ntig.partnerapp.be.model.enums.common.Status;
import elca.ntig.partnerapp.be.model.exception.EndDateBeforeStartDateException;
import elca.ntig.partnerapp.be.model.exception.OverlapPeriodException;
import elca.ntig.partnerapp.be.repository.AddressRepository;
import elca.ntig.partnerapp.be.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    @Override
    public void createAddressForPartner(Partner partner, List<CreateAddressRequestDto> addresses) {

        // solution 1: group by category and check for overlap inside stream
        addresses.stream()
                .collect(Collectors.groupingBy(CreateAddressRequestDto::getCategory))
                .forEach((category, categoryAddresses) -> {
                    categoryAddresses.sort(Comparator.comparing(CreateAddressRequestDto::getValidityStart));

                    LocalDate previousEnd = null;

                    for (CreateAddressRequestDto currentAddress : categoryAddresses) {
                        LocalDate currentStart = currentAddress.getValidityStart();
                        LocalDate currentEnd = currentAddress.getValidityEnd() != null
                                ? currentAddress.getValidityEnd()
                                : LocalDate.MAX;

                        if (currentAddress.getValidityEnd() != null) {
                            if (currentEnd.isBefore(currentStart) || currentEnd.isEqual(currentStart)) {
                                throw new EndDateBeforeStartDateException("The validity end date must be after the validity start date");
                            }
                        }

                        if (previousEnd != null) {
                            if (currentStart.isBefore(previousEnd)) {
                                throw new OverlapPeriodException("The validity periods of addresses of the same type must not overlap");
                            }
                        }

                        previousEnd = currentEnd;
                    }
                });

        // solution 2: sort the list by type and validityStart then check for overlap in sorted list
//        addresses.sort(Comparator
//                .comparing(CreateAddressRequestDto::getCategory)
//                .thenComparing(CreateAddressRequestDto::getValidityStart));

//        AddressType previousType = null;
//        LocalDate previousEnd = null;
//
//        for (CreateAddressRequestDto currentAddress : addresses) {
//            AddressType currentType = currentAddress.getCategory();
//            LocalDate currentStart = currentAddress.getValidityStart();
//            LocalDate currentEnd = currentAddress.getValidityEnd() != null
//                    ? currentAddress.getValidityEnd()
//                    : LocalDate.MAX;
//
//            if (currentAddress.getValidityEnd() != null) {
//                if (currentEnd.isBefore(currentStart) || currentEnd.isEqual(currentStart)) {
//                    throw new EndDateBeforeStartDateException("The validity end date must be after the validity start date");
//                }
//            }
//
//            // Only check overlap for addresses that have the same type
//            if (currentType.equals(previousType)) {
//                if (currentStart.isBefore(previousEnd)) {
//                    throw new OverlapPeriodException("The validity periods of addresses of the same type must not overlap");
//                }
//            }
//
//            // Update previousType and previousEnd for the next iteration
//            previousType = currentType;
//            previousEnd = currentEnd;
//        }

        // if no exception is thrown, save the addresses
        List<Address> addressEntities = addresses.stream()
                .map(address -> mapToAddress(partner, address))
                .collect(Collectors.toList());

        addressRepository.saveAll(addressEntities);
    }

    private Address mapToAddress(Partner partner, CreateAddressRequestDto address) {
        return Address.builder()
                .category(address.getCategory())
                .locality(address.getLocality())
                .street(address.getStreet())
                .country(address.getCountry())
                .validityStart(address.getValidityStart())
                .zipCode(address.getZipCode())
                .houseNumber(address.getHouseNumber())
                .canton(address.getCanton())
                .validityEnd(address.getValidityEnd())
                .partner(partner)
                .status(Status.ACTIVE)
                .build();
    }
}
