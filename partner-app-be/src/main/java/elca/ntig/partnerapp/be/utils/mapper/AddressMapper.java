package elca.ntig.partnerapp.be.utils.mapper;

import elca.ntig.partnerapp.be.model.dto.address.*;
import elca.ntig.partnerapp.be.model.dto.person.UpdatePersonRequestDto;
import elca.ntig.partnerapp.be.model.entity.Person;
import elca.ntig.partnerapp.be.utils.mapper.enums.address.AddressTypeMapper;
import elca.ntig.partnerapp.be.utils.mapper.enums.address.CantonAbbrMapper;
import elca.ntig.partnerapp.be.utils.mapper.enums.address.CountryMapper;
import elca.ntig.partnerapp.be.utils.mapper.enums.partner.StatusMapper;
import elca.ntig.partnerapp.common.proto.entity.address.*;
import org.mapstruct.*;
import elca.ntig.partnerapp.be.model.entity.Address;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {
                DateMapper.class,
                CountryMapper.class,
                AddressTypeMapper.class,
                CantonAbbrMapper.class,
                StatusMapper.class
        },
        collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public interface AddressMapper {
        @Mapping(target = "id", ignore = true)
        @Mapping(target = "partner", ignore = true)
        void updateExistingAddress(AddressResponseDto addressResponseDto, @MappingTarget Address address);

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "partner", ignore = true)
        Address toAddressFromCreateAddressRequestDto(CreateAddressRequestDto createAddressRequestDto);

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "partner", ignore = true)
        Address toAddressFromAddressResponseDto(AddressResponseDto addressResponseDto);

        AddressResponseDto toAddressResponseDto(Address address);

        AddressResponseDto toAddressResponseDtoFromAddressResponseProto(AddressResponseProto addressResponseProto);

        @Mapping(source = "validityStart", target = "validityStart", qualifiedByName = "mapLocalDateToString")
        @Mapping(source = "validityEnd", target = "validityEnd", qualifiedByName = "mapLocalDateToString")
        AddressResponseProto toAddressResponseProto(AddressResponseDto addressResponseDto);

        @Mapping(source = "addresses", target = "addressesList")
        GetListAddressesResponseProto toGetListAddressesResponseProto(GetListAddressesResponseDto getListAddressesResponseDto);

        DeleteAddressResponseProto toDeleteAddressResponseProto(DeleteAddressResponseDto deleteAddressResponseDto);

        @Mapping(source = "validityStart", target = "validityStart", qualifiedByName = "mapStringToLocalDate")
        @Mapping(source = "validityEnd", target = "validityEnd", qualifiedByName = "mapStringToLocalDate")
        CreateAddressRequestDto toCreateAddressRequestDto(CreateAddressRequestProto createAddressRequestProto);

        @Mapping(source = "validityStart", target = "validityStart", qualifiedByName = "mapStringToLocalDate")
        @Mapping(source = "validityEnd", target = "validityEnd", qualifiedByName = "mapStringToLocalDate")
        UpdateAddressRequestDto toUpdateAddressRequestDto(UpdateAddressRequestProto createAddressRequestProto);

        @Mapping(target = "id", ignore = true)
        void updateAddress(UpdateAddressRequestDto updateAddressRequestDto, @MappingTarget Address address);
}
