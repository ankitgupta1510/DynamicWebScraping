package crawlerDesign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelExporterDemo {
	public static void exportToExcel(List<ProductData> products) {
		Map<String,List<ProductData>> categoryMap = new HashMap();
		
		for(ProductData prods:products) {
			String category = prods.getCategory();
			if(category==null) category="Other";
			if(!categoryMap.containsKey(prods.getCategory())) {
				categoryMap.put(category,new ArrayList());
			}
			categoryMap.get(category).add(prods);
		}
	}

}
