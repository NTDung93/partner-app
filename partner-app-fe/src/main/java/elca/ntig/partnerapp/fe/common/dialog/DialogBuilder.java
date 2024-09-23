package elca.ntig.partnerapp.fe.common.dialog;

import elca.ntig.partnerapp.fe.common.constant.ResolutionConstant;
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
        alertDialog.setTitle(getLocalizedString(title));
        alertDialog.setHeaderText(StringUtils.isNotBlank(header) ? getLocalizedString(header) : null);
        if (!message.contains("Dialog")) {
            alertDialog.setContentText(message);
        } else {
            alertDialog.setContentText(StringUtils.isNotBlank(message) ? getLocalizedString(message) : null);
        }

        if (StringUtils.isNotBlank(header)) {
            if (header.contains("delete")) {
                setDialogGraphics(alertDialog, ResourceConstant.QUESTION_ICON, ResourceConstant.BIN_ICON);
            } else if (header.contains("success")) {
                setDialogGraphics(alertDialog, ResourceConstant.SUCCESS_ICON, ResourceConstant.INFO_ICON);
            } else if (header.contains("error")) {
                setDialogGraphics(alertDialog, ResourceConstant.ERROR_ICON, ResourceConstant.WARNING_ICON);
            }
        }

        return alertDialog;
    }

    private String getLocalizedString(String key) {
        return observableResourceFactory.getStringBinding(key).get();
    }

    private void setDialogGraphics(Alert alertDialog, String graphicIconPath, String alertIconPath) {
        Stage dialogStage = (Stage) alertDialog.getDialogPane().getScene().getWindow();
        ImageView alertIcon = createImageView(alertIconPath, ResolutionConstant.DIALOG_ICON_WIDTH, ResolutionConstant.DIALOG_ICON_WIDTH);
        dialogStage.getIcons().add(alertIcon.getImage());

        ImageView graphicIcon = createImageView(graphicIconPath, ResolutionConstant.DIALOG_GRAPHIC_WIDTH, ResolutionConstant.DIALOG_GRAPHIC_WIDTH);
        alertDialog.setGraphic(graphicIcon);
    }

    private ImageView createImageView(String resourcePath, double height, double width) {
        Image image = new Image(getClass().getResourceAsStream(resourcePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        return imageView;
    }
}
