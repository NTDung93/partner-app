package elca.ntig.partnerapp.be.utils.mapper.enums.address;


import elca.ntig.partnerapp.be.model.enums.addess.CantonAbbr;
import elca.ntig.partnerapp.be.utils.mapper.constant.MapperConstant;
import elca.ntig.partnerapp.common.proto.enums.address.CantonAbbrProto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CantonAbbrMapper {
    @ValueMapping(target = MapperConstant.NULL_CANTON, source = MappingConstants.NULL)
    CantonAbbrProto toCantonAbbrProto(CantonAbbr cantonAbbr);

    @ValueMapping(target = MappingConstants.THROW_EXCEPTION, source = MapperConstant.UNRECOGNIZED)
    @ValueMapping(target = MappingConstants.NULL, source = MapperConstant.NULL_CANTON)
    CantonAbbr toCantonAbbr(CantonAbbrProto cantonAbbrProto);
}
