package elca.ntig.partnerapp.fe.perspective;

import elca.ntig.partnerapp.fe.callback.organisation.DeleteOrganisationCallback;
import elca.ntig.partnerapp.fe.callback.organisation.GetOrganisationCallBack;
import elca.ntig.partnerapp.fe.callback.organisation.SearchOrganisationCallback;
import elca.ntig.partnerapp.fe.callback.person.DeletePersonCallback;
import elca.ntig.partnerapp.fe.callback.person.GetPersonCallBack;
import elca.ntig.partnerapp.fe.callback.person.SearchPeopleCallback;
import elca.ntig.partnerapp.fe.common.constant.TargetConstant;
import elca.ntig.partnerapp.fe.component.ViewPartnerComponent;
import javafx.event.Event;
import javafx.scene.layout.VBox;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.annotations.perspective.Perspective;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.componentLayout.FXComponentLayout;
import org.jacpfx.rcp.componentLayout.PerspectiveLayout;
import org.jacpfx.rcp.perspective.FXPerspective;

import java.util.ResourceBundle;

@Perspective(id = ViewPartnerPerspective.ID,
        name = ViewPartnerPerspective.ID,
        components = {
                ViewPartnerComponent.ID,
                SearchPeopleCallback.ID,
                SearchOrganisationCallback.ID,
                DeletePersonCallback.ID,
                DeleteOrganisationCallback.ID,
                GetPersonCallBack.ID,
                GetOrganisationCallBack.ID
        }
)
public class ViewPartnerPerspective implements FXPerspective {
    public static final String ID = "ViewPartnerPerspective";

    @Override
    public void handlePerspective(Message<Event, Object> message, PerspectiveLayout perspectiveLayout) {

    }

    @PostConstruct
    public void onStartPerspective(final PerspectiveLayout perspectiveLayout, final FXComponentLayout layout,
                                   final ResourceBundle resourceBundle) {
        VBox container = new VBox();
        perspectiveLayout.registerRootComponent(container);
        perspectiveLayout.registerTargetLayoutComponent(TargetConstant.TARGET_VIEW_PARTNER_CONTAINER, container);
    }
}
