package elca.ntig.partnerapp.fe.common.cell;

import com.google.protobuf.ProtocolMessageEnum;
import elca.ntig.partnerapp.fe.utils.ObservableResourceFactory;
import javafx.scene.control.ListCell;
import org.springframework.stereotype.Component;

@Component
public class EnumCell<T extends ProtocolMessageEnum> extends ListCell<T> {
    private final ObservableResourceFactory observableResourceFactory;
    private final String resourcePrefix;

    public EnumCell(ObservableResourceFactory observableResourceFactory, String resourcePrefix) {
        this.observableResourceFactory = observableResourceFactory;
        this.resourcePrefix = resourcePrefix;
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        textProperty().unbind();
        if (empty || item == null) {
            setText(null);
        } else {
            int subStringIndex = 0;
            if (needsSubStringIndex(resourcePrefix)) {
                subStringIndex = item.getValueDescriptor().toString().indexOf("_") + 1;
            }
            String value = item.getValueDescriptor().toString().substring(subStringIndex).toLowerCase();
            textProperty().bind(observableResourceFactory.getStringBinding(resourcePrefix + value));
        }
    }

    private boolean needsSubStringIndex(String resourcePrefix) {
        return !resourcePrefix.equals("Enum.legalStatus.")
                && !resourcePrefix.equals("Enum.sex.")
                && !resourcePrefix.equals("Enum.marital.")
                && !resourcePrefix.equals("Enum.cantonAbbr.");
    }
}
