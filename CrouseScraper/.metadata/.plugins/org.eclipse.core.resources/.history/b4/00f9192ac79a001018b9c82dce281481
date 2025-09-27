package flow;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestApp {
    
    // Yeh method ek URL aur category se saare plans scrape karke list return karta hai.
    public static List<HealthPolicy> scrapePoliciesFromUrl(String url, String category) {
        List<HealthPolicy> policies = new ArrayList<>();
        try {
            String jsonResponseString = Jsoup.connect(url)
            		.ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
	                .header("Accept-Language", "en-US,en;q=0.5")
                    .execute()
                    .body();
            JSONObject jsonResponse = new JSONObject(jsonResponseString);
            String htmlContent = jsonResponse.getString("plan_content");
            Document doc = Jsoup.parse(htmlContent);

            Elements insurerCards = doc.select("div.card");

            for (Element insurerCard : insurerCards) {
                Elements individualPlans = insurerCard.select("div.card-content");
                for (Element plan : individualPlans) {
                    String planName = plan.select("div.plan-name").text();
                    if (!planName.isEmpty()) {
                        String coverAmount = plan.select("div.cover-price > div.label-value:nth-child(1) > div.value").text();
                        String monthlyPremium = plan.select("div.cover-price > div.label-value:nth-child(2) > div.value").text();
                        String logoUrl = insurerCard.select("div.logo_top img").attr("src");
                        
                        policies.add(new HealthPolicy(category, planName, coverAmount, monthlyPremium, logoUrl));
                    }
                }
            }
            System.out.println("Successfully scraped " + policies.size() + " policies from category: " + category);
        } catch (Exception e) {
            System.err.println("Error scraping URL " + url + ": " + e.getMessage());
        }
        return policies;
    }

    public static void main(String[] args) {
        // 1. Teeno URLs ko unki category ke saath map mein store karein.
        Map<String, String> urlsToScrape = new LinkedHashMap<>();
        urlsToScrape.put("https://www.policybazaar.com/services/get_health_plan_v2.php?group_id=2", "Individual");
        urlsToScrape.put("https://www.policybazaar.com/services/get_health_plan_v2.php?group_id=3", "Family");
        urlsToScrape.put("https://www.policybazaar.com/services/get_health_plan_v2.php?group_id=1", "Senior Citizen");
        
        // 2. Ek list banayein jisme saare scraped policies store honge.
        List<HealthPolicy> allHealthPolicies = new ArrayList<>();
        
        // 3. Har ek URL ko scrape karein aur results ko main list mein add karein.
        for (Map.Entry<String, String> entry : urlsToScrape.entrySet()) {
            String url = entry.getKey();
            String category = entry.getValue();
            allHealthPolicies.addAll(scrapePoliciesFromUrl(url, category));
        }
        
        // 4. Excel file likhne ka process shuru karein.
        if (!allHealthPolicies.isEmpty()) {
            try {
                String fileName = "InsuranceData.xlsx";
                ExcelWriterService excelWriter = new ExcelWriterService(fileName);

                // Sheets aur unke headers define karein
                excelWriter.createSheetsWithHeader("Health Insurance", "Category", "Plan Name", "Cover Amount", "Starting Premium", "Logo URL");
                excelWriter.createSheetsWithHeader("Term Insurance"); // Baad mein use ke liye
                excelWriter.createSheetsWithHeader("Car Insurance");   // Baad mein use ke liye
                excelWriter.createSheetsWithHeader("Bike Insurance");  // Baad mein use ke liye

                // Scraped data ko Health Insurance sheet mein likhein
                List<String[]> dataForExcel = allHealthPolicies.stream().map(HealthPolicy::toArray).collect(Collectors.toList());
                excelWriter.writeDataToSheet("Health Insurance", dataForExcel);

                // Workbook ko save karein
                excelWriter.saveWorkbook();
                System.out.println("\n✅ Success! Data has been written to " + fileName);

            } catch (Exception e) {
                System.err.println("Error writing to Excel file: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No data was scraped. Excel file not created.");
        }
    }
}