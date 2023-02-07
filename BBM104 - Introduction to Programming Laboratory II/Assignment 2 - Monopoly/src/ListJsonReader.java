import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class ListJsonReader{
	private ArrayList<String> chanceArrayList = new ArrayList<String>(6);
	private ArrayList<String> communityChestArrayList = new ArrayList<String>(11);
     
    public ListJsonReader() {
        JSONParser processor = new JSONParser();
        
        try (Reader file = new FileReader("list.json")) {
            JSONObject jsonfile = (JSONObject) processor.parse(file);
            
            JSONArray chanceList = (JSONArray) jsonfile.get("chanceList");
            for (Object i : chanceList) {
            	// Adds the chance card item values in chanceArrayList
            	chanceArrayList.add(((String)((JSONObject)i).get("item")));
            }
            
            JSONArray communityChestList = (JSONArray) jsonfile.get("communityChestList");
            for (Object i : communityChestList) {	
            	// Adds the community chest card item values in communityChestArrayList
				communityChestArrayList.add((String)((JSONObject)i).get("item"));	
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<String> getChanceArrayList() {
    	return chanceArrayList;
    }
    
    public ArrayList<String> getCommunityChestArrayList() {
    	return communityChestArrayList;
    }
}

