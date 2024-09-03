package elca.ntig.partnerapp.be.model.enums.person;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Nationality {
    FRANCE("FR"),
    GERMANY("DE"),
    SWITZERLAND("CH");

    private final String code;
}
