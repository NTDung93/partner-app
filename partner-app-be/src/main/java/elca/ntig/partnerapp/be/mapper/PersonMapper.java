package elca.ntig.partnerapp.be.mapper;

import elca.ntig.partnerapp.be.model.dto.person.PersonResponse;
import elca.ntig.partnerapp.be.model.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper {
    @Mapping(source = "partner.language", target = "language")
    @Mapping(source = "partner.phoneNumber", target = "phoneNumber")
    @Mapping(source = "partner.status", target = "status")
    PersonResponse toPersonResponse(Person person);
}
