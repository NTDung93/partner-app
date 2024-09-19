package elca.ntig.partnerapp.be.utils.mapper.enums;

import elca.ntig.partnerapp.be.model.enums.organisation.CodeNOGA;
import elca.ntig.partnerapp.be.utils.mapper.constant.MapperConstant;
import elca.ntig.partnerapp.common.proto.enums.organisation.CodeNOGAProto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CodeNOGAMapper {
    @ValueMapping(source = MappingConstants.NULL, target = MapperConstant.NULL_CODE_NOGA)
    CodeNOGAProto toCodeNOGAProto(CodeNOGA codeNOGA);

    @ValueMapping(source = MapperConstant.UNRECOGNIZED, target = MappingConstants.THROW_EXCEPTION)
    @ValueMapping(source = MapperConstant.NULL_CODE_NOGA, target = MappingConstants.NULL)
    CodeNOGA toCodeNOGA(CodeNOGAProto codeNOGAProto);
}
