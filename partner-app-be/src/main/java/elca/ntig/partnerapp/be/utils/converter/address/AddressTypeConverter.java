package elca.ntig.partnerapp.be.utils.converter.address;

import elca.ntig.partnerapp.be.model.enums.addess.AddressType;
import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AddressTypeConverter implements AttributeConverter<AddressType, String> {

    @Override
    public String convertToDatabaseColumn(AddressType addressType) {
        if (addressType == null) {
            return null;
        }
        return addressType.getCode();
    }

    @Override
    public AddressType convertToEntityAttribute(String data) {
        if (data == null || data.trim().isEmpty()) {
            return null;
        }
        try {
            return AddressType.toEnumConstant(data);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }
}
