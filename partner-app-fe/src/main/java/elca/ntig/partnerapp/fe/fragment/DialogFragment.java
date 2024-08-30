package elca.ntig.partnerapp.fe.fragment;

import elca.ntig.partnerapp.fe.config.BasicConfig;
import elca.ntig.partnerapp.fe.perspective.PerspectiveOne;
import elca.ntig.partnerapp.fe.perspective.PerspectiveTwo;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.jacpfx.rcp.context.Context;

@Fragment(id = BasicConfig.DIALOG_FRAGMENT,
        viewLocation = "/fxml/DialogFragment.fxml",
        resourceBundleLocation = "bundles.languageBundle",
        localeID = "en_US",
        scope = Scope.PROTOTYPE)
public class DialogFragment {

    @Resource
    private Context context;

    @FXML
    private TextField name;


    public void init() {
        name.setOnKeyReleased(event->{
            final String nameValue = name.getText();
            if(context.getParentId().equals(PerspectiveOne.ID)) {
                context.send(PerspectiveOne.ID.concat(".").concat(BasicConfig.STATEFUL_CALLBACK), nameValue);
            } else {
                context.send(PerspectiveTwo.ID.concat(".").concat(BasicConfig.STATELESS_CALLBACK), nameValue);
            }
        });
    }
}
