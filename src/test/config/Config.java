package test.config;

import test.utils.Constants;
import java.io.FileReader;
import java.util.Properties;

public class Config {
	public static String baseURI;
	public static String resource_films;
	public static String resource_people;
	private static String OS = System.getProperty(Constants.OS_IDENTIFIER).toLowerCase();
	private static FileReader reader;
	
	public static void readPropertiesFile (){ 
		try {
			Properties prop;
				if(OS.contains(Constants.WINDOWS_OS)) 
					reader = new FileReader(System.getProperty(Constants.USER_DIR_IDENTIFIER)+Constants.WIN_PROPERTIES_FILE_PATH);
				else 
					reader = new FileReader(System.getProperty(Constants.USER_DIR_IDENTIFIER)+Constants.MAC_PROPERTIES_FILE_PATH);
				
				prop = new Properties();
				prop.load(reader);
			
			baseURI= prop.getProperty(Constants.BASE_URI_IDENTIFIER); 
			resource_films= prop.getProperty(Constants.FILMS_RESOURCE_IDENTIFIER); 
			resource_people= prop.getProperty(Constants.PEOPLE_RESOURCE_IDENTIFIER); 
  
		} catch (Exception e) { 
			e.getMessage();
	  	}
	
	}
		
	public static String getJsonSchemaPath (){ 	
		if(OS.contains(Constants.WINDOWS_OS)) 
			return System.getProperty(Constants.USER_DIR_IDENTIFIER) + Constants.WIN_JSON_SCHEMA_PATH;	
		
		else 
			return System.getProperty(Constants.USER_DIR_IDENTIFIER) + Constants.MAC_JSON_SCHEMA_PATH;	
	}
	
	public static String getResourceFilmPath (){ 	
		return resource_films;
	}
	
	public static String getResourcePeoplePath (){ 	
		return resource_people;
	}
		
}
