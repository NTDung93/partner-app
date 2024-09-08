package elca.ntig.partnerapp.be.model.enums.organisation;

import elca.ntig.partnerapp.be.model.enums.person.SexEnum;
import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CodeNOGA {
    NOGA_011100,
    NOGA_011200,
    NOGA_011400,
    NOGA_011500,
    NOGA_011900,
    NOGA_012101,
    NOGA_013000,
    NOGA_014200,
    NOGA_014700,
    NOGA_016300,
    NOGA_021000,
    NOGA_024000,
    NOGA_031200,
    NOGA_032100,
    NOGA_032200,
    NOGA_051000,
    NOGA_052000,
    NOGA_061000,
    NOGA_062000,
    NOGA_071000;

    public static CodeNOGA toEnumConstant(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }

        return Arrays.stream(CodeNOGA.values())
                .filter(sexEnum -> sexEnum.toString().equals("NOGA_" + code))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("SexEnum", "code", code));
    }
}
