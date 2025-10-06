package basic;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache {
	public static void main (String [] args) {
	//----------Hashmap do not maintain the order------------
//		HashMap<String,Integer> hm = new HashMap<String, Integer>();
//		hm.put("Apple", 5);
//		hm.put("Orange", 20);
//		hm.put("Pineapple", 100);
//		for(Map.Entry<String, Integer> entry:hm.entrySet()) {
//			System.out.println(entry.getKey() + ":" + entry.getValue());
//	}
		
	//-------Use LHM for LRU----------	
		//-----LHM uses doubly linkedlist internally to keep sorting order------
		LinkedHashMap<String, Integer> linkedhm = new LinkedHashMap<String, Integer>(11,0.2f,true);
		linkedhm.put("Apple", 5);
		linkedhm.put("Orange", 20);
		linkedhm.put("Pineapple", 100);
		
		linkedhm.forEach((key,value)->{
			System.out.println(key+":"+value);
			});

		

		
	}

}
