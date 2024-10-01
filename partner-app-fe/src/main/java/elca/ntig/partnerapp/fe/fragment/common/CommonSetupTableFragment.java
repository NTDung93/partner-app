package elca.ntig.partnerapp.fe.fragment.common;

import elca.ntig.partnerapp.fe.common.constant.ExportExcelConstant;
import elca.ntig.partnerapp.fe.common.dialog.DialogBuilder;
import elca.ntig.partnerapp.fe.common.model.OrganisationTableModel;
import elca.ntig.partnerapp.fe.common.model.PersonTableModel;
import elca.ntig.partnerapp.fe.utils.BindingHelper;
import elca.ntig.partnerapp.fe.utils.ObservableResourceFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public abstract class CommonSetupTableFragment<T> {
    private static Logger logger = Logger.getLogger(CommonSetupTableFragment.class);

    public void setTableDefaultMessage(BindingHelper bindingHelper, TableView<?> partnersTable) {
        Label empty = new Label();
        bindingHelper.bindLabelTextProperty(empty, "TableFragment.defaultMessage");
        partnersTable.setPlaceholder(empty);
    }

    public void setCellFactoryDateColumn(TableColumn<T, String> column) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        column.setCellFactory(cell -> new TableCell<T, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    if (StringUtils.isBlank(item)) {
                        setText(null);
                        return;
                    }
                    LocalDate date = LocalDate.parse(item);
                    setText(date.format(dateFormatter));
                }
            }
        });
    }

    public void setCellFactoryAvsNumberColumn(TableColumn<T, String> column) {
        column.setCellFactory(cell -> new TableCell<T, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    StringBuilder formatted = new StringBuilder();
                    for (int i = 0; i < item.length(); i++) {
                        if (i == 3 || i == 7 || i == 11) {
                            formatted.append(".");
                        }
                        formatted.append(item.charAt(i));
                    }
                    setText(formatted.toString());
                }
            }
        });
    }

    public void setCellFactoryCodeNOGANumberColumn(TableColumn<T, String> column) {
        column.setCellFactory(cell -> new TableCell<T, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    if (item.equals("NULL_CODE_NOGA")) {
                        setText(null);
                        return;
                    }
                    setText(item.substring(5));
                }
            }
        });
    }

    public void exportPersonDataToExcel(ObservableList<PersonTableModel> data, Window window, ObservableResourceFactory observableResourceFactory) {
        if (data == null) {
            logger.info(ExportExcelConstant.NO_DATA_MSG);
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(ExportExcelConstant.FILE_CHOOSER_TITLE);

        // Set extension filter to only allow Excel files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(ExportExcelConstant.EXTENSTION_FILTER_DESCRIPTION, ExportExcelConstant.EXTENSTION_FILTER_EXTENSION);
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(window);
        if (file == null) {
            return;
        }

        // Ensure the file has the correct extension
        if (!file.getPath().endsWith(ExportExcelConstant.FILE_EXTENSION)) {
            file = new File(file.getPath() + ExportExcelConstant.FILE_EXTENSION);
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(ExportExcelConstant.PERSON_SHEET_NAME);

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = ExportExcelConstant.PERSON_HEADERS;
        createHeaderRow(headers, headerRow, workbook);

        // Write data rows from the TableView
        for (int i = 0; i < data.size(); i++) {
            PersonTableModel person = data.get(i);
            LocalDate date = null;
            if (StringUtils.isNotBlank(person.getBirthDate())) {
                date = LocalDate.parse(person.getBirthDate());
            }
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(person.getId());
            row.createCell(1).setCellValue(person.getLastName());
            row.createCell(2).setCellValue(person.getFirstName());
            row.createCell(3).setCellValue(StringUtils.capitalize(person.getLanguage().substring(5).toLowerCase()));
            row.createCell(4).setCellValue(StringUtils.capitalize(person.getGender().toLowerCase()));
            row.createCell(5).setCellValue((person.getNationality().contains("NULL")) ? "" : StringUtils.capitalize(person.getNationality().substring(12).toLowerCase()));
            row.createCell(6).setCellValue(formatAvsNumber(person.getAvsNumber()));
            row.createCell(7).setCellValue(StringUtils.isBlank(person.getBirthDate()) ? "" : date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            row.createCell(8).setCellValue((person.getCivilStatus().contains("NULL")) ? "" : StringUtils.capitalize(person.getCivilStatus().toLowerCase()));
            row.createCell(9).setCellValue(person.getPhoneNumber());
            row.createCell(10).setCellValue(StringUtils.capitalize(person.getStatus().toLowerCase()));
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        writeExcelFile(observableResourceFactory, file, workbook);
    }

    public String formatAvsNumber(String avsNumber) {
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < avsNumber.length(); i++) {
            if (i == 3 || i == 7 || i == 11) {
                formatted.append(".");
            }
            formatted.append(avsNumber.charAt(i));
        }
        return formatted.toString();
    }

    public void exportOrganizationDataToExcel(ObservableList<OrganisationTableModel> data, Window window, ObservableResourceFactory observableResourceFactory) {
        if (data == null) {
            logger.info(ExportExcelConstant.NO_DATA_MSG);
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(ExportExcelConstant.FILE_CHOOSER_TITLE);

        // Set extension filter to only allow Excel files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(ExportExcelConstant.EXTENSTION_FILTER_DESCRIPTION, ExportExcelConstant.EXTENSTION_FILTER_EXTENSION);
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(window);
        if (file == null) {
            return;
        }

        // Ensure the file has the correct extension
        if (!file.getPath().endsWith(ExportExcelConstant.FILE_EXTENSION)) {
            file = new File(file.getPath() + ExportExcelConstant.FILE_EXTENSION);
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(ExportExcelConstant.ORGANIZATION_SHEET_NAME);

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = ExportExcelConstant.ORGANIZATION_HEADERS;
        createHeaderRow(headers, headerRow, workbook);

        // Write data rows from the TableView
        for (int i = 0; i < data.size(); i++) {
            OrganisationTableModel organization = data.get(i);
            LocalDate creationDate = null;
            if (StringUtils.isNotBlank(organization.getCreationDate())) {
                creationDate = LocalDate.parse(organization.getCreationDate());
            }
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(organization.getId());
            row.createCell(1).setCellValue(organization.getName());
            row.createCell(2).setCellValue(organization.getAdditionalName());
            row.createCell(3).setCellValue(StringUtils.capitalize(organization.getLanguage().substring(5).toLowerCase()));
            row.createCell(4).setCellValue((organization.getLegalStatus().contains("NULL")) ? "" : StringUtils.capitalize(organization.getLegalStatus().toLowerCase()));
            row.createCell(5).setCellValue(organization.getIdeNumber());
            row.createCell(6).setCellValue(StringUtils.isBlank(organization.getCreationDate()) ? "" : creationDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            row.createCell(7).setCellValue((organization.getCodeNoga().contains("NULL")) ? "" : organization.getCodeNoga().substring(5));
            row.createCell(8).setCellValue(organization.getPhoneNumber());
            row.createCell(9).setCellValue(StringUtils.capitalize(organization.getStatus().toLowerCase()));
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        writeExcelFile(observableResourceFactory, file, workbook);
    }

    private static void createHeaderRow(String[] headers, Row headerRow, Workbook workbook) {
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            cell.setCellStyle(style);
        }
    }

    private static void writeExcelFile(ObservableResourceFactory observableResourceFactory, File file, Workbook workbook) {
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);
            workbook.close();
            showMessageExportExcelSuccessFully(observableResourceFactory);
        } catch (IOException e) {
            logger.error(ExportExcelConstant.ERROR_WHILE_EXPORTING_MSG + e.getMessage());
        }
    }

    private static void showMessageExportExcelSuccessFully(ObservableResourceFactory observableResourceFactory) {
        DialogBuilder dialogBuilder = new DialogBuilder(observableResourceFactory);
        Alert alert = dialogBuilder.buildAlert(Alert.AlertType.INFORMATION, "Dialog.information.title", "Dialog.information.header.success.exportDataToExcel");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            alert.close();
        }
    }
}
