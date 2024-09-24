package elca.ntig.partnerapp.be.utils.mapper;


import elca.ntig.partnerapp.be.model.dto.partner.DeletePartnerResponseDto;
import elca.ntig.partnerapp.be.model.dto.person.*;
import elca.ntig.partnerapp.be.model.entity.Person;
import elca.ntig.partnerapp.be.utils.mapper.enums.partner.LanguageMapper;
import elca.ntig.partnerapp.be.utils.mapper.enums.partner.StatusMapper;
import elca.ntig.partnerapp.be.utils.mapper.enums.person.MaritalStatusMapper;
import elca.ntig.partnerapp.be.utils.mapper.enums.person.NationalityMapper;
import elca.ntig.partnerapp.be.utils.mapper.enums.person.SexEnumMapper;
import elca.ntig.partnerapp.common.proto.entity.person.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {
                DateMapper.class,
                LanguageMapper.class,
                SexEnumMapper.class,
                NationalityMapper.class,
                MaritalStatusMapper.class,
                StatusMapper.class
        },
        collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public interface PersonMapper {
    public static PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

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

    @Mapping(source = "birthDate", target = "birthDate", qualifiedByName = "mapStringToLocalDate")
    CreatePersonRequestDto toCreatePersonRequestDto(CreatePersonRequestProto createPersonRequestProto);

    @Mapping(source = "birthDate", target = "birthDate", qualifiedByName = "mapStringToLocalDate")
    UpdatePersonRequestDto toUpdatePersonRequestDto(UpdatePersonRequestProto request);
}
