package elca.ntig.partnerapp.fe.fragment.person;

import elca.ntig.partnerapp.common.proto.entity.person.SearchPeopleCriteriasProto;
import elca.ntig.partnerapp.common.proto.entity.person.SearchPeoplePaginationRequestProto;
import elca.ntig.partnerapp.common.proto.enums.common.StatusProto;
import elca.ntig.partnerapp.fe.common.constant.ResourceConstant;
import elca.ntig.partnerapp.fe.common.model.PaginationModel;
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

import java.util.Collections;
import java.util.List;

@Component
@Fragment(id = UpdatePersonFormFragment.ID,
        viewLocation = ResourceConstant.UPDATE_PERSON_FORM_FRAGMENT_FXML,
        scope = Scope.PROTOTYPE)
public class UpdatePersonFormFragment extends CommonSetupFormFragment implements BaseFormFragment {
    public static final String ID = "UpdatePersonFormFragment";
    private static Logger logger = Logger.getLogger(UpdatePersonFormFragment.class);
    SearchPeopleCriteriasProto.Builder searchPeopleCriteriaProto = SearchPeopleCriteriasProto.newBuilder();
    SearchPeoplePaginationRequestProto.Builder searchPeoplePaginationRequestProto = SearchPeoplePaginationRequestProto.newBuilder();
    private BindingHelper bindingHelper;

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Resource
    private Context context;

    @Override
    public void init() {

    }

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
    public void handleTypeChange() {

    }

    @Override
    public void validateValues() {

    }
}