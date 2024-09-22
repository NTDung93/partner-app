package elca.ntig.partnerapp.be.utils.converter.organisation;

import elca.ntig.partnerapp.be.model.enums.organisation.CodeNOGA;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;

@Converter(autoApply = true)
public class CodeNOGAConverter implements AttributeConverter<CodeNOGA, String> {

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
            return null;
        }
    }
}
