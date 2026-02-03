package com.scrape.dmart;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelExportSingleSheet {

    public static void exportToExcel(List<ProductData> products) {
        try {
            Workbook workbook = new XSSFWorkbook();

            Sheet sheet = workbook.createSheet("All_Data_Dmart");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Name");
            headerRow.createCell(1).setCellValue("Image_URL");
            headerRow.createCell(2).setCellValue("Brand");
            headerRow.createCell(3).setCellValue("Product_Type");
            headerRow.createCell(4).setCellValue("Description");

            int rowNum = 1;

            for (ProductData product : products) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(product.getName());
                row.createCell(1).setCellValue(product.getImgUrl());
                row.createCell(2).setCellValue(product.getBrand());
                row.createCell(3).setCellValue(product.getCategory());
                row.createCell(4).setCellValue(product.getDescription());
            }

            // Save file
            FileOutputStream fileOut = new FileOutputStream("DmartProductsMaster.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

            System.out.println("Excel file created: DmartProductsMaster.xlsx with " + (rowNum - 1) + " rows.");

        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
