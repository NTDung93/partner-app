package elca.ntig.partnerapp.fe.common.dialog;

import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
import elca.ntig.partnerapp.fe.utils.ObservableResourceFactory;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

public class DialogBuilder {
    private final ObservableResourceFactory observableResourceFactory;

    public DialogBuilder(ObservableResourceFactory observableResourceFactory) {
        this.observableResourceFactory = observableResourceFactory;
    }

    public Alert buildAlert(Alert.AlertType alertType, String title, String header, String message) {
        Alert alertDialog = new Alert(alertType);
        alertDialog.setTitle(observableResourceFactory.getStringBinding(title).get());
        alertDialog.setHeaderText(StringUtils.isNotBlank(header) ? observableResourceFactory.getStringBinding(header).get() : null);
        alertDialog.setContentText(StringUtils.isNotBlank(message) ? observableResourceFactory.getStringBinding(message).get() : null);
        if (header.contains("delete")){
            final ImageView questionIcon = new ImageView(new Image(getClass().getResourceAsStream(ResourceConstant.QUESTION_ICON)));
            {
                questionIcon.setFitHeight(80);
                questionIcon.setFitWidth(80);
            }
            alertDialog.setGraphic(questionIcon);

            Stage dialogStage = (Stage) alertDialog.getDialogPane().getScene().getWindow();
            final ImageView alertIcon = new ImageView(new Image(getClass().getResourceAsStream(ResourceConstant.BIN_ICON)));
            {
                alertIcon.setFitHeight(20);
                alertIcon.setFitWidth(20);
            }
            dialogStage.getIcons().add(alertIcon.getImage());
        } else if (header.contains("success")) {
            final ImageView questionIcon = new ImageView(new Image(getClass().getResourceAsStream(ResourceConstant.SUCCESS_ICON)));
            {
                questionIcon.setFitHeight(80);
                questionIcon.setFitWidth(80);
            }
            alertDialog.setGraphic(questionIcon);

            Stage dialogStage = (Stage) alertDialog.getDialogPane().getScene().getWindow();
            final ImageView alertIcon = new ImageView(new Image(getClass().getResourceAsStream(ResourceConstant.INFO_ICON)));
            {
                alertIcon.setFitHeight(20);
                alertIcon.setFitWidth(20);
            }
            dialogStage.getIcons().add(alertIcon.getImage());
        }
        return alertDialog;
    }
}
