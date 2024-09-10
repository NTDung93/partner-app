package elca.ntig.partnerapp.be.utils.mapper.enums;

import elca.ntig.partnerapp.be.model.enums.partner.Language;
import elca.ntig.partnerapp.be.utils.mapper.constant.MapperConstant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import elca.ntig.partnerapp.common.proto.enums.partner.LanguageProto;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ValueMapping;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface LanguageMapper {

    @ValueMapping(target = MapperConstant.NULL_LANGUAGE, source = MappingConstants.NULL)
    LanguageProto toLanguageProto(Language language);

    @ValueMapping(target = MappingConstants.THROW_EXCEPTION, source = MapperConstant.PROTO_UNRECOGNIZED)
    @ValueMapping(target = MappingConstants.NULL, source = MapperConstant.NULL_LANGUAGE)
    Language toLanguage(LanguageProto languageProto);
}
