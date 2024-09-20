package elca.ntig.partnerapp.fe.config;

import elca.ntig.partnerapp.fe.common.constant.ServerContant;
import elca.ntig.partnerapp.fe.common.enums.Language;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import elca.ntig.partnerapp.fe.service.OrganisationClientService;
import elca.ntig.partnerapp.fe.service.PersonClientService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public ManagedChannel managedChannel() {
        return ManagedChannelBuilder
                .forAddress(ServerContant.SERVER_HOST, ServerContant.SERVER_PORT)
                .usePlaintext()
                .build();
    }

    @Bean
    public ObservableResourceFactory observableResourceFactory() {
        ObservableResourceFactory observableResourceFactory = new ObservableResourceFactory();
        observableResourceFactory.setResource(Language.FR);
        return observableResourceFactory;
    }

    @Bean
    public PersonClientService personClientService(ManagedChannel managedChannel) {
        return new PersonClientService(managedChannel);
    }

    @Bean
    public OrganisationClientService organisationClientService(ManagedChannel managedChannel) {
        return new OrganisationClientService(managedChannel);
    }
}
