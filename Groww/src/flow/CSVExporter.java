package flow;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVExporter {
	public static void exportTocsv(List<MutualFund> funds) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter("mutualfunds.csv"));
			
			pw.println("Fund Name,AUM,5 Year return,Logo URL");
			for(MutualFund mf:funds) {
				pw.println(mf.getName()+","+mf.getAum()+","+mf.getReturn()+ "," + mf.getLogo());
			}
			System.out.println("CSV created successfully");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

}
