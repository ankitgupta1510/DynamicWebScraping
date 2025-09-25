package scrapers;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import models.BikePolicy;

public class BikeInsuranceScraper implements InsuranceScraper {

    public List<BikePolicy> scrape() {
        System.out.println("\n--- Starting Bike Insurance Scraping ---");
        List<BikePolicy> policies = new ArrayList<>();
        String bikeUrl = "https://www.policybazaar.com/services/gettwservices.php";
        String bikePayload = "{\"task\":\"newtopplansfullwidth\"}";

        try {
            Response response = Jsoup.connect(bikeUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .header("Accept-Language", "en-US,en;q=0.5")
                    .ignoreContentType(true)
                    .timeout(15000)
                    .requestBody(bikePayload)
                    .method(Connection.Method.POST)
                    .execute();
            String htmlContent = response.body();

            // Parse HTML
            Document doc = Jsoup.parse(htmlContent);

            // Scrape Comprehensive Plans
            Elements comprehensivePlans = doc.select(".comprehensive-plan .plan-card");
            for (Element plan : comprehensivePlans) {
                policies.add(new BikePolicy(
                        "Comprehensive Plan",
                        plan.select(".insurer-name span").text(),
                        plan.select(".price span").text(),
                        plan.select(".insurer-name img").attr("src")));
            }

            // Scrape Third Party Plans
            Elements thirdPartyPlans = doc.select(".third-party-plan .plan-card");
            for (Element plan : thirdPartyPlans) {
                policies.add(new BikePolicy(
                        "Third Party Plan",
                        plan.select(".insurer-name span").text(),
                        plan.select(".price span").text(),
                        plan.select(".insurer-name img").attr("src")));
            }

            // Scrape Own Damage Plans
            Elements ownDamagePlans = doc.select(".own-damage-plan .plan-card");
            for (Element plan : ownDamagePlans) {
                policies.add(new BikePolicy(
                        "Own Damage Plan",
                        plan.select(".insurer-name span").text(),
                        plan.select(".price span").text(),
                        plan.select(".insurer-name img").attr("src")));
            }

            System.out.println("Successfully scraped " + policies.size() + " Bike Insurance policies.");
        } catch (Exception e) {
            System.err.println("Error scraping Bike Insurance: " + e.getMessage());
        }
        return policies;
    }

    @Override
    public String[] getHeaders() {
        return new String[] { "Plan Type", "Plan Name", "Premium", "Logo URL" };
    }

    @Override
    public String getSheetName() {
        return "Bike Insurance";
    }

}