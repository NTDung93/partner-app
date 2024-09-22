package elca.ntig.partnerapp.be.utils.mapper;

import elca.ntig.partnerapp.be.model.dto.organisation.*;
import elca.ntig.partnerapp.be.model.dto.partner.DeletePartnerResponseDto;
import elca.ntig.partnerapp.be.model.entity.Organisation;
import elca.ntig.partnerapp.be.utils.mapper.enums.*;
import elca.ntig.partnerapp.common.proto.entity.organisation.*;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {
                DateMapper.class,
                LanguageMapper.class,
                StatusMapper.class,
                LegalStatusMapper.class,
                CodeNOGAMapper.class,
        },
        collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED
)
public interface OrganisationMapper {
    @Mapping(target = "partner.language", source = "language")
    @Mapping(target = "partner.phoneNumber", source = "phoneNumber")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "partner", ignore = true)
    void updateOrganisation(UpdateOrganisationRequestDto updateOrganisationRequestDto, @MappingTarget Organisation organisation);

    @Mapping(source = "partner.language", target = "language")
    @Mapping(source = "partner.phoneNumber", target = "phoneNumber")
    @Mapping(source = "partner.status", target = "status")
    OrganisationResponseDto toOrganisationResponseDto(Organisation organisation);

    @Mapping(source = "creationDate", target = "creationDate", qualifiedByName = "mapLocalDateToString")
    OrganisationResponseProto toOrganisationResponseProto(OrganisationResponseDto organisationResponseDto);

    @Mapping(source = "statusList", target = "status")
    @Mapping(source = "creationDate", target = "creationDate", qualifiedByName = "mapStringToLocalDate")
    SearchOrganisationCriteriasDto toSearchOrganisationCriteriasDto(SearchOrganisationCriteriasProto searchOrganisationCriteriasProto);

    @Mapping(source = "content", target = "contentList")
    SearchOrganisationPaginationResponseProto toSearchOrganisationPaginationResponse(SearchOrganisationPaginationResponseDto searchOrganisationPaginationResponseDto);

    DeleteOrganisationResponseProto toDeleteOrganisationResponseProto(DeletePartnerResponseDto deleteOrganisationResponseDto);

    @Mapping(source = "creationDate", target = "creationDate", qualifiedByName = "mapStringToLocalDate")
    CreateOrganisationRequestDto toCreateOrganisationRequestDto(CreateOrganisationRequestProto request);

    @Mapping(source = "creationDate", target = "creationDate", qualifiedByName = "mapStringToLocalDate")
    UpdateOrganisationRequestDto toUpdateOrganisationRequestDto(UpdateOrganisationRequestProto request);
}
