package elca.ntig.partnerapp.fe.fragment.address;

import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
import elca.ntig.partnerapp.fe.fragment.BaseFormFragment;
import elca.ntig.partnerapp.fe.fragment.common.CommonSetupFormFragment;
import elca.ntig.partnerapp.fe.utils.BindingHelper;
import elca.ntig.partnerapp.fe.utils.ObservableResourceFactory;
import org.apache.log4j.Logger;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.jacpfx.rcp.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Fragment(id = UpdateAddressFormFragment.ID,
        viewLocation = ResourceConstant.UPDATE_ADDRESS_FORM_FRAGMENT_FXML,
        scope = Scope.PROTOTYPE)
public class UpdateAddressFormFragment extends CommonSetupFormFragment implements BaseFormFragment {
    public static final String ID = "UpdateAddressFormFragment";
    private static Logger logger = Logger.getLogger(UpdateAddressFormFragment.class);
    //    CreatePersonRequestProto.Builder createPersonRequestProto = CreatePersonRequestProto.newBuilder();
    private BindingHelper bindingHelper;

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Resource
    private Context context;

    @Override
    public void bindTextProperties() {

    }

    @Override
    public void setupUIControls() {

    }

    @Override
    public void setupComboBoxes() {

    }

    @Override
    public void setupErrorLabelVisibility() {

    }

    @Override
    public void setupDatePicker() {

    }

    @Override
    public void handleEvents() {

    }

    @Override
    public void validateValues() {

    }
}