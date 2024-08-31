package elca.ntig.partnerapp.fe.component;

import elca.ntig.partnerapp.fe.config.ApplicationConfig;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.jacpfx.api.annotations.component.DeclarativeView;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.FXComponent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A sample JacpFX Component
 */
@DeclarativeView(id = HeaderComponent.ID,
        viewLocation = "/fxml/HeaderComponent.fxml",
        resourceBundleLocation = ObservableResourceFactory.RESOURCE_BUNDLE_NAME,
        name = HeaderComponent.ID,
        initialTargetLayoutId = ApplicationConfig.TARGET_HEADER_CONTAINER)
public class HeaderComponent implements FXComponent {
    public static final String ID = "HeaderComponent";

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @FXML
    private Label lblSampleText;

    @FXML
    private Label lblFr;

    @FXML
    private Label lblEn;

    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        return null;
    }

    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        return null;
    }

    @PostConstruct
    public void onPostConstructComponent() {
        lblSampleText.textProperty().bind(observableResourceFactory.getStringBinding("SamplePerspective.HeaderComponent.lbl.sampleText"));
        lblEn.setOnMouseClicked(event -> {
            observableResourceFactory.switchResourceByLanguage(ObservableResourceFactory.Language.EN);
        });
        lblFr.setOnMouseClicked(event -> {
            observableResourceFactory.switchResourceByLanguage(ObservableResourceFactory.Language.FR);
        });
    }
}
