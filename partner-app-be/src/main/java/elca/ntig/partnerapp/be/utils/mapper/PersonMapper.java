package elca.ntig.partnerapp.be.utils.mapper;


import elca.ntig.partnerapp.be.model.dto.person.PersonResponseDto;
import elca.ntig.partnerapp.be.model.dto.person.SearchPeopleCriteriasDto;
import elca.ntig.partnerapp.be.model.dto.person.SearchPeoplePaginationResponseDto;
import elca.ntig.partnerapp.be.model.entity.Person;
import elca.ntig.partnerapp.be.utils.mapper.enums.LanguageMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import elca.ntig.partnerapp.common.proto.entity.person.PersonResponseProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeopleCriteriasProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationResponseProto;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {
                DateMapper.class,
                LanguageMapper.class,
        }
)
public interface PersonMapper {
    @Mapping(source = "partner.language", target = "language")
    @Mapping(source = "partner.phoneNumber", target = "phoneNumber")
    @Mapping(source = "partner.status", target = "status")
    PersonResponseDto toPersonResponseDto(Person person);

    @Mapping(source = "birthDate", target = "birthDate", qualifiedByName = "mapLocalDateToString")
    PersonResponseProto toPersonResponseProto(PersonResponseDto personResponseDto);

    SearchPeopleCriteriasDto toSearchPeopleCriteriasDto(SearchPeopleCriteriasProto searchPeopleCriteriasDto);

    SearchPeoplePaginationResponseProto toSearchPeoplePaginationResponse(SearchPeoplePaginationResponseDto searchPeoplePaginationResponseDto);
}
