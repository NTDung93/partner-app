package elca.ntig.partnerapp.fe.fragment;

import elca.ntig.partnerapp.common.proto.enums.common.StatusProto;
import elca.ntig.partnerapp.fe.common.model.PaginationModel;

import java.util.List;

public interface BaseFormFragment {
    void init();

    void bindTextProperties();

    void setupUIControls();

    void setupComboBoxes();

    void setupErrorLabelVisibility();

    void setupDatePicker();

    void handleEvents();

    void handleTypeChange();

    void validateValues();
}
