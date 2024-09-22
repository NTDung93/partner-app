package elca.ntig.partnerapp.be.utils.converter.address;

import elca.ntig.partnerapp.be.model.enums.addess.CantonAbbr;
import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CantonConverter implements AttributeConverter<CantonAbbr, String> {
    @Override
    public String convertToDatabaseColumn(CantonAbbr canton) {
        if (canton == null) {
            return null;
        }
        return canton.getCode();
    }

    @Override
    public CantonAbbr convertToEntityAttribute(String data) {
        if (data == null || data.trim().isEmpty()) {
            return null;
        }
        try {
            return CantonAbbr.toEnumConstant(data);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }
}
