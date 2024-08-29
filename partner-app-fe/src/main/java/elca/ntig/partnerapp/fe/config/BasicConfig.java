package elca.ntig.partnerapp.fe.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class BasicConfig {
    public static final String PERSPECTIVE_ONE = "idPone";
    public static final String PERSPECTIVE_TWO = "idPtwo";

    public static final String COMPONENT_LEFT = "id002";
    public static final String COMPONENT_RIGHT = "id003";
    public static final String STATELESS_CALLBACK = "id004";
    public static final String STATEFUL_CALLBACK = "id005";


    public static final String DIALOG_FRAGMENT = "idD1";

    public static final String TARGET_CONTAINER_LEFT = "PLeft";
    public static final String TARGET_CONTAINER_MAIN = "PMain";
}
