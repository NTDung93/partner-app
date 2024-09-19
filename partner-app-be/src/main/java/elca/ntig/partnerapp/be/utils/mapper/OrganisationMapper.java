package elca.ntig.partnerapp.be.utils.mapper;

import elca.ntig.partnerapp.be.model.dto.organisation.OrganisationResponseDto;
import elca.ntig.partnerapp.be.model.dto.organisation.SearchOrganisationCriteriasDto;
import elca.ntig.partnerapp.be.model.dto.organisation.SearchOrganisationPaginationResponseDto;
import elca.ntig.partnerapp.be.model.entity.Organisation;
import elca.ntig.partnerapp.be.utils.mapper.enums.*;
import elca.ntig.partnerapp.common.proto.entity.organisation.OrganisationResponseProto;
import elca.ntig.partnerapp.common.proto.entity.organisation.SearchOrganisationCriteriasProto;
import elca.ntig.partnerapp.common.proto.entity.organisation.SearchOrganisationPaginationResponseProto;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

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
}
