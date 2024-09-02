package elca.ntig.partnerapp.fe.fragment;

import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import org.jacpfx.api.fragment.Scope;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Fragment(id = TableFragment.ID,
        viewLocation = ResourceConstant.TABLE_FRAGMENT_FXML,
        scope = Scope.PROTOTYPE)
public class TableFragment {
    public static final String ID = "TableFragment";

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @FXML
    private Label tableName;

    public void init(){
        tableName.textProperty()
                .bind(observableResourceFactory.getStringBinding("TableFragment.lbl.tableName"));
    }
}
