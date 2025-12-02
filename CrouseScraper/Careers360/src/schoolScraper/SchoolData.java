package schoolScraper;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SchoolData {

    private final String FILE_NAME = "new_school_data.xlsx";

    public void extractSchoolData() throws IOException {

        Workbook xssfWorkbook = new XSSFWorkbook();
        Sheet sheet = xssfWorkbook.createSheet("School_Data");

        int rowNum = 0;
        
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.createCell(0).setCellValue("School_Type");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Description");
        headerRow.createCell(3).setCellValue("Image_URL");
        headerRow.createCell(4).setCellValue("Brand");

        boolean hasData = true;
        int pageNum = 1;

        while (hasData && pageNum < 250) {

            String url = "https://school.careers360.com/schools/schools-in-india?page=" + pageNum + "&ownership=4%2C3%2C1";

            Document doc = null;

            try {
                doc = Jsoup.connect(url).
                        userAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:145.0) Gecko/20100101 Firefox/145.0")
                        .timeout(15000)
                        .get();

            } catch (IOException e) {
                System.err.println("Error fetching listing page " + pageNum + ": " + e.getMessage());
                pageNum++;
                continue;
            }

            Elements schoolCard = doc.select("div.schoollisting_card");

            if (schoolCard.isEmpty()) {
                System.out.println("No more school cards. Ending pagination.");
                break;
            }

            for (Element card : schoolCard) {

                String schoolName = card.select("h2.school_name").text();
                String schoolImage = card.select("div.school_detail div.school_pic a img").attr("src");
                String schoolType = card.select("div.school_info div:nth-child(4) > span:first-child").text();

                String parentInstitution = "";
                String schoolDescription = "";

                String descUrl = card.select("div.school_info .title h2 > a").attr("href");

                try {

                    Document schoolWebDoc = Jsoup.connect(descUrl).userAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:145.0) Gecko/20100101 Firefox/145.0")
                            .timeout(10000)
                            .get();

                    schoolDescription = schoolWebDoc.select("#aboutus div.inner_content > div").text();
                    parentInstitution = schoolWebDoc.select("#schoolInfo div.information > div > div:nth-child(3) > div").text();

                } catch (java.io.IOException e) {
                    System.err.println("Skipping detailed data for " + schoolName + ". Reason: " + e.getMessage());
                }

                Row infoRow = sheet.createRow(rowNum++);

                infoRow.createCell(0).setCellValue(schoolType);
                infoRow.createCell(1).setCellValue(schoolName);
                infoRow.createCell(2).setCellValue(schoolDescription);
                infoRow.createCell(3).setCellValue(schoolImage);
                infoRow.createCell(4).setCellValue(parentInstitution);

            }
            
            System.out.println("Page: " + pageNum + " Scraping complete");
            pageNum++;
            
        }
        
        try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME)) 
        {
            xssfWorkbook.write(fileOut);
            System.out.println("Excel saved successfully.");
        }

        xssfWorkbook.close();
    }
}