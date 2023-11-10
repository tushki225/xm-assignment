package test.scenarios;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.config.Config;
import test.pages.SwapAPIPage;
import test.utils.Constants;

public class UserTestSteps {
	public Response response = null;
	public String latestDate;
	SwapAPIPage swapPageObject = new SwapAPIPage();

	@BeforeSuite
	public void getConfig() {
		Config.readPropertiesFile();
	}

	@Test
	public void findFilmByLatestReleaseDate(ITestContext context) {
		System.out.println(Constants.TESTS_STARTED + context.getName());

		try {
			response = swapPageObject.getResource(Config.getResourceFilmPath());
			Assert.assertEquals(response.getStatusCode(), 200, "Status Code Check Failed!");
			Assert.assertNotNull(response);
			latestDate = swapPageObject.getLatestDateFilm(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void findTallestPersonFromLatestFilm(ITestContext context) {
		try {
			response = swapPageObject.getResource(Config.getResourceFilmPath());
			Assert.assertEquals(response.getStatusCode(), 200, "Status Code Check Failed!");
			Assert.assertNotNull(response);
			swapPageObject.getTallestPersonFromLatestFilm(response, latestDate);

			System.out.println(Constants.TESTS_ENDED + context.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void findTallestPersonFromStarwarsFilm(ITestContext context) {
		System.out.println(Constants.TESTS_STARTED + context.getName());

		try {
			response = swapPageObject.getResource(Config.getResourceFilmPath());
			Assert.assertEquals(response.getStatusCode(), 200, "Status Code Check Failed!");
			Assert.assertNotNull(response);
			swapPageObject.getTallestPersonFromStarwarsFilm(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	  
  @Test
  @Parameters({"jsonSchema"}) 
  public void createJsonSchemaValidationForPeopleAPI(String jsonSchema, ITestContext context) { 
	  System.out.println(Constants.TESTS_STARTED +context.getName());
  
	  response = swapPageObject.getResource(Config.getResourcePeoplePath());
	  Assert.assertEquals(response.getStatusCode(),200,"Status Code Check Failed!");
	  swapPageObject.validateSchema(Config.getResourcePeoplePath(), jsonSchema);
  
	  System.out.println(Constants.TESTS_ENDED + context.getName()); 
  }
}
