package elca.ntig.partnerapp.fe;

import elca.ntig.partnerapp.fe.config.BasicConfig;
import elca.ntig.partnerapp.fe.workbench.JacpFXWorkbench;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jacpfx.rcp.workbench.FXWorkbench;
import org.jacpfx.spring.launcher.AFXSpringJavaConfigLauncher;

import static elca.ntig.partnerapp.fe.config.BasicConfig.BASE_PACKAGE;

public class PartnerAppFELauncher extends AFXSpringJavaConfigLauncher {

    @Override
    protected Class<? extends FXWorkbench> getWorkbenchClass() {
        return JacpFXWorkbench.class;
    }

    @Override
    protected String[] getBasePackages() {
        return new String[]{BASE_PACKAGE};
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    //This method gives you access to the JavaFX stage. You may define a stylesheet for your application.
    @Override
    public void postInit(Stage stage) {
        Scene scene = stage.getScene();
        scene.getStylesheets().addAll(
                PartnerAppFELauncher.class.getResource("/styles/default.css")
                        .toExternalForm()
        );
    }

    @Override
    protected Class<?>[] getConfigClasses() {
        return new Class<?>[]{BasicConfig.class};
    }
}
