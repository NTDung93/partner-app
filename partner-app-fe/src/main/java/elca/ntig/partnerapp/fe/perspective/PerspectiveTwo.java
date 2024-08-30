package elca.ntig.partnerapp.fe.perspective;

import elca.ntig.partnerapp.fe.config.BasicConfig;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.lifecycle.OnHide;
import org.jacpfx.api.annotations.lifecycle.OnShow;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.annotations.lifecycle.PreDestroy;
import org.jacpfx.api.annotations.perspective.Perspective;
import org.jacpfx.api.message.Message;
import org.jacpfx.api.util.ToolbarPosition;
import org.jacpfx.rcp.componentLayout.FXComponentLayout;
import org.jacpfx.rcp.componentLayout.PerspectiveLayout;
import org.jacpfx.rcp.components.toolBar.JACPToolBar;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.perspective.FXPerspective;
import org.jacpfx.rcp.util.LayoutUtil;

import java.util.ResourceBundle;
import java.util.logging.Logger;

import static javafx.scene.layout.Priority.ALWAYS;

@Perspective(id = PerspectiveTwo.ID,
        name = PerspectiveTwo.ID,
        components = {
                BasicConfig.COMPONENT_LEFT,
                BasicConfig.COMPONENT_RIGHT,
                BasicConfig.STATELESS_CALLBACK},
        viewLocation = "/fxml/perspectiveTwo.fxml",
        resourceBundleLocation = "bundles.languageBundle")
public class PerspectiveTwo implements FXPerspective {
    public static final String ID = "PerspectiveTwo";
    private Logger log = Logger.getLogger(PerspectiveTwo.class.getName());
    @FXML
    private SplitPane mainLayout;
    @FXML
    private GridPane leftMenu;
    @FXML
    private GridPane mainContent;


    @Resource
    public Context context;

    @Override
    /**
     * handle messages to perspective, be aware... perspective always runs on FXApplication thread
     *
     */
    public void handlePerspective(final Message<Event, Object> message,
                                  final PerspectiveLayout perspectiveLayout) {

    }


    @OnShow
    /**
     * This method will be executed when the perspective gets the focus and switches to foreground
     * @param layout, the component layout contains references to the toolbar and the menu
     */
    public void onShow(final FXComponentLayout layout) {
        log.info("on show of PerspectiveTwo");
    }

    @OnHide
    /**
     * will be executed when an active perspective looses the focus and moved to the background.
     * @param layout, the component layout contains references to the toolbar and the menu
     */
    public void onHide(final FXComponentLayout layout) {
        log.info("on hide of PerspectiveTwo");
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
        // define toolbars and menu entries
        JACPToolBar toolbar = layout.getRegisteredToolBar(ToolbarPosition.NORTH);


        Button pressMe = new Button(resourceBundle.getString("p1.button"));
        pressMe.setOnAction((event) -> context.send(PerspectiveOne.ID, "show"));
        toolbar.addAllOnEnd(pressMe);
        toolbar.add(new Label(resourceBundle.getString("p2.button")));
        // let them grow
        LayoutUtil.GridPaneUtil.setFullGrow(ALWAYS, mainLayout);
        // register left menu
        perspectiveLayout.registerTargetLayoutComponent(BasicConfig.TARGET_CONTAINER_LEFT, leftMenu);
        // register main content
        perspectiveLayout.registerTargetLayoutComponent(BasicConfig.TARGET_CONTAINER_MAIN, mainContent);
        log.info("on PostConstruct of PerspectiveTwo");
    }

    @PreDestroy
    /**
     * @PreDestroy annotated method will be executed when component is deactivated.
     * @param layout, the component layout contains references to the toolbar and the menu
     */
    public void onTearDownPerspective(final FXComponentLayout layout) {
        log.info("on PreDestroy of PerspectiveTwo");

    }

}
