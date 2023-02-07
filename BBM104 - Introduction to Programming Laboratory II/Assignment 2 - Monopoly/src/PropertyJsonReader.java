import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

public class PropertyJsonReader {
     private HashMap<Integer, String[]> lands = new HashMap<Integer, String[]>(22);
     private HashMap<Integer, String[]> railRoads = new HashMap<Integer, String[]>(4);
     private HashMap<Integer, String[]> companies = new HashMap<Integer, String[]>(2);
	 
     public PropertyJsonReader(){
         JSONParser processor = new JSONParser();
         
         try (Reader file = new FileReader("property.json")) {
             JSONObject jsonfile = (JSONObject) processor.parse(file);
             
             JSONArray Land = (JSONArray) jsonfile.get("1");    
             for (Object i : Land) {
            	 
				 // Makes an array of name and cost to create objects
            	 String[] nameCostArray = new String[2];
            	 nameCostArray[0] = (String)((JSONObject)i).get("name");
            	 nameCostArray[1] = (String)((JSONObject)i).get("cost");
            	 
            	 // Puts id-array pair to lands HashMap to be later used in Land.java
            	 lands.put(Integer.parseInt((String)((JSONObject)i).get("id")), nameCostArray);
				 
             }
             
             JSONArray RailRoad = (JSONArray) jsonfile.get("2");
             for (Object i : RailRoad) {
				
            	// Makes an array of name and cost to create objects
            	 String[] nameCostArray = new String[2];
            	 nameCostArray[0] = (String)((JSONObject)i).get("name");
            	 nameCostArray[1] = (String)((JSONObject)i).get("cost");
            	 
            	// Puts id-array pair to railRoads HashMap to be later used in RailRoad.java
            	 railRoads.put(Integer.parseInt((String)((JSONObject)i).get("id")), nameCostArray);
            	 
             }
			 
             JSONArray Company = (JSONArray) jsonfile.get("3");
             for (Object i : Company) {
				 
            	// Makes an array of name and cost to create objects
            	 String[] nameCostArray = new String[2];
            	 nameCostArray[0] = (String)((JSONObject)i).get("name");
            	 nameCostArray[1] = (String)((JSONObject)i).get("cost");
            	 
            	// Puts id-array pair to companies HashMap to be later used in Company.java
            	 companies.put(Integer.parseInt((String)((JSONObject)i).get("id")), nameCostArray);
            	 
             }
             
         } catch (IOException e) {
             e.printStackTrace();
         } catch (ParseException e) {
             e.printStackTrace();
         }
     }
     
     public HashMap<Integer, String[]> getLands() {
    	 return lands;
     }
     
     public HashMap<Integer, String[]> getRailRoads() {
    	 return railRoads;
     }
     
     public HashMap<Integer, String[]> getCompanies() {
    	 return companies;
     }
}