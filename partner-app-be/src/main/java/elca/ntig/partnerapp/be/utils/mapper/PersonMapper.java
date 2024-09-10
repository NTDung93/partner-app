package elca.ntig.partnerapp.be.utils.mapper;


import elca.ntig.partnerapp.be.model.dto.person.PersonResponseDto;
import elca.ntig.partnerapp.be.model.dto.person.SearchPeopleCriteriasDto;
import elca.ntig.partnerapp.be.model.dto.person.SearchPeoplePaginationResponseDto;
import elca.ntig.partnerapp.be.model.entity.Person;
import elca.ntig.partnerapp.be.utils.mapper.enums.LanguageMapper;
import elca.ntig.partnerapp.be.utils.mapper.enums.NationalityMapper;
import elca.ntig.partnerapp.be.utils.mapper.enums.SexEnumMapper;
import org.mapstruct.CollectionMappingStrategy;
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
                SexEnumMapper.class,
                NationalityMapper.class
        },
        collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED
)
public interface PersonMapper {
    @Mapping(source = "partner.language", target = "language")
    @Mapping(source = "partner.phoneNumber", target = "phoneNumber")
    @Mapping(source = "partner.status", target = "status")
    PersonResponseDto toPersonResponseDto(Person person);

    @Mapping(source = "birthDate", target = "birthDate", qualifiedByName = "mapLocalDateToString")
    PersonResponseProto toPersonResponseProto(PersonResponseDto personResponseDto);

    @Mapping(source = "birthDate", target = "birthDate", qualifiedByName = "mapStringToLocalDate")
    SearchPeopleCriteriasDto toSearchPeopleCriteriasDto(SearchPeopleCriteriasProto searchPeopleCriteriasDto);

    @Mapping(source = "content", target = "contentList")
    SearchPeoplePaginationResponseProto toSearchPeoplePaginationResponse(SearchPeoplePaginationResponseDto searchPeoplePaginationResponseDto);
}
