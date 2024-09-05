package elca.ntig.partnerapp.be.model.enums.addess;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Country {
    SWITZERLAND("CH"),
    GERMANY("DE"),
    SPAIN("ES"),
    FRANCE("FR"),
    UNITED_KINGDOM("GB"),
    ITALY("IT");

    private final String code;
}
