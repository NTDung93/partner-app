package elca.ntig.partnerapp.be.utils.mapper.enums.person;

import elca.ntig.partnerapp.be.utils.mapper.constant.MapperConstant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import elca.ntig.partnerapp.common.proto.enums.person.SexEnumProto;
import elca.ntig.partnerapp.be.model.enums.person.SexEnum;
import org.mapstruct.ValueMapping;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface SexEnumMapper {

    @ValueMapping(target = MapperConstant.NULL_SEX_ENUM, source = MappingConstants.NULL)
    SexEnumProto toSexEnumProto(SexEnum sexEnum);

    @ValueMapping(target = MappingConstants.THROW_EXCEPTION, source = MapperConstant.UNRECOGNIZED)
    @ValueMapping(target = MappingConstants.NULL, source = MapperConstant.NULL_SEX_ENUM)
    SexEnum toSexEnum(SexEnumProto sexEnumProto);
}
