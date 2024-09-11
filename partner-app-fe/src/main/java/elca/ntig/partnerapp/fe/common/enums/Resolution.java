package elca.ntig.partnerapp.fe.common.enums;

import elca.ntig.partnerapp.fe.workbench.PartnerAppWorkbench;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static elca.ntig.partnerapp.fe.common.constant.ResolutionConstant.*;

public enum Resolution {
    RECOMMENDED(RECOMMENDED_WIDTH, RECOMMENDED_HEIGHT),
    DEGRADED(DEGRADED_WIDTH, DEGRADED_HEIGHT);

    private final int width;
    private final int height;
    private static final Logger LOGGER = LoggerFactory.getLogger(PartnerAppWorkbench.class);

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

    public static Resolution resolutionByPrimaryScreenBounds() {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        LOGGER.debug("Screen size: " + primScreenBounds.getMaxX() + "x" + primScreenBounds.getMaxY());
        if (primScreenBounds.getMaxX() >= Resolution.RECOMMENDED.width()) {
            return Resolution.RECOMMENDED;
        }
        return Resolution.DEGRADED;
    }
}
