package schoolScraper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CsvExporter {
	public static void exportToCsv(String name,String type,String board,String fees, String Location) {
		try {
			File file = new File("school_data.csv");
			
			boolean fileExist = file.exists() && file.length()>0;
			PrintWriter pw = new PrintWriter(new FileWriter(file,true));
			
			if(!fileExist) {
				pw.println("School name,Type,Board,Fees,Location");
			}
			pw.println(name + "," + type + "," + board + "," + fees + "," + Location);
			System.out.println("csv saved");

           
            pw.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

}
