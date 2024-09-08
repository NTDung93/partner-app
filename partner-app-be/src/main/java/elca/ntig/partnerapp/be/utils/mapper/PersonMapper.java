package elca.ntig.partnerapp.be.utils.mapper;

import elca.ntig.partnerapp.be.model.dto.person.PersonResponseDto;
import elca.ntig.partnerapp.be.model.entity.Person;
import elca.ntig.partnerapp.common.proto.entity.person.PersonResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = DateMapper.class)
public interface PersonMapper {
    @Mapping(source = "partner.language", target = "language")
    @Mapping(source = "partner.phoneNumber", target = "phoneNumber")
    @Mapping(source = "partner.status", target = "status")
    PersonResponseDto toPersonResponseDto(Person person);

    @Mapping(source = "birthDate", target = "birthDate", qualifiedByName = "mapLocalDateToTimestamp")
    PersonResponse toPersonResponse(PersonResponseDto personResponseDto);
}
