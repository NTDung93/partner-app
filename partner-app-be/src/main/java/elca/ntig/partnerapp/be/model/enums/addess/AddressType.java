package elca.ntig.partnerapp.be.model.enums.addess;

import elca.ntig.partnerapp.be.model.enums.partner.Language;
import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum AddressType {
    MAIN("PRINCIP"),
    SECONDARY("SECOND"),
    CORRESPONDENCE("CORRESP");

    private final String code;

    public static AddressType toEnumConstant(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }

        return Arrays.stream(AddressType.values())
                .filter(addressType -> addressType.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("AddressType", "code", code));
    }
}
