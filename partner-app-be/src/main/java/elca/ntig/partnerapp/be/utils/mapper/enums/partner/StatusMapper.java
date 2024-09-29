package elca.ntig.partnerapp.be.utils.mapper.enums.partner;

import elca.ntig.partnerapp.be.model.enums.common.Status;
import elca.ntig.partnerapp.be.utils.mapper.constant.MapperConstant;
import elca.ntig.partnerapp.common.proto.enums.common.StatusProto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface StatusMapper {
    @ValueMapping(target = MapperConstant.NULL_STATUS, source = MappingConstants.NULL)
    StatusProto toStatusProto(Status status);

    @ValueMapping(target = MappingConstants.THROW_EXCEPTION, source = MapperConstant.UNRECOGNIZED)
    @ValueMapping(target = MappingConstants.NULL, source = MapperConstant.NULL_STATUS)
    Status toStatus(StatusProto statusProto);
}
