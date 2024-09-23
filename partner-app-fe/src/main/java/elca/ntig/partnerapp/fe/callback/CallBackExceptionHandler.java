package elca.ntig.partnerapp.fe.callback;

import elca.ntig.partnerapp.fe.common.constant.MessageConstant;
import elca.ntig.partnerapp.fe.common.dialog.DialogBuilder;
import elca.ntig.partnerapp.fe.perspective.ViewPartnerPerspective;
import elca.ntig.partnerapp.fe.utils.ObservableResourceFactory;
import io.grpc.StatusRuntimeException;
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
            Alert alert = dialogBuilder.buildAlert(Alert.AlertType.ERROR, "Dialog.err.title",
                    "Dialog.err.message.unexpected.error", e.getMessage());
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                alert.close();
            }
        });
    }

    public void handleStatusRuntimeException(Exception e, String suffix) {
        String prefix = "Dialog.err.header.error.";
        int index = e.getMessage().indexOf(":") + 2;
        String errMessage = e.getMessage().substring(index);
        Platform.runLater(() -> {
            DialogBuilder dialogBuilder = new DialogBuilder(observableResourceFactory);
            Alert alert = dialogBuilder.buildAlert(Alert.AlertType.ERROR, "Dialog.err.title", prefix.concat(suffix), errMessage);
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
            Alert alert = dialogBuilder.buildAlert(Alert.AlertType.INFORMATION, "Dialog.information.title", prefix.concat(suffix), "");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                alert.close();
            }
        });
    }
}
