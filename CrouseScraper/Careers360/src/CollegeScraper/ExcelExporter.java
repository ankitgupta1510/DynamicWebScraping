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
        
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Product_Type");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Description");
        headerRow.createCell(3).setCellValue("Image_URL");
        headerRow.createCell(4).setCellValue("Brand");
        
        return sheet;
    }
    
    public void addRow(Sheet sheet, int rowNum, String college_type, String name, String description, String image_url, String brand) 
    {
        Row row = sheet.createRow(rowNum);
    
        row.createCell(0).setCellValue(college_type);
        row.createCell(1).setCellValue(name);
        row.createCell(2).setCellValue(description);
        row.createCell(3).setCellValue(image_url);
        row.createCell(4).setCellValue(brand);
        
    }
    
    public void saveAndClose(String fileName) throws IOException 
    {
        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        
        fileOut.close();
        workbook.close();
        
        System.out.println("File saved: " + fileName);
    }
    
}