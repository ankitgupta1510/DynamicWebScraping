package CollegeScraper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class BrowseByStream {

    public static Map<String, String> streamScraper() throws IOException {

        String url = "https://www.careers360.com/";
 
        Map<String, String> streamMap = new HashMap<>();

        Document doc = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0")
                            .get();

        Elements streams = doc.select("#domain-engineering-li > a");

        for (Element stream : streams) {

            String name = stream.text();
            String streamUrl = stream.attr("href");

            if ( !name.equals("Competition") && !name.equals("Learn") && !name.equals("Online Courses and Certifications") && !name.equals("Study Abroad") )
                streamMap.put(name, streamUrl);
            
        }

        return streamMap;
    }
}