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
            logger.error("Bugs coming when convert Nationality: " + e);
            return null;
        }
    }
}
