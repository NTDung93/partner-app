package elca.ntig.partnerapp.fe.common.cell;

import com.google.protobuf.ProtocolMessageEnum;
import elca.ntig.partnerapp.common.proto.enums.partner.LanguageProto;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import javafx.scene.control.ListCell;
import org.springframework.stereotype.Component;

@Component
public class EnumCell<T extends ProtocolMessageEnum> extends ListCell<T> {
    private final ObservableResourceFactory observableResourceFactory;
    private final String resourcePrefix;
    private final int subStringIndex;

    public EnumCell(ObservableResourceFactory observableResourceFactory, String resourcePrefix, int subStringIndex) {
        this.observableResourceFactory = observableResourceFactory;
        this.resourcePrefix = resourcePrefix;
        this.subStringIndex = subStringIndex;
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        textProperty().unbind();
        if (empty || item == null) {
            setText(null);
        } else {
            String value = item.getValueDescriptor().toString().substring(subStringIndex).toLowerCase();
            textProperty().bind(observableResourceFactory.getStringBinding(resourcePrefix + value));
        }
    }
}
