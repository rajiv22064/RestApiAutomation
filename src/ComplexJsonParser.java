import org.testng.Assert;

import Files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JsonPath js = new JsonPath(Payload.CoursePrice());
		int count = js.getInt("courses.size()");
		System.out.println("Number of course"+count);
		int PurchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Amount: "+PurchaseAmount);
		String FirstCourseTitle = js.getString("courses[0].title");
		System.out.println("First Course Titile: "+FirstCourseTitle);
		String SecoundCourseTitle = js.getString("courses[2].title");
		System.out.println("Secound Course Titile: "+SecoundCourseTitle);
		// Print All course Title and respective prices
		for(int i=0;i<count;i++)
		{
			String CourseT = js.getString("courses["+i+"].title");
			int Price = js.getInt("courses["+i+"].price");
			System.out.println("Titile: "+CourseT+", Price: "+Price);
		}
		
		//5. Number of Copies sold for RPA course
		for(int i=0;i<count;i++)
		{
			String CourseT = js.getString("courses["+i+"].title");
			if(CourseT.equalsIgnoreCase("RPA"))
			{
				int NoOfCopies = js.getInt("courses["+i+"].copies");
				System.out.println("Number of Copies sold is for "+CourseT+" is:"+NoOfCopies);
				break;
			}
		}
		
		//6.Verify the Sum of all course amount matches the purchase amount
		int TotalAmount = 0;
		for(int i=0;i<count;i++)
		{
			int Price = js.getInt("courses["+i+"].price");
			int Copies = js.getInt("courses["+i+"].copies");
			int NetAmount = Price*Copies;
			TotalAmount = TotalAmount + NetAmount;
		}
		System.out.println("Total Anount is:"+TotalAmount);
		Assert.assertEquals(TotalAmount, PurchaseAmount);
		
		
		
	}

}
