package elca.ntig.partnerapp.be.model.enums.person;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SexEnum {
    MALE("M"),
    FEMALE("F");

    private final String code;
}
