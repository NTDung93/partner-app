package elca.ntig.partnerapp.fe.perspective;

import elca.ntig.partnerapp.fe.component.HeaderComponent;
import elca.ntig.partnerapp.fe.component.MenuComponent;
import elca.ntig.partnerapp.fe.component.SampleContentComponent;
import elca.ntig.partnerapp.fe.config.ApplicationConfig;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.annotations.perspective.Perspective;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.componentLayout.FXComponentLayout;
import org.jacpfx.rcp.componentLayout.PerspectiveLayout;
import org.jacpfx.rcp.perspective.FXPerspective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ResourceBundle;

@Component
@Perspective(id = SamplePerspective.ID,
        name = SamplePerspective.ID,
        viewLocation = "/fxml/SamplePerspective.fxml",
        components = {
                HeaderComponent.ID,
                MenuComponent.ID,
                SampleContentComponent.ID
        })
public class SamplePerspective implements FXPerspective {
    public static final String ID = "SamplePerspective";

    @FXML
    private VBox vbxHeaderContainer;

    @FXML
    private VBox vbxLeftMenuContainer;

    @FXML
    private VBox vbxRightContentContainer;

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Override
    public void handlePerspective(Message<Event, Object> message, PerspectiveLayout perspectiveLayout) {

    }

    @PostConstruct
    /**
     * @PostConstruct annotated method will be executed when component is activated.
     * @param perspectiveLayout , the perspective layout let you register target layouts
     * @param layout, the component layout contains references to the toolbar and the menu
     * @param resourceBundle
     */
    public void onStartPerspective(final PerspectiveLayout perspectiveLayout, final FXComponentLayout layout,
                                   final ResourceBundle resourceBundle) {
        perspectiveLayout.registerTargetLayoutComponent(ApplicationConfig.TARGET_HEADER_CONTAINER, vbxHeaderContainer);
        perspectiveLayout.registerTargetLayoutComponent(ApplicationConfig.TARGET_LEFT_MENU_CONTAINER, vbxLeftMenuContainer);
        perspectiveLayout.registerTargetLayoutComponent(ApplicationConfig.TARGET_RIGHT_CONTENT_CONTAINER, vbxRightContentContainer);
    }
}
