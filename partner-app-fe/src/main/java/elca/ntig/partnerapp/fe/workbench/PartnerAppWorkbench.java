package elca.ntig.partnerapp.fe.workbench;

import elca.ntig.partnerapp.fe.perspective.PerspectiveOne;
import elca.ntig.partnerapp.fe.perspective.PerspectiveTwo;
import javafx.event.Event;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jacpfx.api.annotations.workbench.Workbench;
import org.jacpfx.api.componentLayout.WorkbenchLayout;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.componentLayout.FXComponentLayout;
import org.jacpfx.rcp.workbench.FXWorkbench;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Workbench(id = PartnerAppWorkbench.ID,
        name = PartnerAppWorkbench.ID,
        perspectives = {
                PerspectiveOne.ID,
                PerspectiveTwo.ID,
        })
public class PartnerAppWorkbench implements FXWorkbench {

    public static final String ID = "PartnerAppWorkbench";

    private static final Logger LOGGER = LoggerFactory.getLogger(PartnerAppWorkbench.class);

    @Override
    public void handleInitialLayout(final Message<Event, Object> action, final WorkbenchLayout<Node> layout, final Stage stage) {
        Resolution resolution = resolutionByPrimaryScreenBounds();
        stage.setMaximized(true);
        layout.setWorkbenchXYSize(resolution.width(), resolution.height());
        layout.setStyle(StageStyle.DECORATED);
        layout.setMenuEnabled(false);
    }

    @Override
    public void postHandle(final FXComponentLayout layout) {
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
        RECOMMENDED(1920, 960), DEGRADED(1280, 720);

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
