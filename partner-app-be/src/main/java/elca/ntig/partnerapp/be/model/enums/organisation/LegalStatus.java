package elca.ntig.partnerapp.be.model.enums.organisation;

import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum LegalStatus {
    SIMPLE_COMPANY("S_SIMPLE"),
    GENERAL_PARTNERSHIP("S_NOM_COL"),
    LIMITED_PARTNERSHIP("S_C_SIMPLE"),
    HEREDITARY_COMMUNITY("COMHEREDIT"),
    LIMITED_COMPANY("S_ANON"),
    LIMITED_LIABILITY_COMPANY("S_RESP_L"),
    COOPERATIVE_SOCIETY("S_COOP"),
    PARTNERSHIP_BY_SHARES("S_C_ACTION"),
    ASSOCIATION("ASSOC"),
    FOUNDATION("FOUNDATION"),
    PUBLIC_LAW_CORPORATION("CORP_PUB");

    private final String code;

    public static LegalStatus toEnumConstant(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }

        return Arrays.stream(LegalStatus.values())
                .filter(legalStatus -> legalStatus.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("LegalStatus", "code", code));
    }
}
