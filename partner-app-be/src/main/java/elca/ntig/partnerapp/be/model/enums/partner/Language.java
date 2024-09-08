package elca.ntig.partnerapp.be.model.enums.partner;

import elca.ntig.partnerapp.be.model.enums.person.SexEnum;
import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum Language {
    LANG_DEUTSCH("DE"),
    LANG_ENGLISH("EN"),
    LANG_FRENCH("FR"),
    LANG_ITALIAN("IT");

    private final String code;

    public static Language toEnumConstant(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }

        return Arrays.stream(Language.values())
                .filter(sexEnum -> sexEnum.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("SexEnum", "code", code));
    }
}
