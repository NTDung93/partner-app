package elca.ntig.partnerapp.fe.common.cell;

import elca.ntig.partnerapp.common.proto.enums.partner.LanguageProto;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import javafx.scene.control.ListCell;
import org.springframework.stereotype.Component;

@Component
public class LanguageCell extends ListCell<LanguageProto> {
    private final ObservableResourceFactory observableResourceFactory;

    public LanguageCell(ObservableResourceFactory observableResourceFactory) {
        this.observableResourceFactory = observableResourceFactory;
    }

    @Override
    protected void updateItem(LanguageProto item, boolean empty) {
        super.updateItem(item, empty);
        textProperty().unbind();
        if (empty || item == null) {
            setText(null);
        } else {
            String value = item.getValueDescriptor().toString().substring(5).toLowerCase();
            textProperty().bind(observableResourceFactory.getStringBinding("Enum.language." + value));
        }
    }
}
