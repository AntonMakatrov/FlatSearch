package by.itacademy.audit.service;

import by.itacademy.audit.core.entity.Audit;
import by.itacademy.audit.service.api.IFileGenerator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.util.List;

@Component
@Qualifier("excel-file-generator")
public class XlsxFileGenerator implements IFileGenerator {

    private String[] headers = {
            "id",
            "action_date",
            "user_id",
            "user_email",
            "user_fio",
            "user_role",
            "action",
            "essence_type",
            "essence_type_id"
    };

    @Override
    public void generateFile(List<Audit> audits, String filename) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("Report Data");
        createHeader(spreadsheet);

        int rowId = 1;
        for (Audit audit : audits) {
            Object[] objectArray = convertToArray(audit);
            createRow(spreadsheet, rowId, objectArray);
            rowId++;
        }
        FileOutputStream out = new FileOutputStream(filename + ".xlsx");
        workbook.write(out);
        out.close();
    }

    private void createHeader(XSSFSheet spreadsheet) {
        createRow(spreadsheet, 0, headers);
    }

    private void createRow(XSSFSheet spreadsheet, int rowId, Object[] objectArray) {
        XSSFRow row = spreadsheet.createRow(rowId);
        int cellId = 0;
        for (Object object : objectArray) {
            Cell cell = row.createCell(cellId);
            cell.setCellValue((String) object);
            cellId++;
        }
    }

    private Object[] convertToArray(Audit audit) {
        return new Object[] {
                audit.getId().toString(),
                audit.getActionDate().toString(),
                audit.getUserId().toString(),
                audit.getUserEmail(),
                audit.getUserFio(),
                audit.getUserRole().toString(),
                audit.getAction().toString(),
                audit.getEssenceType().toString(),
                audit.getEssenceTypeId()
        };
    }
}
