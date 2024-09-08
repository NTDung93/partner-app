package elca.ntig.partnerapp.be.model.enums.person;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

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

    public static Nationality fromCode(String code){
        return Arrays.stream(Nationality.values())
                .filter(nationality -> nationality.getCode().equals(code))
                .findFirst().get();
    }
}
