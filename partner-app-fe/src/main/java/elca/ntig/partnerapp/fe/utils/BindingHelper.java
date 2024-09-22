package elca.ntig.partnerapp.fe.utils;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

@Component
public class BindingHelper {
    private final ObservableResourceFactory observableResourceFactory;

    public BindingHelper(ObservableResourceFactory observableResourceFactory) {
        this.observableResourceFactory = observableResourceFactory;
    }

    public void bindTextProperty(Text control, String key) {
        control.textProperty().bind(observableResourceFactory.getStringBinding(key));
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
