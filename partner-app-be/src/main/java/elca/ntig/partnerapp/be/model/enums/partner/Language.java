package elca.ntig.partnerapp.be.model.enums.partner;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Language {
    DEUTSCH("DE"),
    ENGLISH("EN"),
    FRENCH("FR"),
    ITALIAN("IT");

    private final String code;
}
