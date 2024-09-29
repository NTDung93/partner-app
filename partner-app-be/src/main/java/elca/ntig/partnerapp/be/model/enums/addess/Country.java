package elca.ntig.partnerapp.be.model.enums.addess;

import elca.ntig.partnerapp.be.model.enums.partner.Language;
import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Country {
    COUNTRY_SWITZERLAND("CH"),
    COUNTRY_GERMANY("DE"),
    COUNTRY_SPAIN("ES"),
    COUNTRY_FRANCE("FR"),
    COUNTRY_UNITED_KINGDOM("GB"),
    COUNTRY_ITALY("IT");

    private final String code;

    public static Country toEnumConstant(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }

        return Arrays.stream(Country.values())
                .filter(country -> country.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Country", "code", code));
    }
}
