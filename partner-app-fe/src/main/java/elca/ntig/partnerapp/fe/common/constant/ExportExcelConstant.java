package elca.ntig.partnerapp.fe.common.constant;

public class ExportExcelConstant {
    public static final String FILE_CHOOSER_TITLE = "Save Excel File";
    public static final String EXTENSTION_FILTER_DESCRIPTION = "Excel Files (*.xlsx)";
    public static final String EXTENSTION_FILTER_EXTENSION = "*.xlsx";
    public static final String[] PERSON_HEADERS = {"Id", "Last Name", "First Name", "Language", "Gender", "Nationality", "AVS Number", "Birth Date", "Civil Status", "Phone Number", "Status"};
    public static final String[] ORGANIZATION_HEADERS = {"ID", "Name", "Additional Name", "Language", "Legal Status", "IDE Number", "Creation Date", "Code NOGA", "Phone Number", "Status"};
    public static final String PERSON_SHEET_NAME = "Person Data";
    public static final String ORGANIZATION_SHEET_NAME = "Organization Data";
    public static final String FILE_EXTENSION = ".xlsx";

    public static final String NO_DATA_MSG = "No data to export";
    public static final String ERROR_WHILE_EXPORTING_MSG = "Error while exporting data: ";
}
