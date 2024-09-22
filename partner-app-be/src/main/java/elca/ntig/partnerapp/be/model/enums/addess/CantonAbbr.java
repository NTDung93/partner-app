package elca.ntig.partnerapp.be.model.enums.addess;

import elca.ntig.partnerapp.be.model.enums.partner.Language;
import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CantonAbbr {
    AARGAU("AG"),
    APPENZELL_INNER_RHODES("AI"),
    APPENZELL_OUTER_RHODES("AR"),
    BERN("BE"),
    BASEL_COUNTRY("BL"),
    BASEL_CITY("BS"),
    FRIBOURG("FR"),
    GENEVA("GE"),
    GLARUS("GL"),
    GRISONS("GR"),
    HJURA("JU"),
    LUCERNE("LU"),
    NEUCHATEL("NE"),
    NIDWALD("NW"),
    OBWALD("OW"),
    ST_GALL("SG"),
    SCHAFFHOUSE("SH"),
    SOLOTHURN("SO"),
    SCHWYZ("SZ"),
    THURGAU("TG"),
    TESSIN("TI"),
    URI("UR"),
    VAUD("VD"),
    VALAIS("VS"),
    ZUG("ZG"),
    ZURICH("ZH");

    private final String code;

    public static CantonAbbr toEnumConstant(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }

        return Arrays.stream(CantonAbbr.values())
                .filter(canton -> canton.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Canton", "code", code));
    }
}
