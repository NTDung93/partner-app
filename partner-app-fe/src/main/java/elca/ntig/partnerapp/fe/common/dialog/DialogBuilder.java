package elca.ntig.partnerapp.fe.common.dialog;

import elca.ntig.partnerapp.fe.utils.ObservableResourceFactory;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public final class DialogBuilder {
    @Autowired
    public static ObservableResourceFactory observableResourceFactory;

    private DialogBuilder() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static Alert buildAlert(Alert.AlertType alertType, String title, String header, String message) {
        Alert alertDialog = new Alert(alertType);

        alertDialog.setTitle(observableResourceFactory.getStringBinding(title).get());

        if (StringUtils.isNotBlank(header)) {
            alertDialog.setHeaderText(observableResourceFactory.getStringBinding(header).get());
        } else {
            alertDialog.setHeaderText(null);
        }

        alertDialog.setContentText(observableResourceFactory.getStringBinding(message).get());

        Stage dialogStage = (Stage) alertDialog.getDialogPane().getScene().getWindow();
        Image icon = new Image(Objects.requireNonNull(DialogBuilder.class.getResource("/images/alert.png"))
                .toExternalForm());
        dialogStage.getIcons().add(icon);

        return alertDialog;
    }
}
