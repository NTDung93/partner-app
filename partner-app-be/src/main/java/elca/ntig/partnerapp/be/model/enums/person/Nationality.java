package elca.ntig.partnerapp.be.model.enums.person;

import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum Nationality {
    NATIONALITY_SWITZERLAND("CH"),
    NATIONALITY_GERMAN("DE"),
    NATIONALITY_SPANISH("ES"),
    NATIONALITY_FRENCH("FR"),
    NATIONALITY_BRITISH("GB"),
    NATIONALITY_ITALIAN("IT");

    private final String code;

    public static Nationality toEnumConstant(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }

        return Arrays.stream(Nationality.values())
                .filter(nationality -> nationality.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Nationality", "code", code));
    }
}
