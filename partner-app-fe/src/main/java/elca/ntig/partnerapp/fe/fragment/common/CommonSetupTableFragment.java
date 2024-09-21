package elca.ntig.partnerapp.fe.fragment.common;

import elca.ntig.partnerapp.fe.common.model.OrganisationTableModel;
import elca.ntig.partnerapp.fe.common.model.PersonTableModel;
import elca.ntig.partnerapp.fe.utils.BindingHelper;
import javafx.scene.control.*;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class CommonSetupTableFragment<T> {
    private static Logger logger = Logger.getLogger(CommonSetupTableFragment.class);

    public void setTableDefaultMessage(BindingHelper bindingHelper, TableView<?> partnersTable) {
        Label empty = new Label();
        bindingHelper.bindLabelTextProperty(empty, "TableFragment.defaultMessage");
        partnersTable.setPlaceholder(empty);
    }

    public void setCellFactoryDateColumn(TableColumn<T, String> column) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        column.setCellFactory(cell -> new TableCell<T, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    LocalDate date = LocalDate.parse(item);
                    setText(date.format(dateFormatter));
                }
            }
        });
    }

    public void setCellFactoryAvsNumberColumn(TableColumn<T, String> column) {
        column.setCellFactory(cell -> new TableCell<T, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    StringBuilder formatted = new StringBuilder();
                    for (int i = 0; i < item.length(); i++) {
                        if (i == 3 || i == 7 || i == 11) {
                            formatted.append(".");
                        }
                        formatted.append(item.charAt(i));
                    }
                    setText(formatted.toString());
                }
            }
        });
    }

    public void setCellFactoryCodeNOGANumberColumn(TableColumn<T, String> column) {
        column.setCellFactory(cell -> new TableCell<T, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.substring(5));
                }
            }
        });
    }
}
