package scrapers;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Connection.Response;

import models.CarPolicy;
import models.Policy;
import models.TermPolicy;

public class CarInsuranceScraper implements InsuranceScraper {

	@Override
	public List<? extends Policy> scrape() {
		
		System.out.println("Scraping car plolicies");
		String url = "https://www.policybazaar.com/services/getcarservices.php";
		String payload = "[{\"task\":\"topplans\",\"ismobile\":false,\"topPlanNew\":1,\"comprehensive\":1,\"plantypeid\":1}]";
//		String payload = "[{\"task\":\"topplans\",\"ismobile\":false,\"topPlanNew\":1,\"comprehensive\":1,\"plantypeid\":1}]";
		List<CarPolicy> policies = new ArrayList<CarPolicy>();
		
		try {
			
			Response response = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
	                .header("Accept-Language", "en-US,en;q=0.5")
                    .ignoreContentType(true)  
                    .timeout(15000)
                    .requestBody(payload)
                    .method(Connection.Method.POST)
                    .execute();
     	   String htmlContent = response.body();
     	   
     	   Document doc = Jsoup.parse(htmlContent);
     	   
     	   Elements plans = doc.select(".planCard");
     	   for (Element plan : plans) {
               
               String planType = plan.select(".planType .headingV3").text();
              
               String planName = plan.select(".top-plan-widget").attr("data-label");
           
               String premium = plan.select(".primaryBtnV2").text();

               String logoUrl = plan.select(".logo img").attr("src");

               if (!planType.isEmpty() && !premium.isEmpty()) {
                   policies.add(new CarPolicy(planType, planName, premium, logoUrl));
               }
           }
           System.out.println("Successfully scraped " + policies.size() + " Car Insurance policies.");
     	   
							
			
		}catch(Exception e) {
			e.getMessage();
		}

		
		
		
		return policies;
	}

	@Override
	public String[] getHeaders() {
		 return new String[]{"Plan Type", "Plan Name", "Premium", "Logo URL"};
		
	}

	@Override
	public String getSheetName() {
		// TODO Auto-generated method stub
		return "Car Insurance";
	}
	 

}
