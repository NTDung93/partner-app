package elca.ntig.partnerapp.fe.workbench;

import elca.ntig.partnerapp.fe.common.constant.ClassNameConstant;
import elca.ntig.partnerapp.fe.common.constant.LanguageConstant;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import elca.ntig.partnerapp.fe.perspective.SamplePerspective;
import elca.ntig.partnerapp.fe.perspective.ViewPartnerPerspective;
import javafx.event.Event;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jacpfx.api.annotations.workbench.Workbench;
import org.jacpfx.api.componentLayout.WorkbenchLayout;
import org.jacpfx.api.message.Message;
import org.jacpfx.api.util.ToolbarPosition;
import org.jacpfx.rcp.componentLayout.FXComponentLayout;
import org.jacpfx.rcp.components.menuBar.JACPMenuBar;
import org.jacpfx.rcp.workbench.FXWorkbench;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Workbench(id = PartnerAppWorkbench.ID,
        name = PartnerAppWorkbench.ID,
        perspectives = {
                ViewPartnerPerspective.ID,
//                SamplePerspective.ID,
        })
public class PartnerAppWorkbench implements FXWorkbench {

    public static final String ID = "PartnerAppWorkbench";

    private static final int RECOMMENDED_WIDTH = 1920;
    private static final int RECOMMENDED_HEIGHT = 960;

    private static final int DEGRADED_WIDTH = 1280;
    private static final int DEGRADED_HEIGHT = 720;

    private static final Logger LOGGER = LoggerFactory.getLogger(PartnerAppWorkbench.class);

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Override
    public void handleInitialLayout(final Message<Event, Object> action, final WorkbenchLayout<Node> layout, final Stage stage) {
        Resolution resolution = resolutionByPrimaryScreenBounds();
        stage.setMaximized(true);
        layout.setWorkbenchXYSize(resolution.width(), resolution.height());
        layout.setStyle(StageStyle.DECORATED);
        layout.setMenuEnabled(true);
    }

    @Override
    public void postHandle(final FXComponentLayout layout) {
        final JACPMenuBar menu = layout.getMenu();
        final Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        Button frButton = createLanguageButton(LanguageConstant.FR, true,  menu);
        Button enButton = createLanguageButton(LanguageConstant.EN, false,  menu);

        menu.getChildren().addAll(region, frButton, enButton);
    }

    private Button createLanguageButton(String text, boolean isActive, JACPMenuBar menu) {
        Button button = new Button(text);
        button.getStyleClass().add(ClassNameConstant.LANGUAGE_BUTTON);
        button.getStyleClass().add(isActive ? ClassNameConstant.LANGUAGE_BUTTON_ACTIVE : ClassNameConstant.LANGUAGE_BUTTON_INACTIVE);

        button.setOnAction(e -> {
            menu.getChildren().stream()
                    .filter(node -> node instanceof Button)
                    .forEach(node -> {
                        node.getStyleClass().removeAll(ClassNameConstant.LANGUAGE_BUTTON_ACTIVE, ClassNameConstant.LANGUAGE_BUTTON_INACTIVE);
                        node.getStyleClass().addAll(ClassNameConstant.LANGUAGE_BUTTON_INACTIVE);
                    });

            button.getStyleClass().remove(ClassNameConstant.LANGUAGE_BUTTON_INACTIVE);
            button.getStyleClass().add(ClassNameConstant.LANGUAGE_BUTTON_ACTIVE);

            ObservableResourceFactory.Language language = LanguageConstant.FR.equals(text)
                    ? ObservableResourceFactory.Language.FR
                    : ObservableResourceFactory.Language.EN;
            observableResourceFactory.switchResourceByLanguage(language);
        });

        return button;
    }

    private Resolution resolutionByPrimaryScreenBounds() {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        LOGGER.debug("Screen size: " + primScreenBounds.getMaxX() + "x" + primScreenBounds.getMaxY());
        if (primScreenBounds.getMaxX() >= Resolution.RECOMMENDED.width()) {
            return Resolution.RECOMMENDED;
        }
        return Resolution.DEGRADED;
    }

    private enum Resolution {
        RECOMMENDED(RECOMMENDED_WIDTH, RECOMMENDED_HEIGHT), DEGRADED(DEGRADED_WIDTH, DEGRADED_HEIGHT);

        private final int width;
        private final int height;

        Resolution(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int width() {
            return width;
        }

        public int height() {
            return height;
        }
    }
}
