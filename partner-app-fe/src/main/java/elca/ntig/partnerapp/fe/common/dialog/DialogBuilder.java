package elca.ntig.partnerapp.fe.common.dialog;

import elca.ntig.partnerapp.fe.utils.ObservableResourceFactory;
import javafx.scene.control.Alert;
import org.apache.commons.lang3.StringUtils;

public class DialogBuilder {
    private final ObservableResourceFactory observableResourceFactory;

    public DialogBuilder(ObservableResourceFactory observableResourceFactory) {
        this.observableResourceFactory = observableResourceFactory;
    }

    public Alert buildAlert(Alert.AlertType alertType, String title, String message) {
        Alert alertDialog = new Alert(alertType);
        alertDialog.setTitle(getLocalizedString(title));
        if (!message.contains("Dialog")) {
            alertDialog.setHeaderText(message);
        } else {
            alertDialog.setHeaderText(StringUtils.isNotBlank(message) ? getLocalizedString(message) : null);
        }

        return alertDialog;
    }

    private String getLocalizedString(String key) {
        return observableResourceFactory.getStringBinding(key).get();
    }
}
