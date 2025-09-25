package crawlerDesign2;

import java.util.List;

import crawlerDesign.ProductData;
import crawlerDesign.ScrapingLogic;

public class App {
	
	public static void main(String[] args) {
		DmartScraper scraper = new DmartScraper();
        List<ProductData> reqProducts = scraper.scrapeAllProds();
        
        System.out.println("Total products= " + reqProducts.size());
        
        for (ProductData product : reqProducts) {
            System.out.println(product);
        }
		
	}

}
