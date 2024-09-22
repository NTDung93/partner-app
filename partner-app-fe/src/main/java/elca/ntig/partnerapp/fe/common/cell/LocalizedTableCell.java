package elca.ntig.partnerapp.fe.common.cell;

import elca.ntig.partnerapp.fe.utils.ObservableResourceFactory;
import javafx.scene.control.TableCell;
import org.apache.commons.lang3.StringUtils;
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
            if (item.contains("NULL_")) {
                setText(null);
                return;
            }
            int subStringIndex = 0;
            if (!resourcePrefix.equals("Enum.legalStatus.") && !resourcePrefix.equals("Enum.sex.")) {
                subStringIndex = item.indexOf("_") + 1;
            }
            String value = item.substring(subStringIndex).toLowerCase();
            textProperty().bind(observableResourceFactory.getStringBinding(resourcePrefix + value));
        }
    }
}