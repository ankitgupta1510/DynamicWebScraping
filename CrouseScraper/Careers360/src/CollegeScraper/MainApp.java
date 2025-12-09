package CollegeScraper;

import java.io.IOException;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class MainApp {

    public static void main(String[] args) throws IOException {

        ExcelExporter excelExporter = new ExcelExporter();

        Map<String, String> urlMap = BrowseByStream.streamScraper();

        for ( Map.Entry<String, String> entry : urlMap.entrySet() ) 
        {
            String streamName = entry.getKey();
            String urlString = entry.getValue();

            Sheet sheet = excelExporter.createSheet(streamName);

            int rowNum = 1;

            boolean hasData = true;
            int pageNum = 1;

            while (hasData) {

                String url = "";

                if (urlString.equals("https://finance.careers360.com")) {
                    url = urlString + "/colleges/list-of-commerce-colleges-in-india" + "?page=" + pageNum;
                } else if (urlString.equals("https://hospitality.careers360.com")) {
                    url = urlString + "/colleges/list-of-hospitality-tourism-colleges-in-india" + "?page=" + pageNum;
                } else if (urlString.equals("https://it.careers360.com")) {
                    url = urlString + "/colleges/list-of-bca-mca-colleges-in-india" + "?page=" + pageNum;
                } else if (urlString.equals("https://media.careers360.com")) {
                    url = urlString + "/colleges/list-of-media-journalism-colleges-in-india" + "?page=" + pageNum;
                } else {
                    url = urlString + "/colleges/ranking" + "?page=" + pageNum;
                }
                
                Document doc = Jsoup.connect(url)
                                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/141.0.0.0 Safari/537.36")
                                    .header("cookie", "WZRK_G=f2e7755f59cf47dbb343cfca086a5caf; _gcl_au=1.1.1590437675.1764238582; _fbp=fb.1.1764238582317.406446817229918834; _gid=GA1.2.2045716678.1765259153; _clck=17uqqxu%5E2%5Eg1p%5E0%5E2157; prevPath=undefined; currentPath=%2Fcolleges%2Flist-of-commerce-colleges-in-india; common_sign_up_show=1; user-visitor-key=19b01b50e7a3fa0a; __gads=ID=c31fc4147d406a74:T=1764238628:RT=1765262246:S=ALNI_MYsjCOYvLgfanFSVy_XC3B37bu4iQ; __gpi=UID=000011bdb37dfd47:T=1764238628:RT=1765262246:S=ALNI_MYNt5oz08x-0PRGgyACvFwTqBzMBw; __eoi=ID=c4c9ee0fc7dd0da6:T=1764238628:RT=1765262246:S=AA-AfjamOp6a1kEqtJywtamoi5QF; _ga_9DWNLY4G2G=GS2.1.s1765259152$o8$g1$t1765262254$j53$l0$h0; _ga=GA1.2.462201392.1764238582; WZRK_S_47W-5KW-RZ7Z=%7B%22p%22%3A16%2C%22s%22%3A1765259152%2C%22t%22%3A1765263077%7D; _ga_GCM1JTVF8P=GS2.1.s1765259153$o8$g1$t1765263079$j55$l0$h1359909184; _clsk=190ccny%5E1765263601233%5E12%5E0%5Eh.clarity.ms%2Fcollect")
                                    .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                                    .get();
                
                if(doc == null) 
                {
                    System.out.println("No Document found for url" + url);
                    return;
                }

                Elements collegeCards = doc.select("div#college-list div.card_block");

                if (collegeCards.size() == 0) {
                    hasData = false;
                    System.out.println("No more data found. Ending pagination.");
                    break;
                }

                for (Element card : collegeCards) {
                    
                    String collegeName = "";
                    String collegeImage = "";
                    String collegeType = "";

                    if (url.equals(urlString + "/colleges/list-of-bca-mca-colleges-in-india" + "?page=" + pageNum) || 
                            url.equals(urlString + "/colleges/list-of-hospitality-tourism-colleges-in-india"+ "?page=" + pageNum) || 
                            url.equals(urlString + "/colleges/list-of-media-journalism-colleges-in-india" + "?page="+ pageNum) || 
                            url.equals(urlString + "/colleges/list-of-commerce-colleges-in-india" + "?page=" + pageNum)) 
                    {
                        
                        collegeName = card.select("h3.college_Name").text();
                        System.out.println("College name: " + collegeName);
                        
                        collegeImage = card.select("div.image_block img").attr("src");
                        System.out.println("College image: " + collegeImage);
                        
                        collegeType = card.select("p.college-listing-rating").text() + " College";
                        System.out.println("College Type: " + collegeType);
                        
                    } else {
                        
                        collegeName = card.select("h3.college_Name").text();
                        System.out.println("College name: " + collegeName);
                        
                        collegeImage = card.select("div.image_block img").attr("src");
                        System.out.println("College image: " + collegeImage);
                        
                        collegeType = card.select("p.college-listing-rating").text() + " College";
                        System.out.println("College Type: " + collegeType);
                        
                    }

                    if (!collegeName.isEmpty())
                        excelExporter.addRow(sheet, rowNum++, collegeType, collegeName, "", collegeImage, "");  // because the description is too long to accommodate and also their is no info for the parentInstitution
                }
                
                pageNum++;
                
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            
            excelExporter.saveAndClose("new_colleges_data.xlsx");    
        }
    }
}