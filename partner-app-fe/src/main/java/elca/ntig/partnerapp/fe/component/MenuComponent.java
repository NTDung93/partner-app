package elca.ntig.partnerapp.fe.component;

import elca.ntig.partnerapp.fe.common.constant.TargetConstant;
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
import org.springframework.stereotype.Component;

/**
 * A sample JacpFX Component
 */
@Component
@DeclarativeView(id = MenuComponent.ID,
        viewLocation = "/fxml/MenuComponent.fxml",
        name = MenuComponent.ID,
        initialTargetLayoutId = TargetConstant.TARGET_LEFT_MENU_CONTAINER)
public class MenuComponent implements FXComponent {
    public static final String ID = "MenuComponent";

    @FXML
    private Label lblMenuSampleText;

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

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
        lblMenuSampleText.textProperty().bind(observableResourceFactory.getStringBinding("SamplePerspective.MenuComponent.lbl.sampleText"));
    }

}
