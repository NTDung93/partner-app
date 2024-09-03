package elca.ntig.partnerapp.be.model.enums.addess;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CantonAbbr {
    // France (http://www.statoids.com/yfr.html)
    AIN("AI"),
    CHARENTE("CT"),
    GIRONDE("GI"),

    // Germany (https://en.wikipedia.org/wiki/States_of_Germany)
    BADEN_WURTTEMBERG("BW"),
    BAVARIA("BY"),
    BERLIN("BE"),

    // Switzerland (https://en.wikipedia.org/wiki/Cantons_of_Switzerland)
    ZURICH("ZH"),
    BERN("BE"),
    GENEVA("GE");

    private final String value;
}
