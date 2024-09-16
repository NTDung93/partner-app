package elca.ntig.partnerapp.fe.utils;

import javafx.beans.binding.StringBinding;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import org.springframework.stereotype.Component;

@Component
public class BindingHelper {
    private final ObservableResourceFactory observableResourceFactory;

    public BindingHelper(ObservableResourceFactory observableResourceFactory) {
        this.observableResourceFactory = observableResourceFactory;
    }

    public void bindLabelTextProperty(Labeled control, String key) {
        control.textProperty().bind(observableResourceFactory.getStringBinding(key));
    }

    public void bindColumnTextProperty(TableColumn<?, ?> column, String key) {
        column.textProperty().bind(observableResourceFactory.getStringBinding(key));
    }

    public void bindPromptTextProperty(ComboBox<?> comboBox, String key) {
        comboBox.promptTextProperty().bind(observableResourceFactory.getStringBinding(key));
    }
}
