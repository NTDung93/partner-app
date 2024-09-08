package elca.ntig.partnerapp.be.model.enums.addess;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AddressType {
    MAIN("PRINCIP"),
    SECONDARY("SECOND"),
    CORRESPONDENCE("CORRESP");

    private final String code;
}
