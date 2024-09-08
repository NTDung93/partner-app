package elca.ntig.partnerapp.be.utils.converter;

import elca.ntig.partnerapp.be.model.enums.person.SexEnum;
import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;
import org.apache.log4j.Logger;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SexEnumConverter implements AttributeConverter<SexEnum, String> {
    private static Logger logger = Logger.getLogger(SexEnumConverter.class);

    @Override
    public String convertToDatabaseColumn(SexEnum sexEnum) {
        if (sexEnum == null) {
            return null;
        }
        return sexEnum.getCode();
    }

    @Override
    public SexEnum convertToEntityAttribute(String data) {
        if (data == null || data.trim().isEmpty()) {
            return null;
        }
        try {
            return SexEnum.toEnumConstant(data);
        } catch (ResourceNotFoundException e) {
            logger.error("Bugs coming when convert SexEnum: " + e);
            return null;
        }
    }
}
