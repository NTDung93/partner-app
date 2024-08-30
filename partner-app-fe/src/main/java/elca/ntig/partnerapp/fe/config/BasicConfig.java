package elca.ntig.partnerapp.fe.config;

//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@ComponentScan
public class BasicConfig {
    // Application launcher
    public static final String BASE_PACKAGE = "elca.ntig.partnerapp.fe";

    // Workbench
    public static final int WORKBENCH_X_SIZE = 1024;
    public static final int WORKBENCH_Y_SIZE = 768;

    public static final String COMPONENT_LEFT = "id002";
    public static final String COMPONENT_RIGHT = "id003";
    public static final String STATELESS_CALLBACK = "id004";
    public static final String STATEFUL_CALLBACK = "id005";


    public static final String DIALOG_FRAGMENT = "idD1";

    public static final String TARGET_CONTAINER_LEFT = "PLeft";
    public static final String TARGET_CONTAINER_MAIN = "PMain";
}
