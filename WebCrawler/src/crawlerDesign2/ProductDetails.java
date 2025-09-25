package crawlerDesign2;

public class ProductDetails {
	private String name;
	private String category;;
	private double priceMRP;
	
	ProductDetails(String name,String category,double priceMRP){
		this.name = name;
		this.category=category;
		this.priceMRP=priceMRP;
		
	}
	
	public String toString() {
        return String.format("Category: %s | Product: %s| MRP: ₹%.2f", 
                           category, name, priceMRP);
    }
	
	 public String getCategory() { return category; }
	 public String getName() { return name; }
	 public double getPriceMRP() { return priceMRP; }

}
