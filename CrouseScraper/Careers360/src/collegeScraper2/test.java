package collegeScraper2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class test {
    
    private FileWriter csvWriter;
    
    public test() throws IOException {
        // Initialize CSV file
        csvWriter = new FileWriter("colleges_data.csv");
        csvWriter.write("Category,Institution,Course,Fees,Type,Location\n");
    }
    
    public static void main(String[] args) {
        try {
            test scraper = new test();
            scraper.scrapeAllCategories();
            scraper.close();
            System.out.println("\nData exported to colleges_data.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void scrapeAllCategories() throws IOException {
        Map<String, String> categories = Map.of(
            "Management", "https://bschool.careers360.com/colleges/ranking",
            "Engineering", "https://www.careers360.com/colleges/list-of-engineering-colleges-in-india",
            "Medical", "https://medicine.careers360.com/colleges/list-of-medical-colleges-in-india"
        );
        
        for (Map.Entry<String, String> entry : categories.entrySet()) {
            scrapeWithPagination(entry.getKey(), entry.getValue());
        }
    }
    
    private void scrapeWithPagination(String category, String baseUrl) throws IOException {
        int totalColleges = 0;
        
        for (int page = 1; page <= 5; page++) { // Scrape first 5 pages
            String pageUrl = baseUrl + "?page=" + page;
            
            try {
                Document doc = Jsoup.connect(pageUrl)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                        .timeout(15000)
                        .get();
                
                // Card-based extraction for proper mapping
                Elements cards = doc.select(".card_block, .facet-tuple-inner-container");
                
                if (cards.isEmpty()) break;
                
                for (Element card : cards) {
                    String institution = getTextFromCard(card, "h3.college_name_heading", "h3 a", ".college-name");
                    String course = getTextFromCard(card, ".course-name", ".program");
                    String fees = getTextFromCard(card, "li.college-fees-text", "*:contains(₹)");
                    String type = getTextFromCard(card, "*:contains(Private), *:contains(Public)");
                    String location = getTextFromCard(card, ".location", "*:contains(Delhi), *:contains(Mumbai)");
                    
                    // Write to CSV
                    csvWriter.write(String.format("%s,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\n",
                            category, 
                            cleanCsvText(institution),
                            cleanCsvText(course),
                            cleanCsvText(fees),
                            cleanCsvText(type),
                            cleanCsvText(location)));
                    
                    totalColleges++;
                }
                
                System.out.println(category + " - Page " + page + ": " + cards.size() + " colleges");
                TimeUnit.SECONDS.sleep(1); // Respectful scraping
                
            } catch (Exception e) {
                System.out.println("Error on page " + page + ": " + e.getMessage());
                break;
            }
        }
        
        System.out.println("Total " + category + " colleges: " + totalColleges);
    }
    
    private String getTextFromCard(Element card, String... selectors) {
        for (String selector : selectors) {
            Element element = card.selectFirst(selector);
            if (element != null && !element.text().trim().isEmpty()) {
                return element.text().trim();
            }
        }
        return "Not Available";
    }
    
    private String cleanCsvText(String text) {
        if (text == null) return "Not Available";
        return text.replace("\"", "\"\"").replace("\n", " ").replace("\r", "");
    }
    
    private void close() throws IOException {
        csvWriter.close();
    }
}
