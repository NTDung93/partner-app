package elca.ntig.partnerapp.be.model.enums.person;

import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum SexEnum {
    MALE("M"),
    FEMALE("F"),
    NULL_SEX_ENUM(null);

    private final String code;

    public static SexEnum toEnumConstant(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }

        return Arrays.stream(SexEnum.values())
                .filter(sexEnum -> sexEnum.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("SexEnum", "code", code));
    }
}
