package crawlerDesign;


import java.io.IOException;
import java.util.List;

import io.github.millij.poi.SpreadsheetReadException;

import io.github.millij.poi.ss.writer.SpreadsheetWriter;
import io.github.millij.poi.ss.writer.XlsxWriter;

public class ExcelExporter {
	
	public static void exportToExcel(List<ProductData> products) throws SpreadsheetReadException, IOException {
//		final File xlsFile = new File("<path_to_file>");
//	    final XlsReader reader = new XlsReader();
//	    final List<Employee> employees = reader.read(Employee.class, xlsFile);
	    
//	    products = reader.read(ProductData.class,xlsFile);
	    
	    
//	    writer
	    try {
	    final SpreadsheetWriter writer = new XlsxWriter();
	    writer.addSheet(ProductData.class, products);
	    writer.write("newproducts.xlsx");
	    System.out.println("excel file created:newproducts.xlsx");
	    }catch(Exception e) {
	    	System.out.print("Exceptionn"+ e.getMessage());
	    }
	    
	    
		
	}

}
