package Files;

import io.restassured.path.json.JsonPath;

public class ReUsableAction {
	
	public static String RowToJson(String Response , String ValueToExtract)
	{
		JsonPath js1= new JsonPath(Response);
		String ExtractedValue = js1.getString(ValueToExtract);
		return ExtractedValue;
		
	}
	
	public static JsonPath RowToJson(String Response)
	{
		JsonPath js1= new JsonPath(Response);
		return js1;
		
	}

}
