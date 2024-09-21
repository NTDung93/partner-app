package elca.ntig.partnerapp.be.utils.converter.person;

import elca.ntig.partnerapp.be.model.enums.person.Nationality;
import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class NationalityConverter implements AttributeConverter<Nationality, String> {

    @Override
    public String convertToDatabaseColumn(Nationality nationalityEnum) {
        if (nationalityEnum == null) {
            return null;
        }
        return nationalityEnum.getCode();
    }

    @Override
    public Nationality convertToEntityAttribute(String data) {
        if (data == null || data.trim().isEmpty()) {
            return null;
        }
        try {
            return Nationality.toEnumConstant(data);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }
}
