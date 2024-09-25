package elca.ntig.partnerapp.be.utils.mapper.enums.address;

import elca.ntig.partnerapp.be.model.enums.addess.AddressType;
import elca.ntig.partnerapp.be.utils.mapper.constant.MapperConstant;
import elca.ntig.partnerapp.common.proto.enums.address.AddressTypeProto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface AddressTypeMapper {

    AddressTypeProto toAddressTypeProto(AddressType addressType);

    @ValueMapping(target = MappingConstants.THROW_EXCEPTION, source = MapperConstant.UNRECOGNIZED)
    AddressType toAddressType(AddressTypeProto addressTypeProto);
}
