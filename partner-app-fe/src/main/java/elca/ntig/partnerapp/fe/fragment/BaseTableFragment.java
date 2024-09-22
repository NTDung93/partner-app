package elca.ntig.partnerapp.fe.fragment;

public interface BaseTableFragment {
    void init();

    void bindTextProperties();

    void initializeTable();

    void initializePagination();

    void setupSortListener();
}
