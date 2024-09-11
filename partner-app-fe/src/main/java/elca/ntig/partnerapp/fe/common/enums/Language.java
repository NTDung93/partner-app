package elca.ntig.partnerapp.fe.common.enums;

import java.util.Locale;

public enum Language {
    FR(Locale.FRENCH), EN(Locale.ENGLISH);
    private Locale locale;

    Language(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }
}
