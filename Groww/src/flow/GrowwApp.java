package flow;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GrowwApp {
	public static void main(String[] args) {
		
		List<MutualFund> fundList = new ArrayList<>();
		
			
			String url = "https://groww.in/v1/api/search/v1/derived/scheme?available_for_investment=true&doc_type=scheme&max_aum=&page=1&plan_type=Direct&q=&size=15&sort_by=3\n";

			

			try {
				String doc = Jsoup.connect(url)
						.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
						.ignoreContentType(true)
						.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
						.header("Accept-Language", "en-US,en;q=0.5").execute().body();

				JSONObject jsonObj = new JSONObject(doc);
				JSONArray jsonArr = jsonObj.getJSONArray("content");
				for (int i = 0; i < jsonArr.length(); i++) {
					JSONObject fund = jsonArr.getJSONObject(i);
					String fundName = fund.getString("direct_scheme_name");
					Double aum = fund.optDouble("aum",0);
//			 Double fiveYrReturn = fund.has("return5y")?fund.getDouble("return5y"):0.0;
					Double fiveYrReturn = fund.optDouble("return5y", 0.0);

					fundList.add(new MutualFund(fundName, aum, fiveYrReturn));

				}
				
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		
		CSVExporter.exportTocsv(fundList);
	}

}
