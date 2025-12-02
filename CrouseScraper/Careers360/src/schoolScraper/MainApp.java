package schoolScraper;

import java.io.IOException;

public class MainApp {
    
    public static void main(String[] args) throws IOException{

        SchoolData schoolData = new SchoolData();
        schoolData.extractSchoolData();
        
    }
}