package scrapers;



import models.HealthPolicy;
import models.Policy;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HealthInsuranceScraper implements InsuranceScraper {

    private final Map<String, String> urlsToScrape = new LinkedHashMap<>();

    public HealthInsuranceScraper() {
        urlsToScrape.put("https://www.policybazaar.com/services/get_health_plan_v2.php?group_id=2", "Individual");
        urlsToScrape.put("https://www.policybazaar.com/services/get_health_plan_v2.php?group_id=3", "Family");
        urlsToScrape.put("https://www.policybazaar.com/services/get_health_plan_v2.php?group_id=1", "Senior Citizen");
    }

    @Override
    public List<HealthPolicy> scrape() {
        System.out.println("--- Starting Health Insurance Scraping ---");
        List<HealthPolicy> allPolicies = new ArrayList<>();
        for (Map.Entry<String, String> entry : urlsToScrape.entrySet()) {
             try {
                String jsonResponseString = Jsoup
                		.connect(entry.getKey())
                		.ignoreContentType(true)
                		.userAgent("Mozilla/5.0")
                		  .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
      	                .header("Accept-Language", "en-US,en;q=0.5")
                		.execute()
                		.body();
                JSONObject jsonResponse = new JSONObject(jsonResponseString);
                Document doc = Jsoup.parse(jsonResponse.getString("plan_content"));
                Elements insurerCards = doc.select("div.card");

                for (Element insurerCard : insurerCards) {
                    for (Element plan : insurerCard.select("div.card-content")) {
                        String planName = plan.select("div.plan-name").text();
                        if (!planName.isEmpty()) {
                            allPolicies.add(new HealthPolicy(
                                    entry.getValue(),
                                    planName,
                                    plan.select(".cover-price .label-value:nth-child(1) .value").text(),
                                    plan.select(".cover-price .label-value:nth-child(2) .value").text(),
                                    insurerCard.select(".logo_top img").attr("src")
                            ));
                        }
                    }
                }
                 System.out.println("Successfully scraped " + entry.getValue() + " policies.");
            } catch (Exception e) {
                System.err.println("Error scraping " + entry.getValue() + ": " + e.getMessage());
            }
        }
        return allPolicies;
    }

    @Override
    public String[] getHeaders() {
        return new String[]{"Category", "Plan Name", "Cover Amount", "Starting Premium", "Logo URL"};
    }

    @Override
    public String getSheetName() {
        return "Health Insurance";
    }
}
