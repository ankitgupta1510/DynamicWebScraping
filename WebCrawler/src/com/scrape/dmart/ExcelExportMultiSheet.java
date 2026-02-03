package com.scrape.dmart;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelExportMultiSheet {

    public static void exportToExcel(List<ProductData> products) {
        try {
            // Group products by category
            Map<String, List<ProductData>> categoryMap = new HashMap<>();

            for (ProductData product : products) {
                String category = product.getCategory();
                if (category == null)
                    category = "Other";

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
                headerRow.createCell(0).setCellValue("Name");
                headerRow.createCell(1).setCellValue("Image_URL");
                headerRow.createCell(2).setCellValue("Brand");
                headerRow.createCell(3).setCellValue("Product_Type");
                headerRow.createCell(4).setCellValue("Description");

                // Data rows
                int rowNum = 1;
                for (ProductData product : categoryProducts) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(product.getName());
                    row.createCell(1).setCellValue(product.getImgUrl());
                    row.createCell(2).setCellValue(product.getBrand());
                    row.createCell(3).setCellValue(product.getCategory());
                    row.createCell(4).setCellValue(product.getDescription());

                }

            }

            // Save file
            FileOutputStream fileOut = new FileOutputStream("DmartProductsUpdated.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

            System.out.println("Excel file created: DmartProductsUpdated.xlsx");

        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
