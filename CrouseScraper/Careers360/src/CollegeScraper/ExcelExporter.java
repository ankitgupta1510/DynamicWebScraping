package CollegeScraper;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelExporter {
    private Workbook workbook;
    
    public ExcelExporter() {
        this.workbook = new XSSFWorkbook();
    }
    
    public Sheet createSheet(String sheetName) {
        Sheet sheet = workbook.createSheet(sheetName.replaceAll("[\\[\\]:*?/\\\\]", ""));
        
        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("College Name");
        headerRow.createCell(1).setCellValue("Fees");
        headerRow.createCell(2).setCellValue("Ownership");
        headerRow.createCell(3).setCellValue("Location");
        
        return sheet;
    }
    
    public void addRow(Sheet sheet, int rowNum, String name, String fees, String ownership, String location) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(name);
        row.createCell(1).setCellValue(fees);
        row.createCell(2).setCellValue(ownership);
        row.createCell(3).setCellValue(location);
    }
    
    public void saveAndClose(String fileName) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
        System.out.println("File saved: " + fileName);
    }
}
