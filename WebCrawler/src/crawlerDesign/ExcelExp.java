package crawlerDesign;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExp {
    
    public static void exportToExcel(List<ProductData> products) {
        try {
            // Group products by category
            Map<String, List<ProductData>> categoryMap = new HashMap<>();
            
            for (ProductData product : products) {
                String category = product.getCategory();
                if (category == null) category = "Other";
                
                if (!categoryMap.containsKey(category)) {
                    categoryMap.put(category, new ArrayList<>());
                }
                categoryMap.get(category).add(product);
            }
            
            // Create workbook
            Workbook workbook = new XSSFWorkbook();
            
            // Create sheet for each category
            for (String category : categoryMap.keySet()) {
                List<ProductData> categoryProducts = categoryMap.get(category);
                
                // Create sheet
                Sheet sheet = workbook.createSheet(category);
                
                // Header
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Product Name");
                headerRow.createCell(1).setCellValue("Weight");
                headerRow.createCell(2).setCellValue("Price");
                
                // Data rows
                int rowNum = 1;
                for (ProductData product : categoryProducts) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(product.getName());
                    row.createCell(1).setCellValue(product.getWeight());
                    row.createCell(2).setCellValue(product.getPriceMRP());
                }
                
                // Auto-size
                sheet.autoSizeColumn(0);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
            }
            
            // Save file
            FileOutputStream fileOut = new FileOutputStream("newproducts.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            
            System.out.println("Excel file created: newproducts.xlsx");
            
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}