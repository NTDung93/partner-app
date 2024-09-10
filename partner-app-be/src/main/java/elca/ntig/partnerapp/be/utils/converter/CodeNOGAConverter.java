package elca.ntig.partnerapp.be.utils.converter;

import elca.ntig.partnerapp.be.model.enums.organisation.CodeNOGA;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;
import org.apache.log4j.Logger;

@Converter(autoApply = true)
public class CodeNOGAConverter implements AttributeConverter<CodeNOGA, String> {
    private static Logger logger = Logger.getLogger(CodeNOGAConverter.class);

    @Override
    public String convertToDatabaseColumn(CodeNOGA codeNOGA) {
        if (codeNOGA == null) {
            return null;
        }
        return codeNOGA.toString().substring(5);
    }

    @Override
    public CodeNOGA convertToEntityAttribute(String data) {
        if (data == null || data.trim().isEmpty()) {
            return null;
        }
        try {
            return CodeNOGA.toEnumConstant(data);
        } catch (ResourceNotFoundException e) {
            logger.error("Bugs coming when convert CodeNOGA: " + e);
            return null;
        }
    }
}
