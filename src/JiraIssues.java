import org.testng.annotations.Test;

import Files.ReUsableAction;

import static io.restassured.RestAssured.*;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

public class JiraIssues {
	
	@Test
	public void CreateAndEditIssue() {
		
		RestAssured.baseURI = "http://localhost:8080";
		
		// Step-1 Get session ID
		SessionFilter session = new SessionFilter();
		String response=given().header("Content-Type","application/json")
		.body("{ \r\n" + 
				"    \"username\": \"rajivkumar.s4\",\r\n" + 
				"     \"password\": \"Pass@1234\" \r\n" + 
				"     }")
		.filter(session)
		.when().post("rest/auth/1/session")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		 //System.out.println(response);
		 String Name=ReUsableAction.RowToJson(response, "session.name") ;  
		 String Value=ReUsableAction.RowToJson(response, "session.value") ;  
		 System.out.println("Name: "+Name);
		 System.out.println("Value: "+Value);
		 
		 //Step-2 create issue
		 String CreateIssueResponse=given().header("Content-Type","application/json")
					.body("{\r\n" + 
							"  \"fields\": {\r\n" + 
							"    \"project\": {\r\n" + 
							"      \"key\": \"APITEST\"\r\n" + 
							"    },\r\n" + 
							"    \"summary\": \"Credit Card defect - Issue for adding comments\",\r\n" + 
							"    \"description\":\"This is my first bug\",\r\n" + 
							"    \"issuetype\":{\r\n" + 
							"        \"name\":\"Bug\"\r\n" + 
							"    }\r\n" + 
							"   \r\n" + 
							"  }\r\n" + 
							"}").filter(session)
					.when().post("rest/api/2/issue")
					.then().assertThat().statusCode(201).extract().response().asString();
		
		// System.out.println(CreateIssueResponse);
		 String IssueId=ReUsableAction.RowToJson(CreateIssueResponse, "id") ;  
		 String IssueKey=ReUsableAction.RowToJson(CreateIssueResponse, "key") ;  
		 System.out.println("Id: "+IssueId);
		 System.out.println("Key: "+IssueKey);
		 
		 //Step-3 Add Comments
		 String CommentResponse=given().pathParam("Key", IssueId).header("Content-Type","application/json")
					.body("{\r\n" + 
							"    \"body\":\"This is rest API testing bugs\",\r\n" + 
							"  \"visibility\": {\r\n" + 
							"    \"type\": \"role\",\r\n" + 
							"    \"value\": \"Administrators\"\r\n" + 
							"  }\r\n" + 
							"}").filter(session)
					.when().post("rest/api/2/issue/{Key}/comment")
					.then().assertThat().statusCode(201).extract().response().asString();
		
		// System.out.println(CommentResponse);
		 String CommentsId=ReUsableAction.RowToJson(CommentResponse, "id") ;  
		 System.out.println("CommentsId: "+CommentsId);
		 // Step-4 Update comments
		 
		 String UpdateComments=given().pathParam("Key", IssueId).header("Content-Type","application/json")
					.body("{\r\n" + 
							"    \"body\":\"Comments 101 Updated from PostMan call\",\r\n" + 
							"  \"visibility\": {\r\n" + 
							"    \"type\": \"role\",\r\n" + 
							"    \"value\": \"Administrators\"\r\n" + 
							"  }\r\n" + 
							"}").filter(session)
					.when().put("rest/api/2/issue/{Key}/comment/"+CommentsId)
					.then().assertThat().statusCode(200).extract().response().asString();
		
		 //System.out.println(UpdateComments);
		 String UpdateCommentsId=ReUsableAction.RowToJson(UpdateComments, "id") ;  
		 System.out.println("Update CommentsId: "+UpdateCommentsId);
		 
		 
		 //Add Attachment
		 given().header("X-Atlassian-Token", "no-check").filter(session).pathParam("Key", IssueId)
		 .header("Content-Type","multipart/form-data")
		 .multiPart("file",new File("JiraIssue.txt"))
		 .when().post("rest/api/2/issue/{Key}/attachments").then().log().all().assertThat().statusCode(200);
		 
		 //Delete Issue
		/* 
		   given().header("Content-Type","application/json").filter(session)
				 				 .when().delete("/rest/api/2/issue/"+IssueId)
				 				 .then().assertThat().statusCode(204); 
				 				 */
		 
		 
	}

}
