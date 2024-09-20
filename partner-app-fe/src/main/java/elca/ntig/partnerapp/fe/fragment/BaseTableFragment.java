package elca.ntig.partnerapp.fe.fragment;

public interface BaseTableFragment {
    void bindTextProperties();

    void initializeTable();

    void initializePagination();

    void setupSortListener();
}
