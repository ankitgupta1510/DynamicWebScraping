package scrapers;

import java.util.List;
import models.Policy;

public interface InsuranceScraper {
    
    List<? extends Policy> scrape();

    String[] getHeaders();

    String getSheetName();
}