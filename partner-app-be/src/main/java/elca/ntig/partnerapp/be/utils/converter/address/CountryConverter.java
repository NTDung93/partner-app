package elca.ntig.partnerapp.be.utils.converter.address;

import elca.ntig.partnerapp.be.model.enums.addess.Country;
import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CountryConverter implements AttributeConverter<Country, String> {

    @Override
    public String convertToDatabaseColumn(Country country) {
        if (country == null) {
            return null;
        }
        return country.getCode();
    }

    @Override
    public Country convertToEntityAttribute(String data) {
        if (data == null || data.trim().isEmpty()) {
            return null;
        }
        try {
            return Country.toEnumConstant(data);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }
}
