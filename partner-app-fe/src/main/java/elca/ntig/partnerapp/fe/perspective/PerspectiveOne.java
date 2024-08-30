package elca.ntig.partnerapp.fe.perspective;

import elca.ntig.partnerapp.fe.common.constant.IdentifierConstant;
import elca.ntig.partnerapp.fe.config.BasicConfig;
import javafx.event.Event;
import javafx.geometry.Orientation;
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

@Perspective(id = PerspectiveOne.ID,
        name = PerspectiveOne.ID,
        components = {
                BasicConfig.COMPONENT_LEFT,
                BasicConfig.COMPONENT_RIGHT,
                BasicConfig.STATEFUL_CALLBACK},
        resourceBundleLocation = "bundles.languageBundle")
public class PerspectiveOne implements FXPerspective {
    public static final String ID = "PerspectiveOne";
    private Logger log = Logger.getLogger(PerspectiveOne.class.getName());
    @Resource
    public Context context;

    private SplitPane mainLayout;

    @Override
    public void handlePerspective(final Message<Event, Object> action,
                                  final PerspectiveLayout perspectiveLayout) {

    }


    @OnShow
    /**
     * This method will be executed when the perspective gets the focus and switches to foreground
     * @param layout, the component layout contains references to the toolbar and the menu
     */
    public void onShow(final FXComponentLayout layout) {
        log.info("on show of PerspectiveOne");
    }

    @OnHide
    /**
     * will be executed when an active perspective looses the focus and moved to the background.
     * @param layout, the component layout contains references to the toolbar and the menu
     */
    public void onHide(final FXComponentLayout layout) {
        log.info("on hide of PerspectiveOne");
    }

    @PostConstruct
    /**
     * @PostConstruct annotated method will be executed when component is activated.
     * @param layout
     * @param resourceBundle
     */
    public void onStartPerspective(final PerspectiveLayout perspectiveLayout, final FXComponentLayout layout,
                                   final ResourceBundle resourceBundle) {
        // define toolbars and menu entries
        JACPToolBar toolbar = layout.getRegisteredToolBar(ToolbarPosition.NORTH);
        Button pressMe = new Button(resourceBundle.getString("p2.button"));
        pressMe.setOnAction((event) -> context.send(PerspectiveTwo.ID, "show"));
        toolbar.addAllOnEnd(pressMe);
        toolbar.add(new Label(resourceBundle.getString("p1.button")));


        mainLayout = new SplitPane();
        mainLayout.setOrientation(Orientation.HORIZONTAL);
        mainLayout.setDividerPosition(0, 0.3f);
        // let them grow
        LayoutUtil.GridPaneUtil.setFullGrow(ALWAYS, mainLayout);
        // create left button menu
        GridPane leftMenu = new GridPane();
        // create main content Top
        GridPane mainContent = new GridPane();

        mainLayout.getItems().addAll(leftMenu, mainContent);
        // Register root component
        perspectiveLayout.registerRootComponent(mainLayout);
        // register left menu
        perspectiveLayout.registerTargetLayoutComponent(BasicConfig.TARGET_CONTAINER_LEFT, leftMenu);
        // register main content
        perspectiveLayout.registerTargetLayoutComponent(BasicConfig.TARGET_CONTAINER_MAIN, mainContent);
        log.info("on PostConstruct of PerspectiveOne");
    }


    @PreDestroy
    /**
     * @PreDestroy annotated method will be executed when component is deactivated.
     * @param layout, the component layout contains references to the toolbar and the menu
     */
    public void onTearDownPerspective(final FXComponentLayout layout) {
        log.info("on PreDestroy of PerspectiveOne");

    }

}
