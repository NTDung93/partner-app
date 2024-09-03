package elca.ntig.partnerapp.be.model.enums.addess;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AddressType {
    HOME("Home"),
    WORK("Work"),
    OTHER("Other");

    private final String value;
}
