package elca.ntig.partnerapp.be.service.impl;

import elca.ntig.partnerapp.be.model.dto.address.AddressResponseDto;
import elca.ntig.partnerapp.be.model.dto.address.CreateAddressRequestDto;
import elca.ntig.partnerapp.be.model.entity.Address;
import elca.ntig.partnerapp.be.model.entity.Partner;
import elca.ntig.partnerapp.be.model.enums.addess.AddressType;
import elca.ntig.partnerapp.be.model.enums.common.Status;
import elca.ntig.partnerapp.be.model.exception.EndDateBeforeStartDateException;
import elca.ntig.partnerapp.be.model.exception.OverlapPeriodException;
import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;
import elca.ntig.partnerapp.be.repository.AddressRepository;
import elca.ntig.partnerapp.be.service.AddressService;
import elca.ntig.partnerapp.be.utils.mapper.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

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
                            if (!currentEnd.isAfter(currentStart)) {
                                throw new EndDateBeforeStartDateException("The validity end date must be after the validity start date");
                            }
                        }

                        if (previousEnd != null) {
                            if (!currentStart.isAfter(previousEnd)) {
                                throw new OverlapPeriodException("Dialog.err.message.OverlapPeriodException");
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
//                    throw new OverlapPeriodException("Dialog.err.message.OverlapPeriodException);
//                }
//            }
//
//            // Update previousType and previousEnd for the next iteration
//            previousType = currentType;
//            previousEnd = currentEnd;
//        }

        List<Address> addressEntities = addresses.stream()
                .map(address -> {
                    Address updateAddress = addressMapper.toAddressFromCreateAddressRequestDto(address);
                    updateAddress.setPartner(partner);
                    updateAddress.setStatus(Status.ACTIVE);
                    return updateAddress;
                })
                .collect(Collectors.toList());

        addressRepository.saveAll(addressEntities);
    }

    @Override
    public List<AddressResponseDto> getAddressesByPartnerId(Integer partnerId) {
        List<Address> addresses = addressRepository.findByPartnerId(partnerId);
        if (addresses.isEmpty()) {
            return null;
        } else {
            return addresses.stream()
                    .map(address -> addressMapper.toAddressResponseDto(address))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void updateAddressForPartner(Partner partner, List<AddressResponseDto> addresses) {
        addresses.stream()
                .collect(Collectors.groupingBy(AddressResponseDto::getCategory))
                .forEach((category, categoryAddresses) -> {
                    categoryAddresses.sort(Comparator.comparing(AddressResponseDto::getValidityStart));

                    LocalDate previousEnd = null;

                    for (AddressResponseDto currentAddress : categoryAddresses) {
                        LocalDate currentStart = currentAddress.getValidityStart();
                        LocalDate currentEnd = currentAddress.getValidityEnd() != null
                                ? currentAddress.getValidityEnd()
                                : LocalDate.MAX;

                        if (currentAddress.getValidityEnd() != null) {
                            if (!currentEnd.isAfter(currentStart)) {
                                throw new EndDateBeforeStartDateException("The validity end date must be after the validity start date");
                            }
                        }

                        if (previousEnd != null) {
                            if (!currentStart.isAfter(previousEnd)) {
                                throw new OverlapPeriodException("Dialog.err.message.OverlapPeriodException");
                            }
                        }

                        previousEnd = currentEnd;
                    }
                });

        addresses.stream()
                .forEach(address -> {
                    if (address.getId() == null || address.getId() == 0) {
                        Address updateAddress = addressMapper.toAddressFromAddressResponseDto(address);
                        updateAddress.setPartner(partner);
                        updateAddress.setStatus(Status.ACTIVE);
                        addressRepository.save(updateAddress);
                    } else {
                        Address updateAddress = addressRepository.findById(address.getId())
                                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", address.getId()));
                        addressMapper.updateExistingAddress(address, updateAddress);
                        addressRepository.save(updateAddress);
                    }
                });
    }
}
