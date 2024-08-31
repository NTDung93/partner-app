package elca.ntig.partnerapp.fe.fragment;

import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.springframework.beans.factory.annotation.Autowired;

@Fragment(id = SampleFragment.ID,
        viewLocation = "/fxml/SampleFragment.fxml",
        scope = Scope.PROTOTYPE)
public class SampleFragment {
    public static final String ID = "SampleFragment";

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @FXML
    private Label lblContentSampleText;

    public void init(){
        lblContentSampleText.textProperty()
                .bind(observableResourceFactory.getStringBinding("SamplePerspective.SampleContentComponent.SampleFragment.lbl.sampleText"));
    }
}
