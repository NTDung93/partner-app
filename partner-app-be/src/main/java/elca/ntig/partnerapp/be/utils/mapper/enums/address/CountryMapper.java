package elca.ntig.partnerapp.be.utils.mapper.enums.address;

import elca.ntig.partnerapp.be.model.enums.addess.Country;
import elca.ntig.partnerapp.be.utils.mapper.constant.MapperConstant;
import elca.ntig.partnerapp.common.proto.enums.address.CountryProto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CountryMapper {
    @ValueMapping(target = MapperConstant.NULL_COUNTRY, source = MappingConstants.NULL)
    CountryProto toCountryProto(Country country);

    @ValueMapping(target = MappingConstants.THROW_EXCEPTION, source = MapperConstant.UNRECOGNIZED)
    @ValueMapping(target = MappingConstants.NULL, source = MapperConstant.NULL_COUNTRY)
    Country toCountry(CountryProto countryProto);
}
