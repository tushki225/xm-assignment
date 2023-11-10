package test.utils;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import test.config.Config;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import java.io.File;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RestUtils {

    //Returns response by given path
    public static Response getResponsebyPath(String baseURI, String path) {
    	RestAssured.baseURI = baseURI;
        return RestAssured.given().when().get(path).then().extract().response();
    }    
    
    public static Response getResponse(String path) {
        return RestAssured.given().when().get(path).then().extract().response();
    }  
    
    //Validate Json Schema
    public static void jSONSchemaValidation(String path, File schema) {
    	RestAssured.baseURI = Config.baseURI;
        RestAssured.given().get(path).then().body(matchesJsonSchema(schema)); 
    } 
	
    //Create Resource with body
    public static Response createResource(String path, String requestBody) {
    	return RestAssured.given().contentType(Constants.APPLICATION_JSON).body(requestBody).when().post(path);
    } 
    
    //Updates a Resource with request body
    public static Response updateResource(String path, String requestBody) {
    	return RestAssured.given().contentType(Constants.APPLICATION_JSON).body(requestBody).when().put(path);
    }
    
    //Deletes a Resource
    public static Response deleteResource(String path) {
    	return RestAssured.given().contentType(Constants.APPLICATION_JSON).when().delete(path);
    }

    //Returns JsonPath object
    public static JsonPath getJsonPath (Response res) {
        String json = res.asString();
        return new JsonPath(json);
    }

    public static JSONObject getJsonParsed(String body) {
        JSONParser parser = new JSONParser();
        try {
            return (JSONObject)parser.parse(body);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
