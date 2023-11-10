package test.pages;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import test.config.Config;
import test.utils.HashMapSort;
import test.utils.RestUtils;
import test.utils.TestUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SwapAPIPage {

	//Get response based on resource path
	public Response getResource(String path) { 
		Response response = RestUtils.getResponsebyPath(Config.baseURI, path); 
    	return response;    		
    }	
	
	//Get Film by Latest Release date 
	public String getLatestDateFilm(Response response) { 
    	JsonPath  js = RestUtils.getJsonPath(response);
    	Map<String, String> filmDetails=new HashMap<String, String>();
    	
    	List<Map<String, String>> allFilms = js.getList("results");
    	if (allFilms.size()>0) {
    		for (Iterator<Map<String, String>> iterator = allFilms.iterator(); iterator.hasNext();) {
				Map<String, String> film = (Map<String, String>) iterator.next();
				filmDetails.put(film.get("release_date"), film.get("title"));
			}
    	} 
    	
    	ArrayList<String> datesList = new ArrayList<String>();
		for (String dates : filmDetails.keySet())  
			datesList.add(dates);
		
		String latestDate = TestUtils.getLatestDate(datesList);
		System.out.println("Latest Date is::" + latestDate);
		System.out.println("Film with Latest Release date is::" + filmDetails.get(latestDate));
		return latestDate;	 	 		
    }	
	
	//Get Tallest Person from the film with latest release date
	public void getTallestPersonFromLatestFilm(Response response, String date) { 
    	JsonPath  js = RestUtils.getJsonPath(response);
    	List<Map<String, String>> allFilms = js.getList("results");
    	List<Map<String, List<String>>> allFilmsWithCharacters = js.getList("results");
    	
    	if (allFilms.size()>0) {
    		HashMap<String, Integer> peopleDetails=new HashMap<String, Integer>();
    		for (int i=0; i<allFilms.size();i++) {
    			if(allFilms.get(i).get("release_date").equals(date)) {
    				List<String> allCharacters = allFilmsWithCharacters.get(i).get("characters");
    				
    				for (int j=0; j<allCharacters.size();j++) {
    					Response res = RestUtils.getResponse(allCharacters.get(j));
        				js = RestUtils.getJsonPath(res);
        				String characterHeight = js.get("height");
        				String characterName = js.get("name");
        				peopleDetails.put(characterName, Integer.valueOf(characterHeight));
    				}	
    			}	
			}
    		
    		Map<String, Integer> hm1 =HashMapSort.sortByValue(peopleDetails);
    		
    		List<String> keys = new ArrayList<String>(hm1.keySet());
    		String lastKey = keys.get(keys.size()-1);
    		
    		System.out.println("Tallest Character Name::" + lastKey);
    		System.out.println("Tallest Character Height is::" + hm1.get(lastKey));
    		
    	} 	 		
    }	
	
	//Get Tallest person from all Starwars films
	public void getTallestPersonFromStarwarsFilm(Response response) { 
    	JsonPath  js = RestUtils.getJsonPath(response);
    	List<Map<String, List<String>>> allFilmsWithCharacters = js.getList("results");
    	
    	if (allFilmsWithCharacters.size()>0) {
    		HashMap<String, Integer> peopleDetails=new HashMap<String, Integer>();
    		
    		System.out.println("Total films::" + allFilmsWithCharacters.size());
    		for (int i=0; i<allFilmsWithCharacters.size();i++) {
    			List<String> allCharacters = allFilmsWithCharacters.get(i).get("characters");
    			System.out.println("Total characters in " +i + " film are::" + allCharacters.size());
				
    			for (int j=0; j<allCharacters.size();j++) {
					Response res = RestUtils.getResponse(allCharacters.get(j));
    				js = RestUtils.getJsonPath(res);
    				String characterHeight = js.get("height");
    				String characterName = js.get("name");
    				
    				if(!peopleDetails.containsKey(characterName)) {
    					if(characterHeight.equals("unknown"))
    						peopleDetails.put(characterName, 0);
    					else
    						peopleDetails.put(characterName, Integer.valueOf(characterHeight));
    				}
				}	
    			
			}
    		System.out.println("Total Unique Persons are::" + peopleDetails.size());
    		Map<String, Integer> hm1 =HashMapSort.sortByValue(peopleDetails);
    		
    		List<String> keys = new ArrayList<String>(hm1.keySet());
    		String lastKey = keys.get(keys.size()-1);
    		
    		System.out.println("Tallest Person from all Starwars film::" + lastKey);
    		System.out.println("Tallest Person Height is::" + hm1.get(lastKey));
    		
    	} 	 		
    }
	
	//Validate Json schema for People API
	public void validateSchema(String path, String jsonSchema) { 
		try {
			File schema = new File(Config.getJsonSchemaPath()+jsonSchema);
			RestUtils.jSONSchemaValidation(path, schema);				    	    	
		}
		catch (Exception e) {
			e.printStackTrace();
		}    	 		
    }
	
}
