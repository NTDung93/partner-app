package elca.ntig.partnerapp.fe.common.cell;

import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import javafx.scene.control.TableCell;
import org.springframework.stereotype.Component;

@Component
public class LocalizedTableCell<S> extends TableCell<S, String> {
    private final ObservableResourceFactory observableResourceFactory;
    private final String resourcePrefix;

    public LocalizedTableCell(ObservableResourceFactory observableResourceFactory, String resourcePrefix) {
        this.observableResourceFactory = observableResourceFactory;
        this.resourcePrefix = resourcePrefix;
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        textProperty().unbind();
        if (empty || item == null || item.isEmpty()) {
            setText(null);
        } else {
            int subStringIndex = 0;
            if (!resourcePrefix.equals("Enum.legalStatus.") && !resourcePrefix.equals("Enum.sex.")) {
//                String value = item.substring(item.indexOf("_") + 1);
                subStringIndex = item.indexOf("_") + 1;
            }
//            String key = resourcePrefix + value.toLowerCase();
            String value = item.substring(subStringIndex).toLowerCase();
            textProperty().bind(observableResourceFactory.getStringBinding(resourcePrefix + value));
        }
    }
}