package elca.ntig.partnerapp.be.utils.mapper;


import elca.ntig.partnerapp.be.model.dto.partner.DeletePartnerResponseDto;
import elca.ntig.partnerapp.be.model.dto.person.PersonResponseDto;
import elca.ntig.partnerapp.be.model.dto.person.SearchPeopleCriteriasDto;
import elca.ntig.partnerapp.be.model.dto.person.SearchPeoplePaginationResponseDto;
import elca.ntig.partnerapp.be.model.dto.person.UpdatePersonRequestDto;
import elca.ntig.partnerapp.be.model.entity.Person;
import elca.ntig.partnerapp.be.utils.mapper.enums.LanguageMapper;
import elca.ntig.partnerapp.be.utils.mapper.enums.NationalityMapper;
import elca.ntig.partnerapp.be.utils.mapper.enums.SexEnumMapper;
import elca.ntig.partnerapp.be.utils.mapper.enums.StatusMapper;
import elca.ntig.partnerapp.common.proto.entity.person.DeletePersonResponseProto;
import org.mapstruct.*;
import elca.ntig.partnerapp.common.proto.entity.person.PersonResponseProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeopleCriteriasProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationResponseProto;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {
                DateMapper.class,
                LanguageMapper.class,
                SexEnumMapper.class,
                NationalityMapper.class,
                StatusMapper.class
        },
        collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED
)
public interface PersonMapper {
    @Mapping(target = "partner.language", source = "language")
    @Mapping(target = "partner.phoneNumber", source = "phoneNumber")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "partner", ignore = true)
    void updatePerson(UpdatePersonRequestDto updatePersonRequestDto, @MappingTarget Person person);

    @Mapping(source = "partner.language", target = "language")
    @Mapping(source = "partner.phoneNumber", target = "phoneNumber")
    @Mapping(source = "partner.status", target = "status")
    PersonResponseDto toPersonResponseDto(Person person);

    @Mapping(source = "birthDate", target = "birthDate", qualifiedByName = "mapLocalDateToString")
    PersonResponseProto toPersonResponseProto(PersonResponseDto personResponseDto);

    @Mapping(source = "statusList", target = "status")
    @Mapping(source = "birthDate", target = "birthDate", qualifiedByName = "mapStringToLocalDate")
    SearchPeopleCriteriasDto toSearchPeopleCriteriasDto(SearchPeopleCriteriasProto searchPeopleCriteriasProto);

    @Mapping(source = "content", target = "contentList")
    SearchPeoplePaginationResponseProto toSearchPeoplePaginationResponse(SearchPeoplePaginationResponseDto searchPeoplePaginationResponseDto);

    DeletePersonResponseProto toDeletePersonResponseProto(DeletePartnerResponseDto deletePersonResponseDto);
}
