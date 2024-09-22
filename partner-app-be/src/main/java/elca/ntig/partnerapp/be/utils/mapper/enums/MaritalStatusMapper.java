package elca.ntig.partnerapp.be.utils.mapper.enums;

import elca.ntig.partnerapp.be.model.enums.person.MaritalStatus;
import elca.ntig.partnerapp.be.utils.mapper.constant.MapperConstant;
import elca.ntig.partnerapp.common.proto.enums.person.MaritalStatusProto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface MaritalStatusMapper {
    @ValueMapping(target = MapperConstant.NULL_MARITAL_STATUS, source = MappingConstants.NULL)
    MaritalStatusProto toMaritalStatusProto(MaritalStatus language);

    @ValueMapping(target = MappingConstants.THROW_EXCEPTION, source = MapperConstant.UNRECOGNIZED)
    @ValueMapping(target = MappingConstants.NULL, source = MapperConstant.NULL_MARITAL_STATUS)
    MaritalStatus toMaritalStatus(MaritalStatusProto languageProto);
}
