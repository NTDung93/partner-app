package elca.ntig.partnerapp.fe.common.cell;

import elca.ntig.partnerapp.fe.utils.ObservableResourceFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class LocalizedStringCell {
    private final ObservableResourceFactory observableResourceFactory;

    public LocalizedStringCell(ObservableResourceFactory observableResourceFactory) {
        this.observableResourceFactory = observableResourceFactory;
    }

    public String getLocalizedString(String resourcePrefix, String item) {
        if (StringUtils.isBlank(item) || item.contains("NULL_")) {
            return "";
        }

        int subStringIndex = 0;
        if (needsSubStringIndex(resourcePrefix)) {
            subStringIndex = item.indexOf("_") + 1;
        }

        String value = item.substring(subStringIndex).toLowerCase();

        return observableResourceFactory.getStringBinding(resourcePrefix + value).get();
    }

    private boolean needsSubStringIndex(String resourcePrefix) {
        return !resourcePrefix.equals("Enum.legalStatus.")
                && !resourcePrefix.equals("Enum.sex.")
                && !resourcePrefix.equals("Enum.marital.")
                && !resourcePrefix.equals("Enum.cantonAbbr.");
    }
}
