package elca.ntig.partnerapp.fe.fragment;

import elca.ntig.partnerapp.common.proto.enums.common.StatusProto;
import elca.ntig.partnerapp.fe.common.pagination.PaginationModel;

import java.util.List;

public interface BaseFormFragment {
    void init();

    void bindTextProperties();

    void setupUIControls();

    void setupComboBoxes();

    void setupErrorLabelVisibility();

    void setupDatePicker();

    void handleEvents();

    void handlePagination(PaginationModel paginationModel);

    void handleClearCriteriaButtonOnClick();

    void handleSearchButtonOnClick();

    void handleTypeChange();

    List<StatusProto> getStatuses();

    void validateValues();
}
