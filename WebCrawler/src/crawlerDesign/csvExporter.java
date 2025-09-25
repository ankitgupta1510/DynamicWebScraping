package crawlerDesign;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class csvExporter {
	 public static void exportToCsv(List<ProductData> products) {
	        try (PrintWriter writer = new PrintWriter(new FileWriter("productsNew.csv"))) {
	            
	            // title
	            writer.println("Category,Product Name,Weight,MRP");
	            
	            // csv
	            for (ProductData product : products) {
	                writer.println(product.getCategory() + "," +
	                              product.getName() + "," +
	                              product.getWeight() + "," +
	                              product.getPriceMRP()
	                              );
	            }
	            
	            System.out.println("CSV file created: productsNew.csv");
	            
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        } 

}
}
