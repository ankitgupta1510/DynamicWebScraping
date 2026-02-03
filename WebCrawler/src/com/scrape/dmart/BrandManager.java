package com.scrape.dmart;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class BrandManager {

    private static final String MASTER_SHEET_PATH = "/home/ankit/Downloads/poiJars/Brand_Data.xlsx";

    private static final int MASTER_BRAND_COL_INDEX = 1;

    public static void extractNewBrands(List<ProductData> scrapedProducts) {
        try {
            System.out.println("Reading Master Sheet for comparison...");

            Set<String> masterBrands = readMasterBrands();

            System.out.println("Master Sheet contains " + masterBrands.size() + " brands.");

            Set<String> newBrandsFound = new HashSet<>();

            for (ProductData product : scrapedProducts) {
                String scrapedBrand = product.getBrand();

                if (scrapedBrand != null && !scrapedBrand.trim().isEmpty()) {
                    String normalizedBrand = scrapedBrand.trim().toLowerCase();

                    if (!masterBrands.contains(normalizedBrand)) {
                        newBrandsFound.add(scrapedBrand.trim());
                    }
                }
            }

            System.out.println("Found " + newBrandsFound.size() + " NEW brands that are not in Master Sheet.");

            if (!newBrandsFound.isEmpty()) {
                saveNewBrandsToExcel(newBrandsFound);
            } else {
                System.out.println("No new brands found. Everything exists in Master Sheet.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Read Master Excel
    private static Set<String> readMasterBrands() {
        Set<String> brands = new HashSet<>();
        try (FileInputStream fis = new FileInputStream(MASTER_SHEET_PATH); Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // first sheet

            for (Row row : sheet) {
                Cell cell = row.getCell(MASTER_BRAND_COL_INDEX);
                if (cell != null) {
                    String brandName = cell.getStringCellValue();
                    if (brandName != null && !brandName.trim().isEmpty()) {
                        brands.add(brandName.trim().toLowerCase());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading Master Sheet (Check path): " + e.getMessage());
        }
        return brands;
    }

    // Save Result
    private static void saveNewBrandsToExcel(Set<String> newBrands) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("New_Brands");

            // Header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("New Brand Name");

            int rowNum = 1;
            for (String brand : newBrands) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(brand);
            }

            try (FileOutputStream fos = new FileOutputStream("NewBrandsFound.xlsx")) {
                workbook.write(fos);
            }
            System.out.println("Successfully saved new brands to 'NewBrandsFound.xlsx'");

        } catch (Exception e) {
            System.out.println("Error saving new brands file: " + e.getMessage());
        }
    }
}
