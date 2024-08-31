package elca.ntig.partnerapp.fe.config;

import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class ApplicationConfig {

    @Bean
    public ObservableResourceFactory observableResourceFactory(){
        ObservableResourceFactory observableResourceFactory = new ObservableResourceFactory();
        observableResourceFactory.switchResourceByLanguage(ObservableResourceFactory.Language.FR);
        return observableResourceFactory;
    }

    // Application launcher
    public static final String BASE_PACKAGE = "elca.ntig.partnerapp.fe";

    // Workbench
    public static final int WORKBENCH_X_SIZE = 1024;
    public static final int WORKBENCH_Y_SIZE = 768;

    // Targets id
    public static final String TARGET_HEADER_CONTAINER = "HeaderContainer";
    public static final String TARGET_LEFT_MENU_CONTAINER = "LeftMenuContainer";
    public static final String TARGET_RIGHT_CONTENT_CONTAINER = "RightContentContainer";
}
