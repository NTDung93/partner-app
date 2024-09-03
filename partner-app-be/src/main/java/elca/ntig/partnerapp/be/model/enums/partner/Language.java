package elca.ntig.partnerapp.be.model.enums.partner;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Language {
    ENGLISH("EN"),
    FRENCH("FR");

    private final String code;
}
