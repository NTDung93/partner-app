package elca.ntig.partnerapp.fe.utils;

import javafx.beans.binding.StringBinding;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class BindingHelper {
    @Autowired
    private ObservableResourceFactory observableResourceFactory;
    private String localeKey;

    public BindingHelper(String localeKey) {
        this.localeKey = localeKey;
    }

    public StringBinding textBinding(String localeKey){
        return observableResourceFactory.getStringBinding(localeKey);
    }
}
