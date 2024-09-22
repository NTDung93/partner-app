package elca.ntig.partnerapp.fe.perspective;

import elca.ntig.partnerapp.fe.common.constant.TargetConstant;
import elca.ntig.partnerapp.fe.component.UpdatePartnerComponent;
import javafx.event.Event;
import javafx.scene.layout.VBox;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.annotations.perspective.Perspective;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.componentLayout.FXComponentLayout;
import org.jacpfx.rcp.componentLayout.PerspectiveLayout;
import org.jacpfx.rcp.perspective.FXPerspective;

import java.util.ResourceBundle;

@Perspective(id = UpdatePartnerPerspective.ID,
        name = UpdatePartnerPerspective.ID,
        components = {
                UpdatePartnerComponent.ID,
        }
)
public class UpdatePartnerPerspective implements FXPerspective {
    public static final String ID = "UpdatePartnerPerspective";

    @Override
    public void handlePerspective(Message<Event, Object> message, PerspectiveLayout perspectiveLayout) {

    }

    @PostConstruct
    public void onStartPerspective(final PerspectiveLayout perspectiveLayout, final FXComponentLayout layout,
                                   final ResourceBundle resourceBundle) {
        VBox container = new VBox();
        perspectiveLayout.registerRootComponent(container);
        perspectiveLayout.registerTargetLayoutComponent(TargetConstant.TARGET_UPDATE_PARTNER_CONTAINER, container);
    }
}