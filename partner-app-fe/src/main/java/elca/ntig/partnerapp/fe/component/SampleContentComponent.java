package elca.ntig.partnerapp.fe.component;

import elca.ntig.partnerapp.fe.common.constant.TargetConstant;
import elca.ntig.partnerapp.fe.config.ApplicationConfig;
import elca.ntig.partnerapp.fe.factory.ObservableResourceFactory;
import elca.ntig.partnerapp.fe.fragment.SampleFragment;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.View;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.FXComponent;
import org.jacpfx.rcp.components.managedFragment.ManagedFragmentHandler;
import org.jacpfx.rcp.context.Context;

@View(id = SampleContentComponent.ID,
        name = SampleContentComponent.ID,
        resourceBundleLocation = ObservableResourceFactory.RESOURCE_BUNDLE_NAME,
        initialTargetLayoutId = TargetConstant.TARGET_RIGHT_CONTENT_CONTAINER)
public class SampleContentComponent implements FXComponent {
    public static final String ID = "SampleContentComponent";

    private Node root;

    @Resource
    private Context context;


    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        return this.root;
    }

    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        return null;
    }


    @PostConstruct
    public void onPostConstructComponent() {
        this.root = initFragment();
    }

    private Node initFragment() {
        final VBox container = new VBox();
        VBox.setVgrow(container, Priority.ALWAYS);
        final ManagedFragmentHandler<SampleFragment> handler = context.getManagedFragmentHandler(SampleFragment.class);
        final SampleFragment controller = handler.getController();
        controller.init();
        container.getChildren().addAll(handler.getFragmentNode());
        return container;
    }
}
