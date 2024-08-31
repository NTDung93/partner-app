package elca.ntig.partnerapp.fe;

import elca.ntig.partnerapp.fe.config.ApplicationConfig;
import elca.ntig.partnerapp.fe.workbench.PartnerAppWorkbench;
import javafx.application.Application;
import javafx.stage.Stage;
import org.jacpfx.rcp.workbench.FXWorkbench;
import org.jacpfx.spring.launcher.AFXSpringJavaConfigLauncher;

import static elca.ntig.partnerapp.fe.config.ApplicationConfig.BASE_PACKAGE;

public class PartnerAppFELauncher extends AFXSpringJavaConfigLauncher {

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
    }

    @Override
    protected Class<?>[] getConfigClasses() {
        return new Class<?>[]{ApplicationConfig.class};
    }
}
