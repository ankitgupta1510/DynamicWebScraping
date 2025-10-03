package flow;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.Connection;


public class App {
	public static void main(String [] args) {
		String url = "https://www.makemytrip.com/hotels/five_star-hotels-goa.html";
		try {
			Document doc = Jsoup.connect(url)
					.ignoreContentType(true)
					 .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
	                 .header("Accept-Language", "en-US,en;q=0.5")
					.userAgent("Mozilla/5.0")
					.get();
			
					
			System.out.println(doc);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
