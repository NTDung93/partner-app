package elca.ntig.partnerapp.be.utils.mapper.enums;

import elca.ntig.partnerapp.be.utils.mapper.constant.MapperConstant;
import elca.ntig.partnerapp.common.proto.enums.organisation.LegalStatusProto;
import elca.ntig.partnerapp.be.model.enums.organisation.LegalStatus;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface LegalStatusMapper {
    @ValueMapping(source = MappingConstants.NULL, target = MapperConstant.NULL_LEGAL_STATUS)
    LegalStatusProto toLegalStatusProto(LegalStatus legalStatus);

    @ValueMapping(source = MapperConstant.UNRECOGNIZED, target = MappingConstants.THROW_EXCEPTION)
    @ValueMapping(source = MapperConstant.NULL_LEGAL_STATUS, target = MappingConstants.NULL)
    LegalStatus toLegalStatus(LegalStatusProto legalStatusProto);
}
