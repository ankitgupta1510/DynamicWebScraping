package main;



import models.Policy;
import scrapers.BikeInsuranceScraper;
import scrapers.CarInsuranceScraper;
import scrapers.HealthInsuranceScraper;
import scrapers.HomeInsuranceScraper;
import scrapers.InsuranceScraper;
import scrapers.IntlTravelScraper;
import scrapers.TermInsuranceScraper;
import scrapers.TravelInsuranceScraper;
import services.ExcelWriterService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        
        List<InsuranceScraper> scrapers = new ArrayList<>();
        scrapers.add(new HealthInsuranceScraper());
        scrapers.add(new TermInsuranceScraper());
        scrapers.add(new CarInsuranceScraper());
         scrapers.add(new BikeInsuranceScraper());
         scrapers.add(new TravelInsuranceScraper());
         scrapers.add(new IntlTravelScraper());
         scrapers.add(new HomeInsuranceScraper());
        
        // Excel service 
        String fileName = "InsuranceData.xlsx";
        ExcelWriterService excelWriter = new ExcelWriterService(fileName);

        // data Excel me write.
        for (InsuranceScraper scraper : scrapers) {
            List<? extends Policy> policies = scraper.scrape();
            if (policies != null && !policies.isEmpty()) {
                excelWriter.createSheetsWithHeader(scraper.getSheetName(), scraper.getHeaders());
                List<String[]> dataForExcel = policies.stream().map(Policy::toArray).collect(Collectors.toList());
                excelWriter.writeDataToSheet(scraper.getSheetName(), dataForExcel);
            }
        }
        
        //Khali sheets banane ke liye
//        excelWriter.createSheetsWithHeader("Car Insurance");
//        excelWriter.createSheetsWithHeader("Travel Insurance");
        
       
        try {
            excelWriter.saveWorkbook();
            System.out.println("Success data has been written to " + fileName);
        } catch (Exception e) {
            System.err.println(" Error writing to Excel file: " + e.getMessage());
        }
    }
}