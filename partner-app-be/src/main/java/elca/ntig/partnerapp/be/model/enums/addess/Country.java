package elca.ntig.partnerapp.be.model.enums.addess;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Country {
    FRANCE("FR"),
    GERMANY("DE"),
    SWITZERLAND("CH");

    private final String value;
}
