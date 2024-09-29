package elca.ntig.partnerapp.fe.fragment.common;

import elca.ntig.partnerapp.fe.common.model.OrganisationTableModel;
import elca.ntig.partnerapp.fe.common.model.PersonTableModel;
import elca.ntig.partnerapp.fe.utils.BindingHelper;
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

    public void exportPersonDataToExcel(ObservableList<PersonTableModel> data, Window window) {
        if (data == null) {
            logger.info("No data to export to Excel.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");

        // Set extension filter to only allow Excel files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel Files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(window);
        if (file == null) {
            logger.info("File save operation was canceled.");
            return;
        }

        // Ensure the file has the correct extension
        if (!file.getPath().endsWith(".xlsx")) {
            file = new File(file.getPath() + ".xlsx");
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Person Data");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Id", "Last Name", "First Name", "Language", "Gender", "Nationality", "AVS Number", "Birth Date", "Civil Status", "Phone Number", "Status"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            cell.setCellStyle(style);
        }

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

        // Write the Excel file to the selected location
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);
            workbook.close();
            logger.info("Excel file created successfully at: " + file.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Error writing Excel file: " + e.getMessage());
        }
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

    public void exportOrganizationDataToExcel(ObservableList<OrganisationTableModel> data, Window window) {
        if (data == null) {
            logger.info("No data to export to Excel.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");

        // Set extension filter to only allow Excel files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel Files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(window);
        if (file == null) {
            logger.info("File save operation was canceled.");
            return;
        }

        // Ensure the file has the correct extension
        if (!file.getPath().endsWith(".xlsx")) {
            file = new File(file.getPath() + ".xlsx");
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Organization Data");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Name", "Additional Name", "Language", "Legal Status", "IDE Number", "Creation Date", "Code NOGA", "Phone Number", "Status"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            cell.setCellStyle(style);
        }

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

        // Adjust column width to fit content
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the Excel file to the selected location
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);
            workbook.close();
            logger.info("Excel file created successfully at: " + file.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Error writing Excel file: " + e.getMessage());
        }
    }
}
