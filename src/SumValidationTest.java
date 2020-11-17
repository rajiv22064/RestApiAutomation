import org.testng.Assert;
import org.testng.annotations.Test;

import Files.Payload;
import io.restassured.path.json.JsonPath;

public class SumValidationTest 
{
	@Test
	public void SumValidation() 
	{
		JsonPath js = new JsonPath(Payload.CoursePrice());
		int count = js.getInt("courses.size()");
		System.out.println("Number of course"+count);
		int PurchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Amount: "+PurchaseAmount);
		
		int TotalAmount = 0;
		for(int i=0;i<count;i++)
		{
			int Price = js.getInt("courses["+i+"].price");
			int Copies = js.getInt("courses["+i+"].copies");
			int NetAmount = Price*Copies;
			TotalAmount = TotalAmount + NetAmount;
		}
		System.out.println("Total Anount:"+TotalAmount);
		Assert.assertEquals(TotalAmount, PurchaseAmount);
	}
}
