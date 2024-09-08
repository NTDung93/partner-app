package elca.ntig.partnerapp.be.utils.converter;

import elca.ntig.partnerapp.be.model.enums.person.Nationality;
import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;
import org.apache.log4j.Logger;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class NationalityConverter implements AttributeConverter<Nationality, String> {
    private static Logger logger = Logger.getLogger(NationalityConverter.class);

    @Override
    public String convertToDatabaseColumn(Nationality attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public Nationality convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        try {
            return Nationality.toEnumConstant(dbData);
        } catch (ResourceNotFoundException e) {
            logger.error("Bugs coming: " + e);
            return null;
        }
    }
}
