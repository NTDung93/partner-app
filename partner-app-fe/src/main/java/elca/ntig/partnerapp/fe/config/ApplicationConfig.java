package elca.ntig.partnerapp.fe.config;

import elca.ntig.partnerapp.fe.common.enums.Language;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import elca.ntig.partnerapp.fe.service.PersonClientService;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public ObservableResourceFactory observableResourceFactory() {
        ObservableResourceFactory observableResourceFactory = new ObservableResourceFactory();
        observableResourceFactory.setResource(Language.FR);
        return observableResourceFactory;
    }

    @Bean
    public PersonClientService personClientService() {
        return new PersonClientService();
    }
}
