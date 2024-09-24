package elca.ntig.partnerapp.fe.fragment.address;

import elca.ntig.partnerapp.common.proto.entity.person.CreatePersonRequestProto;
import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
import elca.ntig.partnerapp.fe.fragment.person.CreatePersonFormFragment;
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
@Fragment(id = CreateAddressFormFragment.ID,
        viewLocation = ResourceConstant.CREATE_PERSON_FORM_FRAGMENT_FXML,
        scope = Scope.PROTOTYPE)
public class CreateAddressFormFragment {
    public static final String ID = "CreateAddressFormFragment";
    private static Logger logger = Logger.getLogger(CreateAddressFormFragment.class);
//    CreatePersonRequestProto.Builder createPersonRequestProto = CreatePersonRequestProto.newBuilder();
    private BindingHelper bindingHelper;

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Resource
    private Context context;
}
