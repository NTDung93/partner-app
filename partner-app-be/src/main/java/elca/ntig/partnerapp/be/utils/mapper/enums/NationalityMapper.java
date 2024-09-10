package elca.ntig.partnerapp.be.utils.mapper.enums;

import elca.ntig.partnerapp.be.model.enums.person.Nationality;
import elca.ntig.partnerapp.be.utils.mapper.constant.MapperConstant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import elca.ntig.partnerapp.common.proto.enums.person.NationalityProto;
import org.mapstruct.ValueMapping;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface NationalityMapper {

    @ValueMapping(target = MapperConstant.NULL_NATIONALITY, source = MappingConstants.NULL)
    NationalityProto toNationalityProto(Nationality nationality);

    @ValueMapping(target = MappingConstants.THROW_EXCEPTION, source = MapperConstant.UNRECOGNIZED)
    @ValueMapping(target = MappingConstants.NULL, source = MapperConstant.NULL_NATIONALITY)
    Nationality toNationality(NationalityProto nationalityProto);
}
