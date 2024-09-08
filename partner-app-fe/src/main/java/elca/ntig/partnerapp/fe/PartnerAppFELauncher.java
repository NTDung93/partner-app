package elca.ntig.partnerapp.fe;

import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
import elca.ntig.partnerapp.fe.config.ApplicationConfig;
import elca.ntig.partnerapp.fe.workbench.PartnerAppWorkbench;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jacpfx.rcp.workbench.FXWorkbench;
import org.jacpfx.spring.launcher.AFXSpringJavaConfigLauncher;

public class PartnerAppFELauncher extends AFXSpringJavaConfigLauncher {

    private static final String BASE_PACKAGE = "elca.ntig.partnerapp.fe";

    @Override
    protected Class<? extends FXWorkbench> getWorkbenchClass() {
        return PartnerAppWorkbench.class;
    }

    @Override
    protected String[] getBasePackages() {
        return new String[]{BASE_PACKAGE};
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void postInit(Stage stage) {
        Scene scene = stage.getScene();
        scene.getStylesheets().addAll(
                PartnerAppFELauncher.class.getResource(ResourceConstant.GLOBAL_STYLE_RESOURCE)
                        .toExternalForm()
        );
    }

    @Override
    protected Class<?>[] getConfigClasses() {
        return new Class<?>[]{ApplicationConfig.class};
    }
}
