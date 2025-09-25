package crawlerDesign2;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import crawlerDesign.ProductData;

public class DmartScraper {
	private static final String BASE_URL = "https://digital.dmart.in/api/v3/plp/biscuits---cookies-aesc-biscuitsandcookies";
	private static final int PAGE_SIZE = 40;
	private static final String STORE_ID = "10151";
	
	public List<ProductData> scrapeAllProds(){
		List<ProductData> prodsOnCurrPage = new ArrayList();
		
		
		
		
			String jsonResp;
			try {
				jsonResp = fetchPageData();
				 prodsOnCurrPage = parseProductsFromJson(jsonResp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return prodsOnCurrPage;
			
			
			
			
		}
		
	
	
	private String fetchPageData() throws Exception {
		String url = "https://www.dmart.in/_next/data/AOEhMJKtyA2_30DGww1_b/category/biscuits---cookies-aesc-biscuitsandcookies.json?token=biscuits---cookies-aesc-biscuitsandcookies";
		
		Response res = Jsoup.connect(url)
				.userAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:142.0) Gecko/20100101 Firefox/142.0")
				.header("Acept", "application/json, text/plain, */*")
				.header("Accept-Language", "en-US,en;q=0.5")
				.ignoreContentType(true)
				.method(Connection.Method.GET).execute();
		
		return res.body();
	}
	private List<ProductData> parseProductsFromJson(String jsonString) {
        List<ProductData> products = new ArrayList<>();
        
        try {
            JSONObject jsonObj = new JSONObject(jsonString);
            
            if (jsonObj.has("products")) {
                JSONArray productsArray = jsonObj.getJSONArray("products");
                
                for (int i = 0; i < productsArray.length(); i++) {
                    JSONObject product = productsArray.getJSONObject(i);
                    
                    String categoryName = product.optString("categoryName", "N/A");
                    String productName = product.optString("name", "N/A");
                    
                    JSONArray skus = product.optJSONArray("sKUs");
                    if (skus != null) {
                        for (int j = 0; j < skus.length(); j++) {
                            JSONObject sku = skus.getJSONObject(j);
                            
                            String variantWtStr = sku.optString("variantTextValue", "0 g")
                                                    .replace("g", "").trim();
                            double variantWt = parseWeight(variantWtStr);
                            
                            

                            double priceMRP = sku.optDouble("priceMRP", 0.0);
                            
                            ProductData productData = new ProductData(
                                categoryName, productName, variantWt, priceMRP
                            );
                            products.add(productData);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
        }
        
        return products;
    }
	
	private double parseWeight(String weightStr) {
        try {
            return Double.parseDouble(weightStr);
        } catch (Exception e) {
            return 0.0;
        }
    }
	

	
	
	
	
	
	
	
	
	
	
	
}
