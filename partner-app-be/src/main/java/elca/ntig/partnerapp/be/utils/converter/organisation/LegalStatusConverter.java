package elca.ntig.partnerapp.be.utils.converter.organisation;

import elca.ntig.partnerapp.be.model.enums.organisation.LegalStatus;
import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LegalStatusConverter implements AttributeConverter<LegalStatus, String> {

    @Override
    public String convertToDatabaseColumn(LegalStatus legalStatus) {
        if (legalStatus == null) {
            return null;
        }
        return legalStatus.getCode();
    }

    @Override
    public LegalStatus convertToEntityAttribute(String data) {
        if (data == null || data.trim().isEmpty()) {
            return null;
        }
        try {
            return LegalStatus.toEnumConstant(data);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }
}
