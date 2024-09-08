package elca.ntig.partnerapp.be.model.enums.person;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
}
