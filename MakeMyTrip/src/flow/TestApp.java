package flow;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

import java.io.IOException;

public class TestApp {
    public static void main(String[] args) throws IOException {

        String requestId = "2d6f35c7-867a-47ce-8398-fad51abccef4";

        String url = "https://mapi.makemytrip.com/clientbackend/cg/search-hotels/DESKTOP/2"
                + "?cityCode=CTGOI&language=eng&region=in&currency=INR"
                + "&idContext=B2C&countryCode=IN&requestId=" + requestId;

//        String payload = ;
        try {
        	Response res = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .method(Connection.Method.POST)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36")
                    .header("Content-Type", "application/json")
                    .requestBody(payload)
                    .timeout(60000)
                    .execute();

            int statusCode = res.statusCode();
            System.out.println("Status Code: " + statusCode);
            
            String response = res.body();
            System.out.println("\nAPI Response:");
            System.out.println(response);
            
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}