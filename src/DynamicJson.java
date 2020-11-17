import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Files.Payload;
import Files.ReUsableAction;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson 
{
	@Test(dataProvider = "AddBookData")
	public void AddBook(String isbn , String aisle)
	{
		RestAssured.baseURI = "http://216.10.245.166";
		
		// Add book
	  String response=	given().header("Content-Type","application/json")
		.body(Payload.AddBook(isbn,aisle))
		.post("Library/Addbook.php")
		.then().assertThat().statusCode(200)
		.extract().response().asString();

	  JsonPath js = ReUsableAction.RowToJson(response);
	  String BookID = js.get("ID");
	  System.out.println("BookID :"+BookID);
	  
	  //Delete Book
	  String DeletePaylod =  Payload.DeleteBook(isbn,aisle);
	  System.out.println(DeletePaylod);
	  
	//  String DelRes=	given().header("Content-Type","application/json")
	//			.body(Payload.DeleteBook(isbn,aisle))
	//			.post("Library/DeleteBook.php")
	//			.then().assertThat().statusCode(200)
	//			.extract().response().asString();
	//  js = ReUsableAction.RowToJson(DelRes);
	//  String DeleteMsg = js.get("msg");
	//  System.out.println("Delete :"+DeleteMsg);
	  
	}
	
	
@DataProvider(name = "AddBookData")
public Object[][] GetAddBookData()
{
	return new Object[][] {{"Test11","Book11"},{"Test22","Book22"},{"Test33","Book33"}};
}
	
}
