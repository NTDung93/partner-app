package elca.ntig.partnerapp.be.model.enums.organisation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CodeNOGA {
    CODE_NOGA_021000("SILVICULTURE"),
    CODE_NOGA_031100("FISHING"),
    CODE_NOGA_451101("CAR_SALES"),
    CODE_NOGA_631200("WEB_PORTAL");

    private final String description;
}
