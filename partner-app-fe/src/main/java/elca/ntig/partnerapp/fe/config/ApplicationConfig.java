package elca.ntig.partnerapp.fe.config;

import elca.ntig.partnerapp.fe.common.enums.Language;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class ApplicationConfig {
    @Bean
    public ObservableResourceFactory observableResourceFactory() {

        ObservableResourceFactory observableResourceFactory = new ObservableResourceFactory();
        observableResourceFactory.setResource(Language.FR);
        return observableResourceFactory;
    }
}
