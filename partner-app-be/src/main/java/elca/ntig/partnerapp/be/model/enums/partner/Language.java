package elca.ntig.partnerapp.be.model.enums.partner;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Language {
    LANG_DEUTSCH("DE"),
    LANG_ENGLISH("EN"),
    LANG_FRENCH("FR"),
    LANG_ITALIAN("IT");

    private final String code;
}
