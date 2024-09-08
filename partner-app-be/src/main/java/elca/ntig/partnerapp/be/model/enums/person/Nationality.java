package elca.ntig.partnerapp.be.model.enums.person;

import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum Nationality {
    SWITZERLAND("CH"),
    GERMAN("DE"),
    SPANISH("ES"),
    FRENCH("FR"),
    BRITISH("GB"),
    ITALIAN("IT");

    private final String code;

    public static Nationality toEnumConstant(String code) {
        if (code == null || code.isEmpty()) {
//            throw new IllegalArgumentException("Nationality must not be empty!");
            return null;
        }

        Nationality[] enumConstants = Nationality.values();
        List<Nationality> nationalityList = Arrays.asList(enumConstants);

        return nationalityList.stream()
                .filter(nationality -> nationality.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Nationality", "code", code));
    }
}
