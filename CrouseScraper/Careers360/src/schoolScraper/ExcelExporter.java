package schoolScraper;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExporter {

    private static final String FILE_NAME = "new_school_data.xlsx";
    private static Workbook workbook;
    private static Sheet sheet;
    private static int rowCount = 0;

    // Initialize Excel file and write header
    public static void initExcel() {
        
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Schools");

        Row headerRow = sheet.createRow(rowCount++);
        headerRow.createCell(0).setCellValue("School_Type");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Description");
        headerRow.createCell(3).setCellValue("Image_URL");
        headerRow.createCell(4).setCellValue("Brand");
    }

    public static void exportToExcel(String name, String type, String board, String fees, String location) {
        Row row = sheet.createRow(rowCount++);
        row.createCell(0).setCellValue(name);
        row.createCell(1).setCellValue(type);
        row.createCell(2).setCellValue(board);
        row.createCell(3).setCellValue(fees);
        row.createCell(4).setCellValue(location);
    }

    public static void saveFile() {
        
        try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME)) {
            
            workbook.write(fileOut);
            workbook.close();
            
            System.out.println("Data exported to " + FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}