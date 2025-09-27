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
        
        Map<String, String> urlsToScrape = new LinkedHashMap<>();
        urlsToScrape.put("https://www.policybazaar.com/services/get_health_plan_v2.php?group_id=2", "Individual");
        urlsToScrape.put("https://www.policybazaar.com/services/get_health_plan_v2.php?group_id=3", "Family");
        urlsToScrape.put("https://www.policybazaar.com/services/get_health_plan_v2.php?group_id=1", "Senior Citizen");
        
        
        List<HealthPolicy> allHealthPolicies = new ArrayList<>();
        
        
        for (Map.Entry<String, String> entry : urlsToScrape.entrySet()) {
            String url = entry.getKey();
            String category = entry.getValue();
            allHealthPolicies.addAll(scrapePoliciesFromUrl(url, category));
        }
        
        
        if (!allHealthPolicies.isEmpty()) {
            try {
                String fileName = "InsuranceData.xlsx";
                ExcelWriterService excelWriter = new ExcelWriterService(fileName);

                                excelWriter.createSheetsWithHeader("Health Insurance", "Category", "Plan Name", "Cover Amount", "Starting Premium", "Logo URL");
               
              
                List<String[]> dataForExcel = allHealthPolicies.stream().map(HealthPolicy::toArray).collect(Collectors.toList());
                excelWriter.writeDataToSheet("Health Insurance", dataForExcel);

                
                excelWriter.saveWorkbook();
                

            } catch (Exception e) {
                System.err.println("Error writing to Excel file: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No data was scraped. Excel file not created.");
        }
    }
}