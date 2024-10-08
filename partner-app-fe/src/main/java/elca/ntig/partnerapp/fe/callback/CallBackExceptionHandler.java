package elca.ntig.partnerapp.fe.callback;

import elca.ntig.partnerapp.fe.common.dialog.DialogBuilder;
import elca.ntig.partnerapp.fe.utils.ObservableResourceFactory;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.rcp.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class CallBackExceptionHandler {
    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Resource
    private Context context;

    public void handleUnexpectedException(Exception e) {
        Platform.runLater(() -> {
            DialogBuilder dialogBuilder = new DialogBuilder(observableResourceFactory);
            Alert alert = dialogBuilder.buildAlert(Alert.AlertType.ERROR, "Dialog.err.title", e.getMessage());
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                alert.close();
            }
        });
    }

    public void handleStatusRuntimeException(Exception e) {
        int index = e.getMessage().indexOf(":") + 2;
        String errMessage = e.getMessage().substring(index);
        Platform.runLater(() -> {
            DialogBuilder dialogBuilder = new DialogBuilder(observableResourceFactory);
            Alert alert = dialogBuilder.buildAlert(Alert.AlertType.ERROR, "Dialog.err.title", errMessage);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                alert.close();
            }
        });
    }

    public void handleSuccessfulResponse(String suffix) {
        String prefix = "Dialog.information.header.success.";
        Platform.runLater(() -> {
            DialogBuilder dialogBuilder = new DialogBuilder(observableResourceFactory);
            Alert alert = dialogBuilder.buildAlert(Alert.AlertType.INFORMATION, "Dialog.information.title", prefix.concat(suffix));
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                alert.close();
            }
        });
    }
}
