package elca.ntig.partnerapp.be.utils.converter;

import elca.ntig.partnerapp.be.model.enums.person.Nationality;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class NationalityConverter implements AttributeConverter<Nationality, String> {

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
            return Nationality.valueOf(dbData); // Converts string back to enum (e.g., "CH" -> Nationality.CH)
        } catch (IllegalArgumentException e) {
            // Handle the case where the enum constant is not found
            throw new IllegalArgumentException("Invalid nationality value: " + dbData);
        }
    }
}
