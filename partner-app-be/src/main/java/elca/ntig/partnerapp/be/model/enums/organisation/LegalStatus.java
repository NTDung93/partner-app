package elca.ntig.partnerapp.be.model.enums.organisation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
}
