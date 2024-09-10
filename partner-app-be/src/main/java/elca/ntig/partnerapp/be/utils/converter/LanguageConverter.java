package elca.ntig.partnerapp.be.utils.converter;

import elca.ntig.partnerapp.be.model.enums.partner.Language;
import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;
import org.apache.log4j.Logger;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LanguageConverter implements AttributeConverter<Language, String> {
    private static Logger logger = Logger.getLogger(LanguageConverter.class);

    @Override
    public String convertToDatabaseColumn(Language languageEnum) {
        if (languageEnum == null) {
            return null;
        }
        return languageEnum.getCode();
    }

    @Override
    public Language convertToEntityAttribute(String data) {
        if (data == null || data.trim().isEmpty()) {
            return null;
        }
        try {
            return Language.toEnumConstant(data);
        } catch (ResourceNotFoundException e) {
            logger.error("Bugs coming when convert Language: " + e);
            return null;
        }
    }
}
